/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.io;

import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.io.File;
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
    
    private File file;
    
    
    
    public FileHandler()
    {
        
    }
    
    
    public void setFile(File file)
    {
        this.file = file;
    }

    /**
     * Converts the loaded file into a TrunnableItem and return it. 
     * If the conversion fails, the function returns null.
     * @return TrunnableItem or null (if function fails)
     */
    public TrunnableItem convertBs2ToRunnableItem()
    {
        try {
            Source source = new StreamSource(this.file);
            JAXBContext ctx = JAXBContext.newInstance(new Class[] {TrunnableItem.class});
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            TrunnableItem runnableItem = unmarshaller.unmarshal(source, TrunnableItem.class).getValue();
            return runnableItem;
        } catch (JAXBException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
}
