/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author pol3waf
 */
public class CommandInputRecord {
    
    @JsonProperty
    private final String type = "record";
    @JsonProperty
    private HashMap<String, Input> fields = new HashMap<>();
    
    
    protected CommandInputRecord(ArrayList<Input> inputs)
    {
        for (Input input : inputs)
        {
            fields.put(input.id, input);
        }
    }

    public CommandInputRecord(SimpleInput input)
    {
        fields.put(input.id, input);
    }
    
    
    protected void makeAllInputsOptional()
    {
        for (Input i : fields.values())
        {
            i.enableOptional();
        }
    }
    
    protected void makeAllInputsRequired()
    {
        for (Input i : fields.values())
        {
            i.disableOptional();
        }
    }

    void enableShellQuoteForAllInputs() {
        for (Input i : fields.values())
        {
            i.enableShellQuote();
        }
    }

    void disableShellQuoteForAllInputs() {
        for (Input i : fields.values())
        {
            i.disableShellQuote();
        }
    }
    
    
}
