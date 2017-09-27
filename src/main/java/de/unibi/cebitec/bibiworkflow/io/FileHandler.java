/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template inFile, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.io;

import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    
    private File inFile;
    private File outFile;
    
    
    public FileHandler()
    {
        
    }
    
    
    public void setInFile(File file)
    {
        this.inFile = file;
    }
    
    
    public void setOutFile(File file)
    {
        this.outFile = file;
    }
    

    /**
     * Converts the loaded inFile into a TrunnableItem and return it. 
     * If the conversion fails, the function returns null.
     * @return TrunnableItem or null (if function fails)
     */
    public TrunnableItem convertBs2ToRunnableItem()
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
     * @param text text (string object) to be written to file
     * @throws IOException 
     */
    public void writeStringToFile(String text) throws IOException
    {
        FileWriter fw;
        if (outFile != null)
        {
            fw = new FileWriter(outFile);
        }
        else
        {
            fw = new FileWriter("/tmp/myTestFile.yaml");
        }
        
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(text);
        writer.close();
    }
    
    
    
}
