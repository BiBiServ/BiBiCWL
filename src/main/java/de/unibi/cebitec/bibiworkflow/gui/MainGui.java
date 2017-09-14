/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.gui;



import de.unibi.cebitec.bibiworkflow.control.OpenFileEventHandler;
import de.unibi.cebitec.bibiworkflow.fileio.FileHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
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
    private static OpenFileEventHandler openFileEventHandler;
    
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
    
    
    public static void launchGUI(OpenFileEventHandler ofeh)
    {
        openFileEventHandler = ofeh;
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
    public void setUpGui()
    {
        this.loadedFileLabel = new Label("-none-");
        this.welcomeLabel = new Label("welcome");
        
        this.openFileButton = new Button("openFile");
        this.startConversionButton = new Button("convert");
        
        this.setOpenFileAction(openFileEventHandler);
        
        FlowPane flowPane= new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);
        
        // add stuff to stackplane
        flowPane.getChildren().addAll(
                this.welcomeLabel, 
                this.loadedFileLabel, 
                this.openFileButton,
                this.startConversionButton
        );
        
        this.scene = new Scene(flowPane, 200, 300);
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

    
    @Override
    public void setOpenFileAction(final EventHandler<ActionEvent> eh) {
        try
        {
            this.openFileButton.setOnAction(eh);
        }
        catch (Exception e)
        {
            System.out.println("Cant set action for button" + openFileButton.toString());
        }
    }
    

    @Override
    public void setSaveFileAction(EventHandler eh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStartConversionAction(EventHandler eh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
