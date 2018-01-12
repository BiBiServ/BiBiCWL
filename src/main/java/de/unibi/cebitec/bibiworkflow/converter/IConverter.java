/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.converter;

import de.unibi.cebitec.bibiworkflow.app.IModelListener;
import de.unibi.cebitec.bibiworkflow.cwl.ICwlTool;
import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.util.HashMap;

/**
 *
 * @author pol3waf
 */
public interface IConverter {
    
    
    
    void addModelListener(IModelListener modelListener);
    void removeModelListener(IModelListener modelListener);
    
    HashMap<String, ICwlTool> convertBs2(TrunnableItem runnableItem) throws Exception;
    
    HashMap<String, ICwlTool> getCwlTools();
    
    void setOption_noShellQuote();
    
}
