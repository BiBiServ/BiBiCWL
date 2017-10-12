/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.gui;



import de.unibi.cebitec.bibiworkflow.io.ConvertBs2ToCwlEventHandler;
import de.unibi.cebitec.bibiworkflow.io.OpenFileEventHandler;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


/**
 *
 * @author pol3waf
 */
public final class MainGui extends Application implements IMainGui
{
    private static final Logger LOGGER = Logger.getLogger(MainGui.class.getName());
    
    private static OpenFileEventHandler openFileEventHandler;
    private static ConvertBs2ToCwlEventHandler convertBs2ToCwlEventHandler;
    
    private Stage mainWindow;
    
    // Labels
    private Label welcomeLabel;
    private Label loadedFileLabel;
    
    // Buttons
    private Button startConversionButton;
    private Button openFileButton;
    
    // Scene
    private Scene scene;
    
    
    /**
     * Constructor for the MainGUI.
     */
    public MainGui()
    {
        
    }
    
    
    
    @Override
    public void launchGUI(OpenFileEventHandler ofeh, ConvertBs2ToCwlEventHandler ceh)
    {
        openFileEventHandler = ofeh;
        convertBs2ToCwlEventHandler = ceh;
        Application.launch(MainGui.class);
    }
    
    
    @Override
    public void start(Stage mainWindow)
    {
        setUpGui();
        mainWindow.setTitle("BibiCWL");
        mainWindow.setScene(this.scene);
        mainWindow.show();
    }

    
    
    
    
    @Override
    public void showGui(boolean shouldShow) {
        if (shouldShow)
        {
            this.mainWindow.show();
        }
        else
        {
            this.mainWindow.hide();
        }
    }

    
    /**
     * Set the open action.
     * @param eh 
     */
    @Override
    public void setOpenFileAction(final EventHandler<ActionEvent> eh) {
        try
        {
            this.openFileButton.setOnAction(eh);
        }
        catch (Exception e)
        {
            LOGGER.warning("Cant set action for button" + openFileButton.toString());
        }
    }
    

    @Override
    public void setSaveFileAction(final EventHandler<ActionEvent> eh) {
        
    }

    @Override
    public void setStartConversionAction(final EventHandler<ActionEvent> eh) {
        this.startConversionButton.setOnAction(eh);
    }
    
    
    
    /**
     * Set up the the whole GUI: populate it with labels, buttons and so on ...
     */
    private void setUpGui()
    {
        this.loadedFileLabel = new Label("-none-");
        this.welcomeLabel = new Label("welcome");
        
        this.openFileButton = new Button("openFile");
        this.startConversionButton = new Button("convert");
        
        this.setOpenFileAction(openFileEventHandler);
        this.setStartConversionAction(convertBs2ToCwlEventHandler);
        
        // set the layout
        FlowPane flowPane= new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setColumnHalignment(HPos.CENTER);
        
        // add stuff to layout (flowpane)
        flowPane.getChildren().addAll(
                this.welcomeLabel, 
                this.loadedFileLabel, 
                this.openFileButton,
                this.startConversionButton
        );
        
        this.scene = new Scene(flowPane, 200, 150);
    }
    
    
}
