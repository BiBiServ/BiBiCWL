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
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 *
 * @author pol3waf
 */
public class CmdControl implements IControl
{
    
    private static final Logger LOGGER = Logger.getLogger(GuiControl.class.getName());
    private final IConverter converter;
    private final FileHandler fileHandler;
    private final CommandLine cmd;
    
    
    
    
    
    public CmdControl(IConverter converter, CommandLine cmd)
    {
        this.converter = converter;
        this.cmd = cmd;
        this.fileHandler = new FileHandler();
        
        boolean isInputProvided = processInputArgument();
        if (isInputProvided)
        {
            LOGGER.info("Input accepted.");
        }
        else
        {
            LOGGER.severe("Input file needs to be provided in command line mode");
            exit(1);
        }
        
    }
    
    
    
    
    
    @Override
    public void convertBs2ToCWL()
    {
        try
        {
            
            LOGGER.info("Converting bs2-document to CwlTools ...");
            this.converter.convertBs2(this.fileHandler.importBs2AsRunnableItem() );
            
            
            LOGGER.info("Processing additional command line arguments if they exist ...");
            this.processAdditionalCommandLineArguments();
            
            
            LOGGER.info("Converting CwlTools to YAML ...");
            HashMap<String, ICwlTool> cwlTools = this.converter.getCwlTools();
            HashMap<String, String> yamlCwlTools = this.convertCwlToolsToYaml(cwlTools);
            
            
            LOGGER.info("Writing CWL-YAML-Tools into output directory " 
                    + this.fileHandler.getOutputDirectoryPath() + " ...");
            this.writeYamlCwlToolsToDisk(yamlCwlTools);
            
        }
        catch (Exception ex)
        {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    /**
     * Checks for additional command line arguments and processes them.
     */
    private void processAdditionalCommandLineArguments()
    {
        
        
        /*
            put the stuff below into separate methods because things might
            get messy ...
        */
        
        
        // check output
        if (this.cmd.hasOption("output"))
        {
            try {
                fileHandler.setOutDir(this.cmd.getOptionValue("output"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CmdControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        /*
            check requirements
                system requirements
                ???
        */
        
        
        /*
            check hints
                docker
                ???
        */
        
        
    }
    
    
    
    
    /**
     * Processes the input file if the argument is provided.
     * @return true if the input argument is provide, false if not
     */
    private boolean processInputArgument()
    {
        if (this.cmd.hasOption("input"))
        {
            try
            {
                this.fileHandler.setInFile(cmd.getOptionValue("input"));
            }
            catch (FileNotFoundException ex)
            {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return true;
        }
        else
        {
            LOGGER.severe("No input.");
            return false;
        }
    }
    
    
    
    
    /**
     * Converts a set of CwlTools into a set of YAML Strings.
     * @param cwlTools
     * @return 
     */
    private HashMap<String, String> convertCwlToolsToYaml(HashMap<String, ICwlTool> cwlTools)
    {
        HashMap<String, String> yamlCwlTools = new HashMap<>();
        
        cwlTools.forEach( (name, cwlTool) ->
        {
            YamlWriter ym = new YamlWriter();
            String yamlDocument = ym.writeObjectToYaml(cwlTool);
            LOGGER.info("\n\n" + name + "\n" + yamlDocument + "\n\n");
            yamlCwlTools.put(name, yamlDocument);
        }
        );
        
        return yamlCwlTools;
    }
    
    
    
    
    /**
     * Writes a set of YAML-CWL-Tools (or other strings) to an output directory.
     * @param yamlCwlTools 
     */
    private void writeYamlCwlToolsToDisk(HashMap<String, String> yamlCwlTools)
    {
        yamlCwlTools.forEach( (name, yamlCwlTool) ->
        {
            try {
                this.fileHandler.writeStringToFile(name, yamlCwlTool);
                LOGGER.info("Saved " + name + " to file: " + this.fileHandler.getOuputFilePath());
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        );
    }
    
    
    
    
    
}
