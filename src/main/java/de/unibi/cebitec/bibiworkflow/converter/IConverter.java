/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.converter;

import de.unibi.cebitec.bibiworkflow.cwl.CwlTool;
import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author pol3waf
 */
public interface IConverter {
    
    
    
    public HashMap<String, CwlTool> convertBs2(TrunnableItem runnableItem) throws Exception;
    
}
