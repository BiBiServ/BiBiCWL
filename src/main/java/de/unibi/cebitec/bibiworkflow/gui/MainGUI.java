/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.gui;



import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author pol3waf
 */
public class MainGUI extends Application
{
    private Stage mainWindow;
    
    
    /**
     * Constructor for the MainGUI. It will launch the JavaFX-Application.
     * @param args Command line arguments (those from the "main" method)
     */
    public MainGUI()
    {
        
    }
    
    
    public void startMainGUI(String[] args)
    {
        launch(args);
    }
    
    
    @Override
    public void start(Stage mainWindow)
    {
        
        // create some labels and buttons
        Label welcomeText = new Label("Hello word!");
        Label changingText = new Label("");
        
        Button convertBs2ToCwlTool = new Button("Convert bs2 file to CWL-Tool");
        convertBs2ToCwlTool.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Button was clicked!");
                FileChooser fc = new FileChooser();
                File f = fc.showOpenDialog(mainWindow);
            }
        });
        
        // general stuff
        mainWindow.setTitle("BibiCWL");
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(convertBs2ToCwlTool);
        Scene scene = new Scene(stackPane);
        mainWindow.setScene(scene);
        
        
        // show this window and its content
        mainWindow.show();
    }
    
    
}
