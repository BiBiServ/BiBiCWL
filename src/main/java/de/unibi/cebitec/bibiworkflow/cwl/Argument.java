/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author pol3waf
 */
class Argument
{
    
    
    @JsonProperty
    private final String valueFrom;
    @JsonProperty
    private final int position;
    
    
    
    protected Argument(int position, String value)
    {
        this.position = position;
        this.valueFrom = value;
    }
}
