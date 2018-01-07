/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Input with multiple fields which hold different values. Those
 * input fields are designed as boolean CWL inputs and the contained values
 * can therefore be toggled on or off when the CwlTool is invoked.
 * The input fields of the MultiFieldInput are also designed to be exclusive,
 * hence the use of one CommandInputRecord for each input field.
 * @author pol3waf
 */
public class MultiFieldInput extends Input<ArrayList<CommandInputRecord>>
{
    
    // dont serialize this!!!
    private final String INPUTTYPE = "boolean";                                     // should i really hard-code this ???

    private final static Logger LOGGER = Logger.getLogger(MultiFieldInput.class.getName());
    
    
    
    /**
     * Creates an input with multiple fields which hold different values. Those
     * input fields are designed as boolean CWL inputs and the contained values
     * can therefore be toggled on or off when the CwlTool is invoked.
     * The input fields of the MultiFieldInput are also designed to be exclusive,
     * hence the use of one CommandInputRecord for each input field.
     * @param position Position at which the MultiFieldInput fields should be put on the command line command
     * @param id ID of the CWL Input
     * @param options HashMap containing a identifier and the value (i.e. prefix) for each field
     */
    public MultiFieldInput(int position, String id, HashMap<String, String> options)
    {
        LOGGER.info("Creating MultiFieldInput. Remember to check if the 'type' field should really be an ArrayList");
        super.id = id;
        super.type = new ArrayList<>();
        this.addCommandInputRecords(position, options);
    }
    
    
    /**
     * Add CommnadInputRecords to the type ArrayList which contains all the 
     * fields of the multi-field input.
     * @param options 
     */
    private void addCommandInputRecords(int position, HashMap<String, String> options)
    {
        for (String key : options.keySet())
        {
            String name = key;
            String prefix = options.get(key);
            SimpleInput input = new SimpleInput(position, name, this.INPUTTYPE, prefix, false);
            CommandInputRecord cir = new CommandInputRecord(input);
            
            super.type.add(cir);
        }
    }
    
}
