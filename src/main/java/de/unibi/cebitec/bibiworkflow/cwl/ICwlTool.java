/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

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
    
    public enum RequirementClass
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
    public void addInput(String id, String type, int position, String prefix, boolean separate);
    
    
    /**
     * Adds an output to the outputs ArrayList of the the CWLTool.
     * (Probably other params should be added ... see CWL-Documentation)
     * @param id ID of the output - this will be the name for the field
     * @param type type of the output (stdout, File, ...)
     * @param glob glob-pattern for searching for files in the output of the called tool
     */
    public void addOutput(String id, String type, String glob);
    
    
    /**
     * Adds a sub-class of Requirement to the Requirement array of the CWLTool.
     * @param requirementClass
     */
    public void addRequirement(RequirementClass requirementClass);
    
    
    /**
     * Adds an argument to the CWLTool
     * @param argument 
     */
    public void addArgument(String argument);
    
    
    /**
     * Sets the base command of the CWL-Tool. If a base command is set, the 
     * CWL-Tool will use it as first argument on the command line (to start a
     * a program/ workflow.
     * @param baseCommand 
     */
    public void setBaseCommand(String baseCommand);
}
