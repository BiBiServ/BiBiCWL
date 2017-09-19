/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.converter;

import de.unibi.cebitec.bibiworkflow.bs2.Bs2Document;
import de.unibi.cebitec.bibiworkflow.bs2.IBs2Document;
import de.unibi.cebitec.bibiworkflow.cwl.CwlTool;
import de.unibi.techfak.bibiserv.cms.Tfunction;
import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.util.List;

/**
 *
 * @author pol3waf
 */
public class Converter implements IConverter {

    private Bs2Document bs2doc;
    
    
    /**
     * Convert the whole bs2 document into a single CWL-Tool. 
     * There will be another function which will create a workflow out
     * of CWL-Tools.
     * @param runnableItem
     * @return returns a CWL-Tool object
     * @throws Exception if Conversion fails
     */
    @Override
    public CwlTool convertBs2ToCwlTool(TrunnableItem runnableItem) throws Exception
    {
        bs2doc = new Bs2Document(runnableItem);
        
        // testing some stuff
        Tfunction function = bs2doc.getFunctions().get(0);
        String test_output = ""; 
        for (String s : bs2doc.getCommandLineArgumentOrderAsReferences(function))
        {
            test_output += "\n" + s;
        }
        System.out.println(test_output);
        
        // DO SOMETHING !!!
        return null;
    }
    
    
    
}
