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
    @JsonProperty
    private Boolean shellQuote = null;
    
    
    
    protected Argument(int position, String value)
    {
        this.position = position;
        this.valueFrom = value;
    }
    
    
    protected void deactivateShellQuote()
    {
        this.shellQuote = false;
    }
    
    protected void activateShellQuote()
    {
        this.shellQuote = true;
    }
}
