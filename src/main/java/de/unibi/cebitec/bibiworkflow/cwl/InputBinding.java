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
    private String prefix = null;
    @JsonProperty
    private Boolean separate = null;
    @JsonProperty
    private int position;
    
    // itemSeparator is only needed if the input field is an array-input.
    @JsonProperty
    protected String itemSeparator = null;
    
    // shellQuote is true by default in CWL. (null would be treated as true)
    @JsonProperty
    private Boolean shellQuote = null;
    
    
    protected InputBinding(String prefix, Boolean separate, int position)
    {
        
        
        if (separate == null)
        {
            LOGGER.fine("no separator for the prefix");
        }
        else
        {
            this.separate = separate;
        }
        
        
        // prefix is optional ???
        if (prefix == null || prefix.equals("") || prefix.equals(" "))
        {
            LOGGER.fine("no prefix used for this inputbinding");
            this.separate = null;
        }
        else
        {
            this.prefix = prefix;
        }
        
        this.position = position;
    }
    
    
    
    
    protected void deactivateShellQuote()
    {
        this.shellQuote = false;
    }
    
    protected void activateShellQuote()
    {
        this.shellQuote = true;
    }
    
    
    protected void enableItemSeparator(String itemSeparator)
    {
        this.itemSeparator = itemSeparator;
    }
    
    protected void disableItemSeparator()
    {
        this.itemSeparator = null;
    }
}
