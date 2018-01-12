/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.app;

import de.unibi.cebitec.bibiworkflow.converter.IConverter;
import de.unibi.cebitec.bibiworkflow.cwl.ICwlTool;
import de.unibi.cebitec.bibiworkflow.io.FileHandler;
import de.unibi.cebitec.bibiworkflow.converter.YamlWriter;
import java.io.FileNotFoundException;
import static java.lang.System.exit;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;

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
            
            saveCwlTools();
            
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
    
    
    
    
    /**
     * Checks for additional command line arguments and processes them.
     */
    private void processAdditionalCommandLineArguments()
    {
        this.processOutputArgument();
        this.processArgument_noShellQuote();
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
    
    
    
    private void processOutputArgument()
    {
        if (this.cmd.hasOption("output"))
        {
            try {
                fileHandler.setOutDir(this.cmd.getOptionValue("output"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CmdControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void processArgument_noShellQuote()
    {
        if (this.cmd.hasOption("noShellQuote"))
        {
            converter.setOption_noShellQuote();
        }
    }
    

}
    
    
    
