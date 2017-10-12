/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
class InputBinding {
    
    private static final Logger LOGGER = Logger.getLogger(InputBinding.class.getName());
    
    @JsonProperty
    private String prefix;
    @JsonProperty
    private boolean separate;
    @JsonProperty
    private int position;
    
    
    protected InputBinding(String prefix, boolean separate, int position)
    {
        // prefix is optional ???
        if (prefix == null)
        {
            LOGGER.fine("no prefix used for this inputbinding");
        }
        else
        {
            this.prefix = prefix;
        }
        
        this.separate = separate;
        this.position = position;
    }
}
