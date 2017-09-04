/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.converter;

import de.unibi.cebitec.bibiworkflow.cwl.CwlTool;
import de.unibi.techfak.bibiserv.cms.Tfunction;
import java.net.URL;
import java.util.List;

/**
 *
 * @author pol3waf
 */
public interface IConverter {
    
    
    
    public List<CwlTool> convertBs2ToCwl(String path) throws Exception;
    
}
