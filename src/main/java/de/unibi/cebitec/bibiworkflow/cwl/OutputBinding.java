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
class OutputBinding
{
    
    @JsonProperty
    private final String glob;
    
    // what is this???
    @JsonProperty
    private final Boolean loadContent;
    
    // what is this???
    @JsonProperty
    private final String outputEval;
    
    
    
    protected OutputBinding(final String glob, final boolean loadContent, final String outputEval)
    {
        this.glob = glob;
        this.loadContent = loadContent;
        this.outputEval = outputEval;
    }
    
    protected OutputBinding(final String glob)
    {
        this.glob = glob;
        this.loadContent = null;
        this.outputEval = null;
    }
    
}
