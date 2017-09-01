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
class Output {
    
    private String id;
    
    private String type;
    
    private OutputBinding outputBinding;
    
    
    
    protected Output(String id, String type, OutputBinding outputBinding)
    {
        this.id = id;
        this.type = type;
        this.outputBinding = outputBinding;
    }
    
    
    protected String getId()
    {
        // TODO
        return null;
    }
}
