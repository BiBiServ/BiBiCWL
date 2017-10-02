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
    @JsonProperty
    protected String id;
    @JsonProperty
    protected T type;
    @JsonProperty
    protected InputBinding inputBinding;
    @JsonProperty
    protected String format;
    
    
    
    protected String getId()
    {
        return this.id;
    }
}
