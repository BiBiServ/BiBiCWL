/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
class SimpleInput extends Input {
    
    private static final Logger LOGGER = Logger.getLogger(SimpleInput.class.getName());
    
    
    public SimpleInput(int position, String id, String type, String prefix, Boolean seprate)
    {
        super.id = id;
        super.type = type;
        super.inputBinding = new InputBinding(prefix, seprate, position);
    }

    @Override
    protected void enableOptional() {
        
        if (this.type instanceof String)
        {
            String oldType = (String) super.type;
            ArrayList<String> typesList = new ArrayList<>();
            typesList.add(oldType);
            typesList.add("<null>");
            this.type = typesList;
        }
        else if (this.type instanceof ArrayList)
        {
            ((ArrayList)this.type).add("<null>");
        }
    }

    @Override
    protected void disableOptional() {
        if (this.type instanceof ArrayList)
        {
            ((ArrayList)this.type).remove("<null>");
            
            // if there is only one element left in the list, convert type to a simple string
            if (((ArrayList)this.type).size() == 1)
            {
                this.type = ((ArrayList)this.type).get(0);
            }
        }
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
    
    
    
    
    
    
    @Override
    protected void enableArrayInput(String itemSeparator) {
        LOGGER.info("ArrayInputs not yet suported for SimpleInputs");
    }
    
    
    
    
    @Override
    protected void disableArrayInput() {
        LOGGER.info("ArrayInputs not yet suported for SimpleInputs");
    }
    
    
    
    
}
