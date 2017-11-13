/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.app;

import de.unibi.cebitec.bibiworkflow.converter.IConverter;
import de.unibi.cebitec.bibiworkflow.cwl.ICwlTool;
import de.unibi.cebitec.bibiworkflow.gui.IMainGui;
import de.unibi.cebitec.bibiworkflow.io.ConvertBs2ToCwlEventHandler;
import de.unibi.cebitec.bibiworkflow.io.FileHandler;
import de.unibi.cebitec.bibiworkflow.io.OpenFileEventHandler;
import de.unibi.cebitec.bibiworkflow.converter.YamlWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
public class GuiControl implements IControl, IModelListener
{
    
    private static final Logger LOGGER = Logger.getLogger(GuiControl.class.getName());
    private final IConverter converter;
    private final IMainGui mainGui;
    private final FileHandler fileHandler;
    
    
    /**
     * Constructor for the Controller with a given GUI and 
     * @param converter Implementation of a IConverter
     * @param mainGui Implementation of IMainGUI
     */
    public GuiControl(IConverter converter, IMainGui mainGui)
    {
        this.converter = converter;
        this.mainGui = mainGui;
        this.fileHandler = new FileHandler();
        
        this.setUpGUI();
    }
    
    
    @Override
    public void convertBs2ToCWL()
    {
        try
        {
            
            LOGGER.info("Converting bs2-document to CwlTools ...");
            this.converter.convertBs2(this.fileHandler.importBs2AsRunnableItem() );
            
            
            LOGGER.info("Converting CwlTools to YAML ...");
            HashMap<String, ICwlTool> cwlTools = this.converter.getCwlTools();
            YamlWriter ym = new YamlWriter();
            HashMap<String, String> yamlCwlTools = ym.convertMultipleObjectsToYaml(cwlTools);
            
            LOGGER.info("Updating GUI ...");
            updateDocumentView(yamlCwlTools);
            
        }
        catch (Exception ex)
        {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    private void setUpGUI()
    {
        // create all event handlers and pass them to the GUI
        OpenFileEventHandler ofeh = new OpenFileEventHandler(this.fileHandler);
        ConvertBs2ToCwlEventHandler ceh = new ConvertBs2ToCwlEventHandler(this);
        
        this.mainGui.launchGUI(ofeh, ceh);
    }
    
    
    
    
    private String parseCwlToolsToYaml(ICwlTool cwlTool)
    {
        YamlWriter ym = new YamlWriter();
        String yamlText = ym.writeObjectToYaml(cwlTool);
        
        return yamlText;
    }
    
    
    
    
    
    
    /**
     * Updates the currently displayed document of the GUI.
     * @param document.
     */
    @Override
    public void documentHasChanged()
    {
        HashMap<String, ICwlTool> cwlTools = converter.getCwlTools();
//        String document = 
        // update notify the GUI to update the diplayed document
//        this.updateDocumentView(document);
    }
    
    
    
    
    @Override
    public void newDocumentCreated()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    /**
     * Creates a new document in the GUI. If a document already exists, a new 
     * one is created. (May be used to show different documents in tabs or 
     * whatever ... ...) 
     * @param document 
     */
    private void createNewDocumentView()
    {
        // update the GUI to display a new document
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    /**
     * Updates the GUI's displayed documents. The set of documents are 
     * concatenated into a combined document and passed to the GUI.
     * @param documents set of documents
     */
    private void updateDocumentView(HashMap<String, String> documents)
    {
        String concatenatedDocuments = "";
                
        documents.forEach( (name, document) ->
        {
            concatenatedDocuments.concat("\n\n" + name + "\n" + document);
        }
        );
        
        this.mainGui.updateDocument(concatenatedDocuments);
        System.out.println(concatenatedDocuments);
    }
    
    
    
    
    
}
