/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.converter;

import de.unibi.cebitec.bibiworkflow.bs2.Bs2Document;
import de.unibi.cebitec.bibiworkflow.bs2.IBs2Document;
import de.unibi.cebitec.bibiworkflow.cwl.CwlTool;
import java.util.List;

/**
 *
 * @author pol3waf
 */
public class Converter implements IConverter {

    /**
     * Convert the whole bs2-file (located at the given path) into a single 
     * CWL-Tool. There will be another function which will create a workflow out
     * of CWL-Tools.
     * @param path Path to the bs2-file in the file system
     * @return returns a CWL-Tool object
     * @throws Exception if Conversion fails
     */
    @Override
    public List<CwlTool> convertBs2ToCwlTool(String path) throws Exception {
        IBs2Document bs2doc = new Bs2Document(path);
        
        throw new NoSuchMethodException("Still a dummy ... remove this line and put a return statement here");
    }
    
    
    
}
