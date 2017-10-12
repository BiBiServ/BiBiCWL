/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.bs2;

import de.unibi.techfak.bibiserv.cms.TenumParam;
import de.unibi.techfak.bibiserv.cms.Tfunction;
import de.unibi.techfak.bibiserv.cms.Tfunction.Outputfileref;
import de.unibi.techfak.bibiserv.cms.TinputOutput;
import de.unibi.techfak.bibiserv.cms.ToutputFile;
import de.unibi.techfak.bibiserv.cms.Tparam;
import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 *
 * @author pol3waf
 */
public class Bs2Document implements IBs2Document
{
    
    private static final Logger LOGGER = Logger.getLogger(Bs2Document.class.getName());
    
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
                their reference key. (This is because this function should just
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
                    LOGGER.info("ParamAndInputOutputOrder : unexpected instance type");
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
    
    
    
    
    @Override
    public String getAdditionalStringById(String id) throws Exception
    {
        String additionalString = this.additionalStrings.get(id);
        
        return additionalString;
    }
    
    
    
    
    @Override
    public TinputOutput getOutputById(String id) throws Exception
    {
        TinputOutput output = null;
        List<TinputOutput> outputs = this.runnableItem.getExecutable().getOutput();
        
        for (TinputOutput o : outputs)
        {
            if (o.getId().equals(id))
            {
                output = o;
            }
        }
        
        if (output == null)
        {
            throw new Exception("No such output with id " + id);
        }
        else
        {
            return output;
        }
    }
    
    
    
    
    @Override
    public ToutputFile getOutputFileById(String id) throws Exception
    {
        ToutputFile outputFile = null;
        List<ToutputFile> outputFiles = this.runnableItem.getExecutable().getOutputfile();
        
        for (ToutputFile of : outputFiles)
        {
            if (of.getId().equals(id))
            {
                outputFile = of;
            }
        }
        
        if (outputFile == null)
        {
            throw new Exception("No such outputFile with id " + id);
        }
        else
        {
            return outputFile;
        }
    }
    
    
    
    /**
     * Get the base command of the bs2-file. Usually this is the name of or the 
     * path to the script/ tool which is described by the bs2-file.
     * @return base command
     */
    @Override
    public String getBaseCommand() throws Exception
    {
        String baseCommand = this.runnableItem.getExecutable().getExecInfo().getCallingInformation();
        return baseCommand;
    }
    
    
    
    
    /**
     * Checks what type of "input" an id is pointing at. The function checks 
     * all TinputOutputs, Tparams, TenumParams of the TrunnableItem and the 
     * additionalStrings list of the bs2Document.
     * @param id ID of the object to be
     * @return type or null (if didn't find ID)
     */
    @Override
    public ArgumentType getTypeOfArgumentById(String id)
    {
        ArgumentType type = null;
        
        // search inputs for the given id
        for (TinputOutput input : this.runnableItem.getExecutable().getInput())
        {
            String inputId = input.getId();
            if (inputId.equals(id))
            {
                type = ArgumentType.input;
            }
        }
        
        // search params for given id
        for (Tparam param : this.runnableItem.getExecutable().getParam())
        {
            String paramId = param.getId();
            if (paramId.equals(id))
            {
                type = ArgumentType.param;
            }
        }
        
        // search enumParams for given id
        for (TenumParam eparam : this.runnableItem.getExecutable().getEnumParam())
        {
            String eParamId = eparam.getId();
            if (eParamId.equals(id))
            {
                type = ArgumentType.enumParam;
            }
        }
        
        // search the additionalStrings
        for (String key : this.additionalStrings.keySet())
        {
            if (key.equals(id))
            {
                type = ArgumentType.additionalString;
            }
        }
        
        // search outputs for given id
        for (TinputOutput output : this.runnableItem.getExecutable().getOutput())
        {
            if (output.getId().equals(id))
            {
                type = ArgumentType.output;
            }
        }
        
        return type;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /// to be removed ------------------------------------------------------
    
    
    @Override
    public int getPositionOfInput(Tfunction.Inputref inputref)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPositionOfParameter(TenumParam enumparam)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPositionOfParameter(Tparam param)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    /**
     * Gets the output of a function. 
     * 
     * TODO: should probably use IDs instead of function ... or not?
     * 
     * @param function
     * @return 
     */
    public TinputOutput getFunctionOutput(Tfunction function)
    {
        return (TinputOutput)(function.getOutputref().getRef());
    }
    
    
    
    /**
     * Gets a List of OutputFile objects which are connected to a given function.
     * @param function
     * @return OutputFiles for a given function
     */
    public ArrayList<ToutputFile> getFunctionOutputFiles(Tfunction function)
    {
        ArrayList<ToutputFile> outputFiles = new ArrayList<>();
        for (Outputfileref ofr : function.getOutputfileref())
        {
            ToutputFile o = (ToutputFile)ofr.getRef();
            outputFiles.add(o);
        }
        return outputFiles;
    }
    
    

    
    
}
