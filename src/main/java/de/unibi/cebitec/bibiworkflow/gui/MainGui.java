/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.gui;



import de.unibi.cebitec.bibiworkflow.io.ConvertBs2ToCwlEventHandler;
import de.unibi.cebitec.bibiworkflow.io.OpenFileEventHandler;
import de.unibi.cebitec.bibiworkflow.io.SaveToDirectoryEventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    private static SaveToDirectoryEventHandler saveToDirectoryEventHandler;
    
    private Stage mainWindow;
    
    // Labels
    private Label statusInfo;
    
    // Buttons
    private Button startConversionButton;
    private Button openFileButton;
    private Button saveToDirectoryButton;
    
    // Options
    private static ArrayList<Control> options = new ArrayList<>();
    
    // Textboxes
    private static TextArea documentView;
    private static TextArea notification_field;
    
    
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
    public void launchGUI(OpenFileEventHandler oh, ConvertBs2ToCwlEventHandler ch, SaveToDirectoryEventHandler sh)
    {
        openFileEventHandler = oh;
        convertBs2ToCwlEventHandler = ch;
        saveToDirectoryEventHandler = sh;
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
    
    
    
    
    
    @Override
    public void updateDocument(String document)
    {
        documentView.setText(document);
    }
    
    
    
    
    @Override
    public void updateDocument(HashMap<String, String> documents)
    {
        String concatenatedDocuments = "";
        for (String name : documents.keySet())
        {
            concatenatedDocuments = concatenatedDocuments.concat("# " + name + "\n\n" + documents.get(name) + "\n\n\n\n");
        }
        
//        documentView.setText(documents.toString());
        documentView.setText(concatenatedDocuments);
    }
    
    
    
    
    @Override
    public void setSaveFileAction(EventHandler<ActionEvent> eh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    @Override
    public void setSaveToDirectoryAction(SaveToDirectoryEventHandler saveToDirectoryEventHandler) {
        this.saveToDirectoryButton.setOnAction(saveToDirectoryEventHandler);
    }
    
    
    
    
    @Override
    public HashMap<String, String> getOptions()
    {
        HashMap<String, String> optionsMap = new HashMap<>();
        for (Control option : options)
        {
            String key;
            String value;
            
            if (option instanceof CheckBox)
            {
                key = ((CheckBox)option).getText();
                value = ((Boolean)((CheckBox) option).isSelected()).toString();
                optionsMap.put(key, value);
            }
            
            /*
                This has to be changed ... value is hardcoded for now, since there
                are no other textfields planned (for the moment).
                the options panel should be changed in general ...
            */
            else if (option instanceof TextField)
            {
                key = "itemSeparator";
                value = ((TextField)option).getText();
                optionsMap.put(key, value);
            }
            
            // TODO: add more ...
        }
        
        return optionsMap;
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
        VBox optionsPane = new VBox();
        
        borderPane.setTop(buttonPane);
        borderPane.setLeft(optionsPane);
        borderPane.setCenter(documentPane);
        borderPane.setBottom(infoPane);
        
        
        /*
          document view area
        */
        documentView = new TextArea();
        documentView.setEditable(false);
        documentView.setFont(Font.font("Monospaced", 13));
        documentPane.setPadding(new Insets(10));
        documentPane.getChildren().add(documentView);
        
        
        /*
           button layout
        */
        buttonPane.setPadding(new Insets(10));
        buttonPane.setSpacing(10);
        buttonPane.setMaxWidth(Double.MAX_VALUE);
        
        // buttons and some labels
        
        this.openFileButton = new Button("Open BS2 File");
        this.startConversionButton = new Button("Convert to CWL");
        this.saveToDirectoryButton = new Button("Save to Directory");
        
        this.openFileButton.setMaxWidth(Double.MAX_VALUE);
        this.startConversionButton.setMaxWidth(Double.MAX_VALUE);
        this.saveToDirectoryButton.setMaxWidth(Double.MAX_VALUE);

        // set button actions
        this.setOpenFileAction(openFileEventHandler);
        this.setStartConversionAction(convertBs2ToCwlEventHandler);
        this.setSaveToDirectoryAction(saveToDirectoryEventHandler);
        
        // add button-stuff to buttonFlowPane (flowpane)
        buttonPane.getChildren().addAll( 
                this.openFileButton,
                this.startConversionButton,
                this.saveToDirectoryButton
        );
        
        
        
        /*
            Options (equivalent to some of the commandline options)
        */
        // formatting
        optionsPane.setPadding(new Insets(10));
        Label optionsTitle = new Label("Options:");
        
        // options ...
        CheckBox checkbox_noShellQuotes = new CheckBox("noShellQuote");
        options.add(checkbox_noShellQuotes);
        CheckBox checkbox_optionalInputs = new CheckBox("optionalInputs");
        options.add(checkbox_optionalInputs);
        CheckBox checkbox_arrayFileInputs = new CheckBox("arrayFileInputs");
        options.add(checkbox_arrayFileInputs);
        Label separatorLabel = new Label("item separator:");
        options.add(separatorLabel);
        TextField itemSeparator = new TextField("separator");
        itemSeparator.setMaxWidth(100);
        options.add(itemSeparator);
        
        // add everything to the options pane
        for (Control e : options)
        {
            optionsPane.getChildren().add(e);
        }
        
        
        
        
        /*
           status information bar
        */
        statusInfo = new Label("Status: ");
//        notification_field = new TextArea();
//        notification_field.setMinHeight(20);
//        notification_field.setMaxHeight(500);
//        notification_field.set;
//        notification_field.setFont(Font.font("Monospaced", 13));
        infoPane.setPadding(new Insets(10));
        infoPane.setMinHeight(10);
        infoPane.setMaxHeight(400);
        infoPane.getChildren().addAll(statusInfo);
        
        
        this.scene = new Scene(borderPane, 800, 600);
        borderPane.setPrefSize(scene.getWidth(), scene.getWidth());
    }
    
    
    
    
    
    
}
