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
class Argument {
    
    private final String valueFrom;
    private final int position;
    
    
    
    protected Argument(int position, String value)
    {
        this.position = position;
        this.valueFrom = value;
    }
}
