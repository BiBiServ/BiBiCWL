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
abstract class Input <T>
{
//    @JsonProperty
    protected String id;
    
    @JsonProperty
    protected T type;
    @JsonProperty
    protected InputBinding inputBinding;
    @JsonProperty
    protected String format;

    
    
    protected boolean isOptional = false;
    protected boolean isShellQuote = false;
    protected boolean isArrayInput = false;
    
    
    protected String getId()
    {
        return this.id;
    }
    
    
    
    abstract protected void enableOptional();
    
    abstract protected void disableOptional();
    
    abstract protected void enableShellQuote();

    abstract protected void disableShellQuote();

    abstract protected void enableArrayInput(String elementSeparator);
    
    abstract protected void disableArrayInput();
}
