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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    private Label statusInfo;
    
    // Buttons
    private Button startConversionButton;
    private Button openFileButton;
    
    // Textboxes
    private static TextArea documentView;
    
    
    // Scene
    private Scene scene;
    
    private static final double HGAP = 5;
    private static final double VGAP = 5;
    
    
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
        mainWindow.setMinWidth(500);
        mainWindow.setMinHeight(500);
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
    public void setStartConversionAction(final EventHandler<ActionEvent> eh) {
        this.startConversionButton.setOnAction(eh);
    }
    
    
    
    /**
     * Set up the the whole GUI: populate it with labels, buttons and so on ...
     */
    private void setUpGui()
    {
        
        /*
          basic layout
        */
        BorderPane borderPane = new BorderPane();
//        borderPane.setPadding(new Insets(10));
        
        HBox buttonPane = new HBox();
        FlowPane infoPane = new FlowPane();
        StackPane documentPane = new StackPane();           // why a stackpane ??? ????
        
        borderPane.setTop(buttonPane);
        borderPane.setBottom(infoPane);
        borderPane.setCenter(documentPane);
        
        
        /*
          document view area
        */
        documentView = new TextArea();
        documentView.setEditable(false);
        documentPane.setPadding(new Insets(10));
        documentPane.getChildren().add(documentView);
        
        
        /*
           button layout
        */
        buttonPane.setPadding(new Insets(10));
        buttonPane.setSpacing(10);
        buttonPane.setMaxWidth(Double.MAX_VALUE);
        
        // buttons and some labels
        
        this.openFileButton = new Button("openFile");
        this.startConversionButton = new Button("convert");
        
        this.openFileButton.setMaxWidth(Double.MAX_VALUE);
        this.startConversionButton.setMaxWidth(Double.MAX_VALUE);

        // set button actions
        this.setOpenFileAction(openFileEventHandler);
        this.setStartConversionAction(convertBs2ToCwlEventHandler);
        
        // add button-stuff to buttonFlowPane (flowpane)
        buttonPane.getChildren().addAll( 
                this.openFileButton,
                this.startConversionButton
        );
        
        
        /*
           status information bar
        */
        statusInfo = new Label("Status: ");
        infoPane.setPadding(new Insets(10));
        infoPane.getChildren().addAll(statusInfo);
        
        
        this.scene = new Scene(borderPane, 800, 600);
        borderPane.setPrefSize(scene.getWidth(), scene.getWidth());
    }

    
    
    @Override
    public void updateDocument(String document)
    {
        documentView.setText(document);
    }

    @Override
    public void setSaveFileAction(EventHandler<ActionEvent> eh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
