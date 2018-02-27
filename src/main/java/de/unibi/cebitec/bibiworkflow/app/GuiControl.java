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
import de.unibi.cebitec.bibiworkflow.io.SaveToDirectoryEventHandler;
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
            
            // process options / arguments ... same as command line stuff or similar ...
            HashMap<String, String> options = this.mainGui.getOptions();
            this.processArguments(options);
            
            LOGGER.info("Converting CwlTools to YAML ...");
            HashMap<String, ICwlTool> cwlTools = this.converter.getCwlTools();
            YamlWriter ym = new YamlWriter();
            HashMap<String, String> yamlCwlTools = ym.convertMultipleObjectsToYaml(cwlTools);
            
            LOGGER.info("Updating GUI ...");
            this.mainGui.updateDocument(yamlCwlTools);
            
        }
        catch (Exception ex)
        {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    @Override
    public void saveCwlTools()
    {
        LOGGER.info("Converting CwlTools to YAML ...");
        HashMap<String, ICwlTool> cwlTools = this.converter.getCwlTools();
        YamlWriter ym = new YamlWriter();
        HashMap<String, String> yamlCwlTools = ym.convertMultipleObjectsToYaml(cwlTools);


        LOGGER.info("Writing CWL-YAML-Tools into output directory " 
                + this.fileHandler.getOutputDirectoryPath() + " ...");
        this.fileHandler.writeMultipleStringsToDisk(yamlCwlTools);
    }
    
    
    
    
    
    private void setUpGUI()
    {
        // create all event handlers and pass them to the GUI
        OpenFileEventHandler ofeh = new OpenFileEventHandler(this.fileHandler);
        ConvertBs2ToCwlEventHandler ceh = new ConvertBs2ToCwlEventHandler(this);
        SaveToDirectoryEventHandler s = new SaveToDirectoryEventHandler(this, fileHandler);
        
        this.mainGui.launchGUI(ofeh, ceh, s);
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
    
    
    
    
    private void processArguments(HashMap<String, String> options)
    {
        if (options.get("noShellQuote").equals("true"))
        {
            converter.setOption_noShellQuote();
        }
        if (options.get("optionalInputs").equals("true"))
        {
            converter.setOption_optionalInputFiles();
        }
        if (options.get("arrayFileInputs").equals("true"))
        {
            String itemSeparator = options.get("itemSeparator");
            converter.setOption_arrayFileInputs(itemSeparator);
        }
    }
    
    
    
    
    
    
    
    
}
