/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import java.util.HashMap;

/**
 *
 * @author pol3waf
 */
public interface ICwlTool {
    
    String CWLVERSION = "v1.0";
    // rename parameter "CWLCLASS" into "class" ????
    String CWLCLASS = "CWLTool";


    
    
    
    /*
    
    ??????????????????????????????????????????????
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!?
    Add Enum for selecting requirement classes???
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!?
    ??????????????????????????????????????????????
    
    */
    
    public enum ERequirementClass
    {
        InlineJavascriptRequirement,
        SchemaDefRequirement,
        DockerRequirement,
        SoftwareRequirement,
        InitialWorkDirRequirement,
        EnvVarRequirement,
        ShellCommandRequirement,
        ResourceRequirement
    }
    
    
    /**
     * 
     * @param id
     * @param type
     * @param position
     * @param prefix
     * @param separate 
     */
    public void addInput(int position, String id, String type, String prefix, Boolean separate);
    
    public void addInputFile(int position, String id, String prefix, Boolean separate, String fileType);
    
    public void addInput(int position, String id, String type, String prefix, Boolean separate, int min, int max);
    
    
    /**
     * Adds an output to the outputs ArrayList of the the CWLTool.
     * (Probably other params should be added ... see CWL-Documentation)
     * @param id ID of the output - this will be the name for the field
     * @param type type of the output (stdout, File, ...)
     * @param glob glob-pattern for searching for files in the output of the called tool
     * @param format
     */
    public void addOutput(String id, String type, String glob, String format);
    
    
    /**
     * Adds a sub-class of Requirement to the Requirement array of the CWLTool.
     * @param requirementClass
     * @param argument
     */
    public void addRequirement(ERequirementClass requirementClass, String argument);
    
    
    
    /**
     * Adds a sub-class of Requirement to the hints list of the CWLTool.
     * @param requirementClass type of the requirement
     * @param argument the value which should be passed
     */
    public void addHint(ERequirementClass requirementClass, String argument);
    
    
    /**
     * Adds an argument to the CWLTool
     * @param argument 
     */
    public void addArgument(int position, String argument);
    
    
    /**
     * Sets the base command of the CWL-Tool. If a base command is set, the 
     * CWL-Tool will use it as first argument on the command line (to start a
     * a program/ workflow.
     * @param baseCommand 
     */
    public void setBaseCommand(String baseCommand);
    
    
    public void addExclusiveMultiFieldInput(int position, String id, HashMap<String, String> options);
    
    
    public void setupStdout(String inputReference);
    
    
    public void setUpOption_noShellQuote();
    
    public void setUpOption_optionalInputFiles();
    
    public void setUpOption_inputArray();

}
