/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.app;

import de.unibi.cebitec.bibiworkflow.converter.IConverter;
import de.unibi.cebitec.bibiworkflow.cwl.ICwlTool;
import de.unibi.cebitec.bibiworkflow.io.FileHandler;
import de.unibi.cebitec.bibiworkflow.io.YamlWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
public class CmdControl implements IControl {

    private static final Logger LOGGER = Logger.getLogger(GuiControl.class.getName());
    private final IConverter converter;
    private final FileHandler fileHandler;
    
    
    
    public CmdControl(IConverter converter)
    {
        this.converter = converter;
        this.fileHandler = new FileHandler();

    }
    
    
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
    
}
