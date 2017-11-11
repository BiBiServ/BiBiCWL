/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.io;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author pol3waf
 */
public class OpenFileEventHandler implements javafx.event.EventHandler<ActionEvent>
{

    private final FileHandler fileHandler;
    
    
    /**
     * Constructor of the OpenFileEventHandler. It will forward the file gathered
     * to a FileHandler.
     * @param fh FileHandler which should receive the file
     */
    public OpenFileEventHandler(FileHandler fh) {
        this.fileHandler = fh;
    }
    
    

    @Override
    public void handle(ActionEvent event) {
        // Create a FileChooser and set it up.
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose Bs2 File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("bibiserv tool description file", ".bs2");
        fc.setSelectedExtensionFilter(filter);
        
        // Put the FileChooser into a new Stage and get the file
        Stage openFileStage = new Stage();
        openFileStage.setAlwaysOnTop(true);
        
        File file = fc.showOpenDialog(openFileStage);
        
        fileHandler.setInFile(file);
    }
    
    
    
    
}
