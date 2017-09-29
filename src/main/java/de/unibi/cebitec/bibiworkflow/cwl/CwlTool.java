/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
public class CwlTool implements ICwlTool {

    
    private String cwlVersion;  //make this a enum? why? --> name mismatching cwl defenition
    private String cwlClass;    //make this a enum? why? --> name mismatching cwl defenition
    
    private String baseCommand;
    
    private String stdout;
    
    
    /**
     * _List_ of inputs of the CWL-Tool. This member variable will be read when 
     * writing the CWL-Tool into a file.
     */
    private ArrayList<Input> inputs;
    /**
     * _List_ of outputs of the CWL-Tool. This member variable will be read when 
     * writing the CWL-Tool into a file.
     */
    private ArrayList<Output> outputs;
    
    /**
     * _Array_ of requirements of the CWL-Tool. This member variable will be read when 
     * writing the CWL-Tool into a file.
     */
    private Requirement[] requirements;
    
    /**
     * List of requirements which will be converted into an array when writing 
     * the CWL-Tool into a file.
     */
    private ArrayList<Requirement> requirementsList;
    
    
    private Argument[] arguments;
    
    private ArrayList<Argument> argumentList;
    
    
    
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
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.requirements = null;
        this.requirementsList = new ArrayList<>();
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
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.requirements = null;
        this.requirementsList = new ArrayList<>();
    }
    
    
    
    /**
     * 
     * @param requirementClassEnum 
     */
    @Override
    public void addRequirement(RequirementClass requirementClassEnum) {
        try {
            // create the Requirement SubClass depending on the enum given by 
            // creating the required constructor and then creating the instance
            Requirement requirement = (Requirement) Class.forName(requirementClassEnum.name()).newInstance();
            this.requirementsList.add(requirement);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(CwlTool.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void addInput(int position, String id, String type, String prefix, boolean separate) {
        SimpleInput input = new SimpleInput(position, id, type, prefix, separate);
        inputs.add(input);
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
    public void addInputFile(int position, String id, String prefix, boolean separate, String fileType) {
        FileInput input = new FileInput(position, id, prefix, separate, fileType);
        this.inputs.add(input);
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
    public void addInput(int position, String id, String type, String prefix, boolean separate, int min, int max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    /**
     * Adds an output to the list of outputs.
     * @param id
     * @param type
     * @param glob 
     */
    @Override
    public void addOutput(String id, String type, String glob) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    /**
     * Adds an Argument to the List of arguments.
     * @param position position where the argument should be placed in the command line command
     * @param argument the argument to be used
     */
    @Override
    public void addArgument(int position, String argument)
    {
        Argument a = new Argument(position, argument);
        this.argumentList.add(a);
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
    public void addExclusiveMultiFieldInput(int position, String id, HashMap<String, String> options) {
        MultiFieldInput mfi = new MultiFieldInput(position, id, options);
        this.inputs.add(mfi);
    }
  
    
    
}
