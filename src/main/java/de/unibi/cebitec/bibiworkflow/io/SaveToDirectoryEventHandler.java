/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.io;

import de.unibi.cebitec.bibiworkflow.app.GuiControl;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author pol3waf
 */
public class SaveToDirectoryEventHandler implements javafx.event.EventHandler<ActionEvent>
{
    
    private final GuiControl controller;
    private final FileHandler fileHandler;
    
    
    
    
    public SaveToDirectoryEventHandler(GuiControl controller, FileHandler filehandler)
    {
        this.controller = controller;
        this.fileHandler = filehandler;
    }
    
    
    @Override
    public void handle(ActionEvent event)
    {
        // Create a DirectoryChooser and set it up.
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choose output directory");
        
        // Put the DirectoryChooser into a new Stage and get the file (=directory)
        Stage openFileStage = new Stage();
        openFileStage.setAlwaysOnTop(true);
        File directory = dc.showDialog(openFileStage);
        
        // set the output directory of the fileHandler
        fileHandler.setOutDir(directory);
        
        // tell the controller to save everything (convert to YAML and write to file(s))
        this.controller.saveCwlTools();
    }
    
    
    
    
}
