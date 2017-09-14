/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.control;

import de.unibi.cebitec.bibiworkflow.fileio.FileHandler;
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
    
    public OpenFileEventHandler(FileHandler fh) {
        this.fileHandler = fh;
    }
    
    

    @Override
    public void handle(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose Bs2 File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("bibiserv tool description file", ".bs2");
        fc.setSelectedExtensionFilter(filter);
        
        Stage openFileStage = new Stage();
        File file = fc.showOpenDialog(openFileStage);
        fileHandler.setFile(file);
    }
    
    
    
    
}
