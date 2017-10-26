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
class Output
{
    
//    @JsonProperty ... Dont use this property! The ID will be used as key in the HashMap which contains the outputs.
    private final String id;
    @JsonProperty
    private final String type;
    @JsonProperty
    private OutputBinding outputBinding;
    @JsonProperty
    private String format;
    
    
    
    /**
     * Create an Output object. There are some optional fields, which is not 
     * nicely designed, but should be OK for the moment ...
     * @param id ID of the Output
     * @param type type of the Output
     * @param glob name or glob pattern describing the output file (optional)
     * @param format format of the output file (optional)
     */
    protected Output(String id, String type, String glob, String format)
    {
        this.id = id;
        this.type = type;
        
        // only create an outputBinding if there is enough info ...
        if ( (glob != null) && (glob.length() > 0 ) )
        {
            this.outputBinding = new OutputBinding(glob);
        }
        
        // only fill in the format if it is specified ...
        if ( (format != null) && (format.length() > 0) )
        {
            this.format = format;
        }
    }
    
    
    
}
