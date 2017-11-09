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
import de.unibi.cebitec.bibiworkflow.io.YamlWriter;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
public class Controller implements IControl, IModelListener
{
    
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private final IConverter converter;
    private final IMainGui mainGui;
    private final FileHandler fileHandler;
    
    
    /**
     * Constructor for the Controller with a given GUI and 
     * @param converter Implementation of a IConverter
     * @param mainGui Implementation of IMainGUI
     */
    public Controller(IConverter converter, IMainGui mainGui)
    {
        this.converter = converter;
        this.mainGui = mainGui;
        this.fileHandler = new FileHandler();
        
        this.setUpGUI();
    }
    
    private void setUpGUI()
    {
        // create all event handlers and pass them to the GUI
        OpenFileEventHandler ofeh = new OpenFileEventHandler(this.fileHandler);
        ConvertBs2ToCwlEventHandler ceh = new ConvertBs2ToCwlEventHandler(this);
        
        this.mainGui.launchGUI(ofeh, ceh);
    }
    
    
    @Override
    public void convertBs2ToCWL()
    {
        try
        {
            LOGGER.info("Start conversion of bs2 to CWL Tool ...");
            HashMap<String, ICwlTool> cwlTools = converter.convertBs2(this.fileHandler.convertBs2ToRunnableItem());
            HashMap<String, String> yamlCwlTools = new HashMap<>();
            
            LOGGER.info("Start conversion to YAML ...");
            YamlWriter ym = new YamlWriter();
            
            // write each cwlTool to a separate file
            
            cwlTools.forEach((name, cwlTool) -> {
                String yamlDocument = parseCwlToolsToYaml(cwlTool);
                LOGGER.info("\n\n" + name + "\n" + yamlDocument + "\n\n");
                yamlCwlTools.put(name, yamlDocument);
                
                // temporary ... delete this
                updateDocumentView(yamlDocument);
                
            });
//            
//            for (String toolName : cwlTools.keySet())
//            {
//                
//                parseCwlToolsToYaml(toolName, cwlTool);
//                String fileName = toolName;
//                ICwlTool cwlTool = cwlTools.get(toolName);
//                String yamlText = ym.writeObjectToYaml(cwlTool);
//                LOGGER.info("\n\n" + fileName + "\n" + yamlText + "\n\n");
////                fileHandler.writeStringToFile(yamlText, fileName);
//            }
        }
        catch (Exception ex)
        {
            LOGGER.log(Level.SEVERE, null, ex);
        }
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
     * Updates the GUI's displayed document.
     * @param document
     */
    private void updateDocumentView(String document) {
        this.mainGui.updateDocument(document);
    }
    
    
    
}
