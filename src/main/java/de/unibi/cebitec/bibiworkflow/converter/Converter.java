/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.converter;

import de.unibi.cebitec.bibiworkflow.bs2.Bs2Document;
import de.unibi.cebitec.bibiworkflow.bs2.IBs2Document;
import de.unibi.cebitec.bibiworkflow.cwl.CwlTool;
import java.net.URL;
import java.util.List;

/**
 *
 * @author pol3waf
 */
public class Converter implements IConverter {

    @Override
    public List<CwlTool> convertBs2ToCwl(String path) throws Exception {
        IBs2Document bs2doc = new Bs2Document(path);
        
        throw new NoSuchMethodException();
    }
    
    
    
}
