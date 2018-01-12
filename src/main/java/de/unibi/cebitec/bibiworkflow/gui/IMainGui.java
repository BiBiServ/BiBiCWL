/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.gui;

import de.unibi.cebitec.bibiworkflow.io.ConvertBs2ToCwlEventHandler;
import de.unibi.cebitec.bibiworkflow.io.OpenFileEventHandler;
import de.unibi.cebitec.bibiworkflow.io.SaveToDirectoryEventHandler;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author pol3waf
 */
public interface IMainGui {
    
    /**
     * launch the GUI. This provide the GUI with every EventHandler needed and
     * place them in static variables. The GUI will be called statically as 
     * well.
     * @param oh EventHandler for opening files
     * @param ch EventHandler for converting files
     * @param sh
     */
    void launchGUI(OpenFileEventHandler oh, ConvertBs2ToCwlEventHandler ch, SaveToDirectoryEventHandler sh);
    
    /**
     *
     * @param shouldShow
     */
    void showGui(boolean shouldShow);
    
    /**
     *
     * @param eh
     */
    void setOpenFileAction(final EventHandler<ActionEvent> eh);
    
    /**
     *
     * @param eh
     */
    void setSaveFileAction(final EventHandler<ActionEvent> eh);
    
    /**
     *
     * @param eh
     */
    void setStartConversionAction(final EventHandler<ActionEvent> eh);
    
    /**
     * 
     * @param saveToDirectoryEventHandler 
     */
    void setSaveToDirectoryAction(SaveToDirectoryEventHandler saveToDirectoryEventHandler);
    
    
    /**
     * Updates the document view. Takes one document.
     * @param document a document to be displayed
     */
    void updateDocument(String document);
    
    
    /**
     * Updates the document view. Takes multiple documents.
     * @param documents set of documents to be displayed
     */
    void updateDocument(HashMap<String, String> documents);
    
    
    /**
     * Get the options from the GUI in a nice HashMap.
     * @return 
     */
    HashMap<String, String> getOptions();
    
}
