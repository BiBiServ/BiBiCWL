/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author pol3waf
 */
public interface IMainGui {
    
    
    void setUpGui();
    
    void showGui(boolean shouldShow);
    
    void setOpenFileAction(EventHandler<ActionEvent> eh);
    
    void setSaveFileAction(EventHandler eh);
    
    void setStartConversionAction(EventHandler eh);
    
    
    
    
    
}
