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
class InputBinding {
    
    
    private String prefix;
    
    private boolean separate;
    
    private int position;
    
    
    protected InputBinding(String prefix, boolean separate, int position)
    {
        this.prefix = prefix;
        this.separate = separate;
        this.position = position;
    }
}
