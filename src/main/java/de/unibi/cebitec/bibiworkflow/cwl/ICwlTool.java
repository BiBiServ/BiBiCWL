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
    
    String cwlVersion = "v1.0";
    // rename parameter "cwlClass" into "class" ????
    String cwlClass = "CWLTool";
    
    
    public void addInput(String id, String type, InputBinding inputBinding);
    
    public void addOutput(String id, String type, InputBinding outputBinding);
    
    public <T> void addRequirement(String requirementClass, T attribute);
}
