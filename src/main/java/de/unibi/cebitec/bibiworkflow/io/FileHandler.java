/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template inFile, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.io;

import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;




/**
 *
 * @author pol3waf
 */
public class FileHandler {
    
    
    private static final Logger LOGGER = Logger.getLogger(FileHandler.class.getName());

    private File inFile;
    private File outFile;
    private File outDir = new File("/tmp/bibi-workflow");
    
    private String cwlFileExtension = ".cwl";
    
    
    
    
    
    
    public FileHandler()
    {
        
    }
    
    
    
    
    
    
    public void setInFile(File file)
    {
        this.inFile = file;
    }
    
    
    
    
    
    public void setInFile(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        
        if (file.exists())
        {
            this.inFile = file;
        }
        else
        {
            throw new FileNotFoundException("The input file " + fileName + "could not be found.");
        }
    }
    
    
    
    
    
    public void setOutFile(File file)
    {
        this.outFile = file;
    }
    
    
    
    
    
    public void setOutDir(File directory)
    {
        this.outDir = directory;
    }
    
    
    
    
    
    public void setOutDir(String directoryName) throws FileNotFoundException
    {
        File directory = new File(directoryName);
        
        if (directory.isDirectory())
        {
            this.outDir = directory;
        }
        else
        {
            throw new FileNotFoundException("The output directory " + directory + "could not be found.");
        }
    }
    
    
    
    
    
    /**
     * Converts the loaded inFile into a TrunnableItem and return it. 
     * If the conversion fails, the function returns null.
     * @return TrunnableItem or null (if function fails)
     */
    public TrunnableItem importBs2AsRunnableItem()
    {
        try {
            Source source = new StreamSource(this.inFile);
            JAXBContext ctx = JAXBContext.newInstance(new Class[] {TrunnableItem.class});
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            TrunnableItem runnableItem = unmarshaller.unmarshal(source, TrunnableItem.class).getValue();
            return runnableItem;
        } catch (JAXBException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    
    
    
    /**
     * Writes a String to a defined file.
     * @param fileName name of the output file
     * @param text text (string object) to be written to file
     * @throws IOException 
     */
    public void writeStringToFile(String fileName, String text) throws IOException
    {
        
        this.checkAndCreateOutDir();
        
        fileName = fileName.concat(cwlFileExtension);
        String formattedFilenName = fileName.replace(' ', '_');
        
        this.outFile = new File(outDir.getAbsoluteFile(), formattedFilenName);
        
        FileWriter fw = new FileWriter(this.outFile);       // there is probably a better way ...
        
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(text);
        writer.close();
    }
    
    
    
    
    
    
    /**
     * Writes a set of Strings to the output directory which is specified in the FileHandler object.
     * @param documents 
     */
    public void writeMultipleStringsToDisk(HashMap<String, String> documents)
    {
        documents.forEach( (name, yamlCwlTool) ->
        {
            try {
                this.writeStringToFile(name, yamlCwlTool);
                LOGGER.info("Saved " + name + " to file: " + this.getOuputFilePath());
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        );
    }
    
    
    
    
    
    
    
    
    /**
     * Returns the output directory's path as a String.
     * @return output directory path
     */
    public String getOutputDirectoryPath()
    {
        return this.outDir.toPath().toString();
    }
    
    
    
    
    
    public String getOuputFilePath()
    {
        return this.outFile.toPath().toString();
    }
    
    
    
    
    
    /**
     * Creates an output directory if it doesn't already exists.
     * @return true if it already exists, false of it doesn't
     */
    private boolean checkAndCreateOutDir()
    {
        if (this.outDir.exists())
        {
            LOGGER.fine("Output directory " + this.outDir.toString() + " already exists.");
            return true;
        }
        else
        {
            LOGGER.fine("Output directory " + this.outDir.toString() + 
                    " doesn't exist. It will be created.");
            this.outDir.mkdir();
            return true;
        }
    }
    
    
}
