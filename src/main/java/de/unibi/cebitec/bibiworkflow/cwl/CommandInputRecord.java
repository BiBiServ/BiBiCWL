/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import java.util.ArrayList;

/**
 *
 * @author pol3waf
 */
public class CommandInputRecord {
    
    private final String type = "record";
    private final ArrayList<SimpleInput> fields = new ArrayList<>();
    
    
    protected CommandInputRecord(ArrayList<SimpleInput> inputs)
    {
        fields.addAll(inputs);
    }

    public CommandInputRecord(SimpleInput input)
    {
        fields.add(input);
    }
    
    
    
}
