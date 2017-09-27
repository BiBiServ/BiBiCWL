/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author pol3waf
 */
public class YamlWriter
{
    
    private final YAMLMapper mapper = new YAMLMapper();
    
    
    
    public YamlWriter()
    {
        
    }
    
    
    public String writeObjectToYaml(Object object)
    {
        String yamlString = null;
        try
        {
            yamlString = mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException ex) 
        {
            Logger.getLogger(YamlWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return yamlString;
    }
    
    
    
}


