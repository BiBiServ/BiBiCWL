/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.bs2processor;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//import org.jdom2.*;
//import org.jdom2.input.SAXBuilder;
//import org.jdom2.output.Format;
//import org.jdom2.output.XMLOutputter;
//import org.jdom2.Namespace;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.codehaus.jackson.map.ObjectMapper;

import de.unibi.techfak.bibiserv.cms.*;
import java.util.HashMap;
import java.util.List;
import java.util.logging.ConsoleHandler;
import de.unibi.techfak.bibiserv.basespaceconverter.formbuilder.FormBuilder;
import static de.unibi.cebitec.bibiworkflow.bs2processor.BSConstants.*;
import de.unibi.techfak.bibiserv.basespaceconverter.containerclasses.Triplet;
import de.unibi.techfak.bibiserv.cms.Tfunction.Inputref;
import de.unibi.techfak.bibiserv.cms.Tfunction.Outputfileref;
import de.unibi.techfak.bibiserv.cms.TinputOutput;
import de.unibi.techfak.bibiserv.cms.TparamGroup.ParamGroupref;
import de.unibi.techfak.bibiserv.cms.TparamGroup.Paramref;
import java.util.ArrayList;





/**
 * The BS2Accessor converts the bs2-file into a POJO representation and then uses 
 those to create a HashMap of the relevant Structures. 
 * The HashMap uses will be structured as follows: 
 * 
 * (componentName, (componentProperty, propertyValue))
 * 
 * componentName: Name of the BibiServWebApp component
 * componentProperty: Name of the property of the component
 * propertyValue: Value of the property
 * 
 * 
 * This HashMap can then be read by the FormBuilder and the Fields and Rules 
 * of the Basespace form can be created accordingly.
 * 
 * 
 * @author jsaydo
 */
public class BS2Accessor {
    
    private final static Logger LOG = Logger.getLogger(BS2Accessor.class.getName());
    private TrunnableItem runnableItem;
    private FormBuilder formBuilder;
    private OntologyHandler bibiservOntologyHandler;
    // HashMap containing all the necessary components
    private HashMap<String, HashMap<String, String>> components;
    
    
    
    
    
    
    /**
     * Standard constructor for the bs2parser. Unmarshalls the provided
     * document and saves its root element.
     * @param bs2FilePath
     * @param ontologyFilePath path to ontology file
     */
    public BS2Accessor(String bs2FilePath, String ontologyFilePath) 
    {
        try 
        {
            LOG.setLevel(Level.ALL);
            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(Level.ALL);
            LOG.addHandler(handler);
            
            runnableItem = unmarshalDocument(bs2FilePath);
            bibiservOntologyHandler = new OntologyHandler(ontologyFilePath);
        }
        catch (JAXBException ex) 
        {
            Logger.getLogger(BS2Accessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    /**
     * Additional constructor for the bs2accessor. Does not unmarshall the
     * any document. This would have to be done on another step.
     */
    public BS2Accessor() 
    {
        LOG.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOG.addHandler(handler);
    }
    
    
    
    
    /**
     * Unmarshalls a bs2 file and returns the root element (runnableItem).
     * The document is represented as POJOs.
     * @param pathToFile location of the bs2 file
     * @throws JAXBException
     */
    public TrunnableItem unmarshalDocument(String pathToFile) throws JAXBException 
    {
        Source source = new StreamSource(pathToFile);
        JAXBContext ctx = JAXBContext.newInstance(new Class[] {TrunnableItem.class});
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        JAXBElement<TrunnableItem> root = unmarshaller.unmarshal(source, TrunnableItem.class);
        
        return root.getValue();
    }
    
    
    
    
    
    
    
    private int getNumberOfFunctions() 
    {
        return runnableItem.getExecutable().getFunction().size();
    }
    
    private int getNumberOfInputs() 
    {
        return runnableItem.getExecutable().getInput().size();
    }
    
    
    
    
    /**
     * Get a list of Tfunctions.
     * @return List of Tfunciton objects
     */
    public List<Tfunction> getFunctions()
    {
        return runnableItem.getExecutable().getFunction();
    }
    
    
    
    /**
     * Get the desired Tfunction.
     * @param index index of the function.
     * @return Tfunction object
     */
    public Tfunction getFunction(int index)
    {
        return runnableItem.getExecutable().getFunction().get(index);
    }
    
    
    
    
    public String getId(Tfunction function)
    {
        return function.getId();
    }
    
    
    
    /**
     * Get the name of a function.
     * @param function corresponding function
     * @return name a string
     */
    public String getName(Tfunction function)
    {
        return function.getName().get(0).getValue();
    }
    
    
    
    
    /**
     * Get a functions description.
     * @param function corresponding function
     * @return description as string
     */
    public String getDescription(Tfunction function)
    {
        if (function.isSetDescription())
        {
            String description = function.getDescription().get(0).getContent().get(0).toString();
            LOG.finest("Function description = " + description);
            return description;
        }
        return null;
    }
    
    
    
    /**
     * Get ShortDescription of a function.
     * @param function corresponding function
     * @return short description a string
     */
    public String getShortDescription(Tfunction function)
    {
        if (function.isSetShortDescription())
        {
            String shortDescription = function.getShortDescription().get(0).getValue();
            LOG.finer("Function short description = " + shortDescription);
            return shortDescription;
        }
        return null;
    }
    
    
    
    /**
     * Get the input of a function.
     * @param function corresponding function
     * @return input as TinputOutput object
     */
    public ArrayList<TinputOutput> getInputRef(Tfunction function)
    {
        if (function.isSetInputref()) 
        {
            ArrayList<TinputOutput> inputs = new ArrayList<>();
            for (Inputref inputref : function.getInputref())
            {
                inputs.add((TinputOutput) inputref.getRef());
            }
            return inputs;
        }
        return null;
    }
    
    
    
    /**
     * Get the output of a function.
     * @param function corresponding function
     * @return output as TinputOutput object
     */
    public TinputOutput getOutputRef(Tfunction function)
    {
        if (function.isSetOutputref()) 
        {
            return (TinputOutput) function.getOutputref().getRef();
        }
        return null;
    }
    
    
    
    /**
     * Get a list of output files for a function.
     * @param function corresponding function
     * @return list of output files
     */
    public ArrayList<ToutputFile> getOutputFiles(Tfunction function)
    {
        if (function.isSetOutputfileref())
        {
            ArrayList<ToutputFile> outputFiles = new ArrayList<>();
            // TODO: do stuff
            for (Outputfileref outputfileref : function.getOutputfileref())
            {
                outputFiles.add((ToutputFile) outputfileref.getRef());
            }
            return outputFiles;
        }
        return null;
    }
    
    
    
    /**
     * Get a list of parameters from a function. The parameters are either
     * of type Tparam or of type TenumParam.
     * @param function corresponding function
     * @return list of Tparam and TenumParam objects
     */
    public ArrayList<Object> getParameters(Tfunction function)
    {
        if (function.isSetParamGroup()) 
        {
            // Check the paramgroup in the function for multiple paramgrouprefs,
            // paramrefs. Extract the params and enumParams from them.
            TparamGroup functionParamGroup = function.getParamGroup();
            LOG.finest("Function paramgroup = " + functionParamGroup.getId());
            List<Paramref> paramrefList = this.getParamrefsFromParamGroup(functionParamGroup);
            LOG.finest(paramrefList.toString());
            ArrayList<Object> parameters = new ArrayList<>();
            
            for (Paramref paramref : paramrefList)
            {
                if (paramref.getRef() instanceof Tparam)
                {
                    Tparam param = (Tparam) paramref.getRef();
                    parameters.add(param);
                }
                else if (paramref.getRef() instanceof TenumParam)
                {
                    TenumParam enumParam = (TenumParam) paramref.getRef();
                    parameters.add(enumParam);
                }
            }
            return parameters;
        }
        return null;
    }
    
    
    
    
    
    
    
    
    
    /**
     * Get param (parameter) of the bs2 file via its index.
     * @param index index of the param
     * @return param object
     */
    private Tparam getParam(int index) 
    {
        return runnableItem.getExecutable().getParam().get(index);
    }
    
    
    
    /**
     * Get param (parameter) of the bs2 file via its id.
     * @param id id of the param
     * @return param object
     */
    private Tparam getParam(String id)
    {
        for (Tparam param : runnableItem.getExecutable().getParam())
        {
            if (param.getId().equals(id))
            {
                return param;
            }
        }
        return null;
    }
    
    
    
    /**
     * Get paramgroup of the bs2 file via its index.
     * @param index index of the paramgroup
     * @return paramgroup object
     */
    private TparamGroup getparamGroup(int index) 
    {
        return runnableItem.getExecutable().getParamGroup().get(index);
    }
    
    
    
    /**
     * Get param group of the bs2 file via its id.
     * @param id id of the param group
     * @return param group object
     */
    private TparamGroup getParamGroup(String id)
    {
        for (TparamGroup paramGroup : runnableItem.getExecutable().getParamGroup())
        {
            if (paramGroup.getId().equals(id))
            {
                return paramGroup;
            }
        }
        return null;
    }
    
    
    
    /**
     * Retrieves a list of Parameter objects (Tparam and TenumParam) from a 
     * paramGroup (TparamGroup) in the bs2 file. 
     * If the paramGroup contains other paramGroups these are searched 
     * recursively and the lists of params and enumParams are concatenated.
     * @param paramGroup paramGroup which is searched for params
     * @return List of parameters; null if none exist.
     */
    private List<Paramref> getParamrefsFromParamGroup(TparamGroup paramGroup) 
    {
        ArrayList<Paramref> paramrefList = new ArrayList<>();
        // A paramgroup can contain other param groups or paramlists,
        // hence we only get back a generic object of type Object.
        List<Object> paramThings = paramGroup.getParamrefOrParamGroupref();
        
        // check for every object if it is a paramref or a paramgroupref and
        // extract the params and add them to a list of params.
        for (Object o : paramThings) 
        {
            if (o instanceof Paramref) 
            {
                LOG.finer("found a paramref in the paramgroup");
                // Convert the referenced object to Tparam or TenumParam and 
                // add it to the param list.
                Paramref paramref = (Paramref)o;
                
                paramrefList.add(paramref);
            }
            else if (o instanceof ParamGroupref) 
            {
                LOG.finer("found a paramgroupref in the paramgroup");
                // Convert the object to a TparamGroup so that the inner param 
                // group can be checked for params.
                TparamGroup innerParamGroup = (TparamGroup)((ParamGroupref)o).getRef();
                // Since this is a method for retrieving params out of a param
                // group, the method is called recursivly and the resulting new
                // list of params is added to the existing (parent-ish) param 
                // list.
                paramrefList.addAll(this.getParamrefsFromParamGroup(innerParamGroup));
            }
        }
        
        return paramrefList;
    }
    
    
    
    
    
    
    
    /**
     * Get the input object of the bs2 file via its index.
     * @param index index of the input object
     * @return input object
     */
    private TinputOutput getInput(int index)
    {
        return runnableItem.getExecutable().getInput().get(index);
    }
    
    
    
    /**
     * Get input object of the bs2 file via its id.
     * @param id id of the input object
     * @return input object
     */
    private TinputOutput getInput(String id)
    {
        for (TinputOutput input : runnableItem.getExecutable().getInput())
        {
            if (input.getId().equals(id))
            {
                return input;
            }
        }
        return null;
    }
    
    
    
    
    /**
     * Retrieves the innards of a TinputOutput object and puts them into a 
     * HashMap. The keys are the attributes names and the values are the
     * attributes value.
     * @param input TinputOutput object that is checked
     * @return HashMap with the attributes of the TinputOutput field
     */
    public HashMap<String, String> getInputInnards(TinputOutput input) {
        
        HashMap<String, String> inputMap = new HashMap<>();
        
        inputMap.put(TYPE, bibiservOntologyHandler.getBasespaceInputType(input.getType()));
        inputMap.put(ID, input.getId());
        inputMap.put(NAME, input.getName().get(0).getValue()); // why is the name inside a list???
        inputMap.put(HANDLING, input.getHandling());
        inputMap.put(OPTION, input.getOption());
//        inputMap.put(DESCRIPTION, input.getDescription().get(0).getContent()); // does not work like in the short DESCRIPTION - why???
        inputMap.put(SHORTDESCRIPTION, input.getShortDescription().get(0).getValue() );
        inputMap.put(REQUIREMESSAGE, "");
        inputMap.put(REQUIRE, "true");

        LOG.log(Level.FINER, "ParamMap contains: {0}", inputMap.toString());
        
        return inputMap;
    }
    
    
    
    
    /**
     * Retrieves the innards of a Tparam object and puts them into a simple
     * HashMap of type <String, String>. The keys are the attributes names and 
     * the values are the attributes value.
     * @param param Tparam object that is checked
     * @return HashMap with the attributes of the Tparam field
     */
    private HashMap<String, String> getParamInnards(Tparam param) 
    {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put(ID, param.getId());
        paramMap.put(NAME, param.getName().get(0).getValue()); // change this hardcoded crap
        paramMap.put(OPTION, param.getOption());
        paramMap.put(GUIELEMENT, param.getGuiElement());
        paramMap.put(TYPE, param.getType().value());
        paramMap.put(MIN, String.valueOf(param.getMin().getValue()));
        paramMap.put(MAX, String.valueOf(param.getMax().getValue()));
        paramMap.put(MINLENGTH, String.valueOf(param.getMinLength()));
        paramMap.put(MAXLENGTH, String.valueOf(param.getMaxLength()));

        LOG.log(Level.FINER, "Options contained:{0}", paramMap.toString());
        
        return paramMap;
    }
    
    
    
    
    
    /**
     * Retrieves the innards of a TenumParam object and puts them into a 
     * Hashmap of type <String, T>. The keys are the attributes names and the 
     * values are the attributes value.
     * @param <V>
     * @param enumParam TenumParam object that is checked
     * @return HashMap with the attributes of the TenumParam field
     */
//    public <V> HashMap<String, Object> getEnumParamInnards(TenumParam enumParam) 
//    {
//        ArrayList<Triplet> values = new ArrayList<>();
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put(ID, enumParam.getId());
//        paramMap.put(NAME, enumParam.getName().get(0).getValue()); // change this hardcoded crap
//        paramMap.put(OPTION, enumParam.getOption());
//        paramMap.put(GUIELEMENT, enumParam.getGuiElement());
//        paramMap.put(TYPE, enumParam.getType().value());
//        paramMap.put(SEPARATOR, enumParam.getSeparator());
//        
//        // check each enumValues' values and put it into an arraylist of triplets
//        for (TenumValue enumValue : enumParam.getValues())
//        {
//            values.add(new Triplet(
//                    enumValue.isSetName() ? enumValue.getName() : null, 
//                    enumValue.isSetValue() ? enumValue.getValue(): null, 
//                    enumValue.isSetDefaultValue() ? enumValue.isDefaultValue() : false
//            ));
//        }
//        paramMap.put(VALUES, values);
//        
//        
//        LOG.log(Level.FINER, "Options contained:{0}", paramMap.toString());
//        
//        return paramMap;
//    }
    
    
    public <T> ArrayList<Triplet> getValuesFromEnumParam(TenumParam enumParam)
    {
        ArrayList<Triplet> values = new ArrayList<>();
        
        // check each enumValues' values and put it into an arraylist of triplets
        for (TenumValue enumValue : enumParam.getValues())
        {
            values.add(new Triplet<String, String, Boolean>(
                    enumValue.isSetName() ? enumValue.getName().get(0).getValue() : null, 
                    enumValue.isSetValue() ? enumValue.getValue(): null,                    //??? why is the value a string? Apparently it is always a string but it makes no sense ???
                    enumValue.isSetDefaultValue() ? enumValue.isDefaultValue() : false
            ));
            
        }
        
        return values;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    private String getInputType(TinputOutput io)
//    {
//        String bibitype = io.getType();
//        
//        if (bibitype == )
//        return type;
//    }
    
    
    
    // PLACEHOLDER
    private <T> String getDescription(List<T> list) {
        String concat = "";
        for (T element : list) {
            System.out.println(element);
            concat.concat(element.toString());
        }
        return concat;
    }
    
    
    
    // PLACEHOLDER
    private <T> String getShortDescriptionByLanguage(String lang) {
        return "";
    }
    
    
    
    // PLACEHOLDER
    private <T> String getId(T element) {
        
        return "";
    }
    
    
    
}
