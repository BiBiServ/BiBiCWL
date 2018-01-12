/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
public class CwlTool implements ICwlTool {
    
    private final static Logger LOGGER = Logger.getLogger(CwlTool.class.getName());

    @JsonProperty
    private String cwlVersion;  //make this a enum? why? --> name mismatching cwl defenition
    @JsonProperty("class")
    private String cwlClass;    //make this a enum? why? --> name mismatching cwl defenition
    @JsonProperty
    private String baseCommand;
    @JsonProperty
    private String stdout;
    
    
    /**
     * _List_ of inputs of the CWL-Tool. This member variable will be read when 
     * writing the CWL-Tool into a file.
     */
    @JsonProperty
    private HashMap<String, Input> inputs;
    /**
     * _List_ of outputs of the CWL-Tool. This member variable will be read when 
     * writing the CWL-Tool into a file.
     */
    @JsonProperty
    private HashMap<String, Output> outputs;
    
    /**
     * _Array_ of requirements of the CWL-Tool. This member variable will be read when 
     * writing the CWL-Tool into a file.
     */
//    @JsonProperty
//    private Requirement[] requirements;
//    
    /**
     * List of requirements (which will be converted into an array when writing 
     * the CWL-Tool into a file).
     */
    @JsonProperty
    private HashMap<String, Requirement> requirements = null;
    
//    @JsonProperty
//    private Argument[] arguments;
    @JsonProperty
    private ArrayList<Argument> arguments = null;
    
    
    /**
     * Hints are a list of soft requirements. (i.e. need not be met)
     */
    @JsonProperty
    private HashMap<String, Requirement> hints = null;
    
    /**
     * Default constructor which creates a bare bones but functional CWL-Tool.
     * The CWL-Version ("v.1.0") and class ("CommandLineTool") of the CWL-Tool 
     * are fixed.
     */
    public CwlTool()
    {
        this.cwlVersion = "v1.0";
        this.cwlClass = "CommandLineTool";
        this.stdout = null;
        this.inputs = new HashMap<>();
        this.outputs = new HashMap<>();
//        this.requirements = null;
//        this.requirementsList = new ArrayList<>();
//        this.argumentList = new ArrayList<>();
//        this.hints = new HashMap<>();
    }
    
    
    
    /**
     * Constructor which crates a bare bones but functional CWL-Tool.
     * The CWL-Version and class of the CWL-Tool depends on the given 
     * parameters.
     * @param cwlVersion Version of the CWL-Standard which is used
     * @param cwlClass "class" of the cwl-Tool (e.g. "CommandLineTool")
     */
    public CwlTool(String cwlVersion, String cwlClass)
    {
        this.cwlVersion = cwlVersion;
        this.cwlClass = cwlClass;
        this.stdout = null;
        this.inputs = new HashMap<>();
        this.outputs = new HashMap<>();
//        this.requirements = null;
//        this.requirementsList = new ArrayList<>();
//        this.argumentList = new ArrayList<>();
//        this.hints = new HashMap<>();
    }
    
    
    
    
    
    
    
    
    /**
     * Sets up a stdout for the CWL tool with the given reference of the 
     * dependant input.
     * @param inputReference input field to be used as source for the output file's name
     */
    public void setupStdout(String inputReference) {
        this.stdout = "$(inputs." + inputReference + ")";
    }
    
    
    
    
    @Override
    public void addHint(ERequirementClass requirementClass, String argument)
    {
        if (this.hints == null)
        {
            this.hints = new HashMap<>();
        }
        
        Requirement r = createRequirement(requirementClass, argument);
        this.hints.put(requirementClass.name(), r);
    }
    
    
    
    
    /**
     * 
     * @param requirementClass 
     * @param argument 
     */
    @Override
    public void addRequirement(ERequirementClass requirementClass, String argument) {
        
        if (this.requirements == null)
        {
            this.requirements = new HashMap<>();
        }
        
        Requirement r = createRequirement(requirementClass, argument);
        this.requirements.put(requirementClass.name(), r);
    }
    
    
    
    
    /**
     * Creates a requirement for use in the requirements or hints list.
     * @param requirementClass
     * @param argument
     * @return 
     */
    private Requirement createRequirement(ERequirementClass requirementClass, String argument)
    {
        Requirement r = null;
        switch (requirementClass)
        {
            case DockerRequirement: 
                r = new DockerRequirement(argument); 
                break;
        }
        
        return r;
    }
    
    
    
    
    /**
     * Adds a simple input field to the list of inputs.
     * @param position
     * @param id
     * @param type
     * @param prefix
     * @param separate 
     */
    @Override
    public void addInput(int position, String id, String type, String prefix, Boolean separate) {
        SimpleInput input = new SimpleInput(position, id, type, prefix, separate);
        inputs.put(id, input);
    }
    
    
    
    
    /**
     * Adds an input of type "File" to the list of inputs.
     * @param position
     * @param id
     * @param type
     * @param prefix
     * @param separate
     * @param fileType 
     */
    @Override
    public void addInputFile(int position, String id, String prefix, Boolean separate, String fileType) {
        FileInput input = new FileInput(position, id, prefix, separate, fileType);
        this.inputs.put(id, input);
    }
    
    
    
    
    /**
     * Adds a simple input to the list of inputs, but checks also adds input requirements to it.
     * Input requirements can be minimum of maximum integer values.
     * 
     * /// SHOULD THIS REALLY BE USED??? ///
     * 
     * @param position
     * @param id
     * @param type
     * @param prefix
     * @param separate
     * @param min
     * @param max
     */
    @Override
    public void addInput(int position, String id, String type, String prefix, Boolean separate, int min, int max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    /**
     * Adds an output to the list of outputs.
     * @param id
     * @param type
     * @param glob 
     * @param format 
     */
    @Override
    public void addOutput(String id, String type, String glob, String format) {
        Output output = new Output(id, type, glob, format);
        outputs.put(id, output);
    }
    
    
    
    
    /**
     * Adds an Argument to the List of arguments.
     * @param position position where the argument should be placed in the command line command
     * @param argument the argument to be used
     */
    @Override
    public void addArgument(int position, String argument)
    {
        if (this.arguments == null)
        {
            this.arguments = new ArrayList<>();
        }
        
        Argument a = new Argument(position, argument);
        this.arguments.add(a);
    }
    
    
    
    
    /**
     * Set the BaseCommand of the Tool. This can be left out but the CWL 
     * implementation will then choose the fist argument or input as a 
     * substitute.
     * @param baseCommad 
     */
    @Override
    public void setBaseCommand(String baseCommad) 
    {
        this.baseCommand = baseCommad;
    }
    
    
    
    
    /**
     * Adds an input to the list of inputs which contains several input fields
     * which are exclusive to each other.
     * Those sub-input-fields will be created as boolean inputs but their prefix 
     * will contain the relevant information so that they act as switches which 
     * can be turned on or off when invoking the CWL tool.
     * @param position position at which the chosen option should be placed on the command line
     * @param id id of the input
     * @param options HashMap containing an identifier for each sub-input-field and the prefix to be used
     */
    @Override
    public void addExclusiveMultiFieldInput(int position, String id, HashMap<String, String> options) {
        MultiFieldInput mfi = new MultiFieldInput(position, id, options);
        this.inputs.put(id, mfi);
    }
    
    
    
    
    
}
