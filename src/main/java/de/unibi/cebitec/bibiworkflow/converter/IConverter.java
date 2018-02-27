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
    
    
    /**
     * Adds a listener for the model to the Converter
     * @param modelListener 
     */
    void addModelListener(IModelListener modelListener);
    
    /**
     * Removes a listener of the model from the Converter.
     * @param modelListener 
     */
    void removeModelListener(IModelListener modelListener);
    
    /**
     * Converts a TrunnableItem (root-bibi-abstraction element) to a CWL tool.
     * @param runnableItem
     * @return A HashMap with CWL tools. The key is the CWL tool's name.
     * @throws Exception 
     */
    HashMap<String, ICwlTool> convertBs2(TrunnableItem runnableItem) throws Exception;
    
    /**
     * Returns the Converter's HashMap of CWL tools.
     * @return HashMap containing CWL tools.
     */
    HashMap<String, ICwlTool> getCwlTools();
    
    /**
     * Activates CWL's noShellQuote option for the CWL tools
     */
    void setOption_noShellQuote();
    
    /**
     * Makes CWL inputs optional
     */
    void setOption_optionalInputFiles();
    
    /**
     * Converts the file inputs of a CWL tool into array inputs.
     * @param elementSeparator 
     */
    void setOption_arrayFileInputs(String elementSeparator);
    
    /**
     * Adds CWL directory outputs in addition to the File outputs so that
     * the output files are nested inside their original directory structure.
     */
    @Deprecated
    void setOption_useDirectoryOutputs();
    
}
