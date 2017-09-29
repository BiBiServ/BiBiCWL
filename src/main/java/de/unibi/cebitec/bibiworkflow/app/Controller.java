/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.app;

import de.unibi.cebitec.bibiworkflow.converter.IConverter;
import de.unibi.cebitec.bibiworkflow.cwl.CwlTool;
import de.unibi.cebitec.bibiworkflow.gui.IMainGui;
import de.unibi.cebitec.bibiworkflow.io.ConvertBs2ToCwlEventHandler;
import de.unibi.cebitec.bibiworkflow.io.FileHandler;
import de.unibi.cebitec.bibiworkflow.io.OpenFileEventHandler;
import de.unibi.cebitec.bibiworkflow.io.YamlWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
public class Controller implements IControl {
    
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
    public void convertToCWL()
    {
        try {
            CwlTool cwlTool = converter.convertBs2ToCwlTool(this.fileHandler.convertBs2ToRunnableItem());
            YamlWriter ym = new YamlWriter();
            String yamlText = ym.writeObjectToYaml(cwlTool);
            System.out.println("\n\n----------" + yamlText + "----------\n\n");
//            fileHandler.writeStringToFile(yamlText);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
