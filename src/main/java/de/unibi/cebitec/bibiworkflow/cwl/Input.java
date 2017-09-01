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
class Input {
    
    
    private String id;
    
    private String type;
    
    private InputBinding inputBinding;
    
    
    
    protected Input(String id, String type, InputBinding inputBinding)
    {
        this.id = id;
        this.type = type;
        this.inputBinding = inputBinding;
    }
    
    protected String getId()
    {
        // TODO
        return null;
    }
}
