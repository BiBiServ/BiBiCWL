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
public class FileInput extends Input
{
    @JsonProperty
    protected String fileType;
    
    public FileInput(int position, String id, String prefix, Boolean separate, String fileType)
    {
        super.type = "File";
        super.id = id;
        super.inputBinding = new InputBinding(prefix, separate, position);
        this.fileType = fileType;
    }
    
    
    
    
    protected void allowNull()
    {
        /*
            TODO:   the "<null>" thingy should be changed in the future. 
                    For now it is used to sort out the formatting of the 
                    "null"-string issue when converting the objects to YAML
                    by calling an extra function in the YAMLConverter:
                    
                    fixNullQuotes(String yamldocument)
        */
        this.type = new String[] {"<null>", "File"};
    }
    
    protected void doNotAllowNull()
    {
        this.type = "File";
    }
}
