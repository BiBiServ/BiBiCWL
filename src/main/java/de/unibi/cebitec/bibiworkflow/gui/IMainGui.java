/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.gui;

import de.unibi.cebitec.bibiworkflow.io.ConvertBs2ToCwlEventHandler;
import de.unibi.cebitec.bibiworkflow.io.OpenFileEventHandler;
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
     * @param ofeh EventHandler for opening files
     * @param ceh EventHandler for converting files
     */
    void launchGUI(OpenFileEventHandler ofeh, ConvertBs2ToCwlEventHandler ceh);
    
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
    
    
    
    
    
}
