/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.bs2;

import de.unibi.techfak.bibiserv.cms.TenumParam;
import de.unibi.techfak.bibiserv.cms.Tfunction;
import de.unibi.techfak.bibiserv.cms.TinputOutput;
import de.unibi.techfak.bibiserv.cms.Tparam;
import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 *
 * @author pol3waf
 */
public class Bs2Document implements IBs2Document {

    
    private final TrunnableItem runnableItem;
    private final HashMap<String, String> additionalStrings = new HashMap<>();
    private final ArrayList<String> argumentOrder = new ArrayList<>();
    
    
    
    /**
     * Wrapper class for the bs2-document.
     * @param ri runnableItem of the document
     * @throws JAXBException 
     */
    public Bs2Document(TrunnableItem ri) throws JAXBException
    {
        this.runnableItem = ri;
    }

    
    
    /**
     * Gets a list of references of the command line arguments in the order they 
     * should be used. 
     * @param function the function that should be checked for its argument order
     * @return ordered list of IDs of each input or parameter
     */
    @Override
    public ArrayList<String> getCommandLineArgumentOrderAsReferences(Tfunction function)
    {
        ArrayList<String> orderedReferences = new ArrayList<>();
        
        List<JAXBElement<?>> list = function.getParamAndInputOutputOrder().getReferenceOrAdditionalString();
        for (JAXBElement<?> e : list)
        {
            /*
            Check whether the list element e is an additionalString 
            (String-object) or a reference-thingy (Object-object).
            */
            if (e.getDeclaredType() == String.class)
            {
                /*
                additionalStrings have to be saved separately in a hashmap with 
                custom key each so that they can be found again via the 
                their reference key. (This is because this function shoudl just
                return references.)
                */
                String key = "additionalString_" + e.toString();
                String additionalString = (String)e.getValue();
                additionalStrings.put(key, additionalString);
                orderedReferences.add(key);
            }
            else if (e.getDeclaredType() == Object.class) // this is not good!!! Any other checks possible???
            {
                // check for inputs, params, enumparams, enumgroups
                if (e.getValue() instanceof TinputOutput)
                {
                    TinputOutput inputOutput = (TinputOutput)e.getValue();
                    orderedReferences.add(inputOutput.getId());
                }
                else if (e.getValue() instanceof Tparam)
                {
                    Tparam param = (Tparam)e.getValue();
                    orderedReferences.add(param.getId());
                }
                else if (e.getValue() instanceof TenumParam)
                {
                    TenumParam enumParam = (TenumParam)e.getValue();
                    orderedReferences.add(enumParam.getId());
                }
                else
                {
                    System.out.println("ParamAndInputOutputOrder : unexpected instance type");
                }
            }
        }
        return orderedReferences;
    }

    
    
    /**
     * Get a list of functions from the bs2-file.
     * @return 
     */
    @Override
    public List<Tfunction> getFunctions() 
    {
        List functionList = runnableItem.getExecutable().getFunction();
        return functionList;
    }

    
    
    /**
     * Get the TinputOutput object from the inputs of the bs2-file via its ID.
     * @param id ID that is to be used to search for that particular input
     * @return TinputOutput object
     * @throws Exception if ID is not found
     */
    @Override
    public TinputOutput getIntputById(String id) throws Exception
    {
        TinputOutput input = null;
        
        List<TinputOutput> inputList = runnableItem.getExecutable().getInput();
        for (TinputOutput i : inputList)
        {
            if (id.equals(i.getId()))
            {
                input = i;
            }
        }
        
        if (input == null)
        {
            throw new Exception("ID " + id + "not found in inputs");
        }
        else
        {
            return input;
        }
        
    }

    
    
    /**
     * Get a param by its ID.
     * @param id id which should be used to search for the param
     * @return Tparam object
     * @throws Exception if the id is not found
     */
    @Override
    public Tparam getParamById(String id) throws Exception
    {
        Tparam param = null;
                
        List<Tparam> paramList = runnableItem.getExecutable().getParam();
        for (Tparam p : paramList)
        {
            if (p.getId().equals(id))
            {
                param = p;
            }
        }
        
        if (param == null)
        {
            throw new Exception("No such param with id " + id);
        }
        else
        {
            return param;
        }
    }

    
    
    /**
     * Get EnumParam by its ID. Throws an error in case the ID is not found.
     * @param id ID which is to be searched for
     * @return TenumParam with the given ID
     * @throws Exception if there is no such ID
     */
    @Override
    public TenumParam getEnumParamById(String id) throws Exception
    {
        TenumParam enumParam = null;
                
        List<TenumParam> enumParamList = runnableItem.getExecutable().getEnumParam();
        for (TenumParam p : enumParamList)
        {
            if (p.getId().equals(id))
            {
                enumParam = p;
            }
        }
        
        if (enumParam == null)
        {
            throw new Exception("No such param with id " + id);
        }
        else
        {
            return enumParam;
        }
    }

    
    
    /**
     * Get the base command of the bs2-file. Usually this is the name of or the 
     * path to the script/ tool which is described by the bs2-file.
     * @return base command
     */
    @Override
    public String getBaseCommand() {
        String baseCommand = this.runnableItem.getExecutable().getExecInfo().getCallingInformation();
        return baseCommand;
    }
    
    
    
    
//    /**
//     * Loads a bs2 file and un-marshalls it into POJOs.
//     * @param path path to the bs2-file
//     * @return TrunnableItem, which is the root of the bs2document.
//     * @throws JAXBException 
//     */
//    private void setRunnableItem(String path) throws JAXBException
//    {
//        Source source = new StreamSource(path);
//        JAXBContext ctx = JAXBContext.newInstance(new Class[] {TrunnableItem.class});
//        Unmarshaller unmarshaller = ctx.createUnmarshaller();
//        TrunnableItem root = unmarshaller.unmarshal(source, TrunnableItem.class).getValue();
//        
//        this.runnableItem = runnableItem;
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    
    
    
    @Override
    public int getPositionOfInput(Tfunction.Inputref inputref) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPositionOfParameter(TenumParam enumparam) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPositionOfParameter(Tparam param) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
