/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author pol3waf
 */
public class MultiFieldInput extends Input
{
    private final ArrayList<CommandInputRecord> type = new ArrayList<>();
    
    private final String inputType = "boolean";
    
    
    
    
    /*
    Eventually this has to be handled differently ... give this class a list of inputs and let it decide how to use it ...
    */
    public MultiFieldInput(int position, String id, HashMap<String, String> options)
    {
        super.id = id;
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
            SimpleInput input = new SimpleInput(position, name, this.inputType, prefix, false);
            CommandInputRecord cir = new CommandInputRecord(input);
        }
    }
    
}
