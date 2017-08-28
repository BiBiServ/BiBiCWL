/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

/**
 *
 * @author pol3waf
 * @param T bla ?????????????
 */
public interface ICwlTool<T> {
    
    String CWLVERSION = "v1.0";
    // rename parameter "CWLCLASS" into "class" ????
    String CWLCLASS = "CWLTool";
    
    
    public void addInput(String id, String type, InputBinding inputBinding);
    
    public void addOutput(String id, String type, InputBinding outputBinding);
    
    public <T> void addRequirement(String requirementClass, T attribute);
}
