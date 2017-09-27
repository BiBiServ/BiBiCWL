/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

/**
 *
 * @author pol3waf
 */
class SimpleInput extends Input {
    
    
    public SimpleInput(int position, String id, String type, String prefix, boolean seprate)
    {
        super.id = id;
        super.type = type;
        super.inputBinding = new InputBinding(prefix, seprate, position);
    }
    
}
