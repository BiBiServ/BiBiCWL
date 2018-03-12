/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
public class FileInput extends Input
{
    
    private static final Logger LOGGER = Logger.getLogger(FileInput.class.getName());
    
    @JsonProperty
    protected String format;
    
    
    private final String defaultType = "File";
    private final String defaultArrayType = "File[]";
    
    
    private String elementSeparator = null;
    
    
    
    
    
    
    
    
    
    
    
    public FileInput(int position, String id, String prefix, Boolean separate, String fileType)
    {
        super.type = defaultType;
        super.id = id;
        super.inputBinding = new InputBinding(prefix, separate, position);
        this.format = fileType;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * This method looks at several key options like isShellQuote, isOptional
     * and isArrayInput and assembles them into a coherent representation of the
     * input.
     * This is done to avoid individual cross-checking the input's state every
     * time a option is activated or deactivated.
     */
    private void assembleInputParts()
    {
        // set the atomar type (which is either "File" or "File[]"
        String atomarType;
        if (this.isArrayInput)
        {
            atomarType = this.defaultArrayType;
            this.inputBinding.enableItemSeparator(this.elementSeparator);
        }
        else
        {
            atomarType = this.defaultType;
            this.inputBinding.disableItemSeparator();
        }
        
        // if isOptional is true, create an array of types with one type being 
        // "null" and the other being the atomar type
        if (this.isOptional)
        {
            ArrayList<String> typeArray = new ArrayList<>();
            typeArray.add("<null>");
            typeArray.add(atomarType);
            this.type = typeArray;
        }
        else
        {
            this.type = atomarType;
        }
        
    }
    
    
    
    
    
    
    
    @Override
    protected void enableOptional()
    {
        this.isOptional = true;
        this.assembleInputParts();
    }
    
    
    
    
    @Override
    protected void disableOptional()
    {
        this.isOptional = false;
        this.assembleInputParts();
    }
    
    
    
    
    @Override
    protected void enableShellQuote()
    {
        this.inputBinding.activateShellQuote();
    }
    
    
    
    
    @Override
    protected void disableShellQuote()
    {
        this.inputBinding.deactivateShellQuote();
    }
    
    
    
    
    
    /*
        OK ... now I am adding more functions and sometimes there are dependencies.
        FIX THIS!!!!!
        --> make one "assemble input parts"-function which takes in all the options
            and fills in the member variables (or variables marked with @jsonProperty)
            and run that function every time some stuff is changed.
    */
    
    
    @Override
    protected void enableArrayInput(String elememtSeparator)
    {
        this.elementSeparator = elememtSeparator;
        this.isArrayInput = true;
        this.assembleInputParts();
    }
    
    
    
    
    @Override
    protected void disableArrayInput()
    {
        this.elementSeparator = null;
        this.isArrayInput = false;
        this.assembleInputParts();
    }
}
