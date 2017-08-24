/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.bs2processor;

import static de.unibi.cebitec.bibiworkflow.bs2processor.BSConstants.TYPE;
import de.unibi.techfak.bibiserv.basespaceconverter.containerclasses.Triplet;
import de.unibi.techfak.bibiserv.basespaceconverter.formbuilder.Form;
import de.unibi.techfak.bibiserv.basespaceconverter.formbuilder.FormBuilder;
import de.unibi.techfak.bibiserv.cms.TenumParam;
import de.unibi.techfak.bibiserv.cms.Tfunction;
import de.unibi.techfak.bibiserv.cms.TinputOutput;
import de.unibi.techfak.bibiserv.cms.Tparam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.iapi.store.raw.LockingPolicy;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author jsaydo
 */
public class BS2Processor {
    
    
    private static Logger LOG = Logger.getLogger(BS2Processor.class.getName());
    
    private final BS2Accessor acc;
    private final FormBuilder fb;
    
    /**
     * default constructor
     * @param bs2FilePath path to the bs2file
     * @param ontologyFilePath path to ontology file
     */
    public BS2Processor(String bs2FilePath, String ontologyFilePath)
    {
        LOG.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOG.addHandler(handler);
        
        
        acc = new BS2Accessor(bs2FilePath, ontologyFilePath);
        fb = new FormBuilder();
        
        this.createBasespaceForm();
        
    }
    
    
    
    // dont use this
    // @TODO: move this to another class, which is responsible for converting
    // the data structures.
    /**
     * Wrapper method for converting BibiServ application input fields to 
     * Basespace input fields represented as Json object. 
     * Calls other functions which do the actual work to get the input, 
     * parameters and other stuff.
     * @return Json-Object of the Basespace input form
     */
    public String convertToJSon() 
    {
        ObjectMapper om = new ObjectMapper();
        
//        this.getInputFields();
//        this.getParamInnards();
        
        try {
            Form form = fb.getForm();
            System.out.println("This is the form in the bs2processor"
                    + "");
            System.out.println(form.get$type());
            return om.writeValueAsString(fb.getForm());
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(BS2Accessor.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR: conversion of the form to json did not work";
        }
    }
    
    
    
    /**
     * Creates a BasespaceForm by checking all functions of the bs2file and 
     * looking up its innards and passing them to the formBuilder.
     */
    private void createBasespaceForm()
    {
        // There can be multiple functions in a bs2 file. They can be treated
        // seperately, perhaps put into a fieldset in Basespace ...
        for (Tfunction function : acc.getFunctions())
        {
            // check the input of the Tfunction and choose which type of input
            // should be used in the basespace GUI.
            processFunctionInput(function);
            
            LOG.finer("function " + function.getId() + "is checked for parameters");
            System.out.println("chenking function parameters ...");
            String log = "";
            
            // check all the params and enumParams of the function
            for (Object parameter : acc.getParameters(function))
            {
                log += parameter.toString();
                // params are single value input fields
                if (parameter instanceof Tparam)
                {
                    log += " - " + ((Tparam) parameter).getName().get(0).getValue();
                    processFunctionParam((Tparam) parameter);
                }                
                // enumParams are Fields like Checkboxes and RadioButtons
                else if (parameter instanceof TenumParam)
                {
                    processFunctionEnumParam((TenumParam) parameter);
                }
                
                log += ";   ";
            }
            System.out.println(log);
            // log every single paramter
            LOG.finest("found params in function : " + log);
            
            // TODO: do stuff for each function - i.e. put it inside a FieldSet
            // or something similar.
            
            
            
        }
        
        
        
    }
    
    
    
    
    /**
     * Parses the input fields of the Tfunction and calls appropriate Methods
     * in the FormBuilder to create a Basespace compatible form.
     * @param function Tfunction object from a bs2file
     */
    private void processFunctionInput(Tfunction function) 
    {
        for (TinputOutput input : acc.getInputRef(function))
        {
            HashMap<String, String> inputMap = acc.getInputInnards(input);
            switch (inputMap.get(TYPE))
            {
                // TODO : cases dont match - adjust!
                // TODO2: move the switch to the Formbuilder?
                case "Sample": fb.addFieldSampleChooser(
                        inputMap.get(BSConstants.ID),
                        inputMap.get(BSConstants.NAME),
                        true, // yes - this input is always required ...
                        inputMap.get(BSConstants.REQUIREMESSAGE),
                        42, // WHAT SIZE???
                        "read", // what permissions???
                        inputMap.get(BSConstants.TYPE), // this might be crap. use some hardcoded shit?
                        "yeah this rulezzz" // wtf
                );
                break;
                case "SequenceInput": fb.addFieldSampleChooser(
                        inputMap.get(BSConstants.ID),
                        inputMap.get(BSConstants.NAME),
                        true, // yes - this input is always required ...
                        inputMap.get(BSConstants.REQUIREMESSAGE),
                        42, // WHAT SIZE???
                        "read", // what permissions???
                        inputMap.get(BSConstants.TYPE), // this might be crap. use some hardcoded shit?
                        "yeah this rulezzz" // wtf
                );
                break;
                case "Fasta" : fb.addFieldFileChooser(  // what case is correct here??? Basespace uses other names....
                        inputMap.get(BSConstants.ID),
                        inputMap.get(BSConstants.NAME),
                        true, // yes - this input is always required ...
                        inputMap.get(BSConstants.REQUIREMESSAGE),
                        42, // WHAT SIZE???
                        true, // is it really multiselect???
                        "Input", // what input type???
                        ".fastq, .fasta"
                ); break;
                case "FreeText" : fb.addFieldTextbox(
                        "id", 
                        "label", 
                        true, 
                        "i am required", 
                        "help", 
                        "rule_id", 
                        "value", 
                        "String", 
                        42, 
                        1, 
                        42
                );
                break;
                default: break;
            }
        }
    }
    
    
    
    
    /**
     * Parses the params from a Tfunction and creates Basespace compatible 
     * input fields with the FormBuilder.
     * @param param Tparam to be processed
     */
    private void processFunctionParam(Tparam param) 
    {
        // TODO: do something ...
        switch (param.getGuiElement())
        {
//            case BSConstants.
            
            
        }
    }
    
    
    
    
    /**
     * Parses the enumParams from a Tfunction and creates Basespace compatible 
     * input fields with the FormBuilder.
     * @param enumParam Tenumparam to be prcessed
     */
    private void processFunctionEnumParam(TenumParam enumParam) 
    {
        LOG.finer("EnumParam found: " + enumParam.getName().get(0).getValue() + " - " + enumParam.getGuiElement());
        switch (enumParam.getGuiElement()) 
        {
            case BSConstants.SELECTONERADIO: 
                
                // check choices for default value
                ArrayList<Triplet> choices = acc.getValuesFromEnumParam(enumParam);
                int defaultValue = 0;
                int i = 0;
                for (Triplet choice : choices)
                {
                    if ((boolean) choice.getThree())
                    {
                        defaultValue = i;
                    }
                    i++;
                }
                
                // add the radiobuttons ..
                fb.addFieldRadioButton(
                        enumParam.getId(), 
                        enumParam.getName().get(0).getValue(),
                        true,
                        enumParam.getShortDescription().get(0).getValue(),
                        enumParam.getDescription().get(0).getContent().get(0).toString(), // what about this??? this looks strange                        
                        "",                                       // rules?????
                        defaultValue,
                        choices
                ); 
                break;
            case BSConstants.SELECTMANYCHECKBOX: 
                fb.addFieldCheckBox(
                        enumParam.getId(), 
                        enumParam.getName().get(0).getValue(),
                        false,
                        enumParam.getShortDescription().get(0).getValue(),
                        enumParam.getDescription().get(0).getContent().get(0).toString(), // what about this??? this looks strange
                        null,                                       // how to use this?
                        acc.getValuesFromEnumParam(enumParam)
                );
                break;
            case BSConstants.SELECTBOOLEANCHECKBOX: 
                fb.addFieldCheckBox(
                        enumParam.getId(), 
                        enumParam.getName().get(0).getValue(),
                        false,
                        enumParam.getShortDescription().get(0).getValue(),
                        enumParam.getDescription().get(0).getContent().get(0).toString(), // what about this??? this looks strange
                        null,                                       // what is this?
                        acc.getValuesFromEnumParam(enumParam)
                ); 
                break;
            default: 
                break;
        }
    }

    
    
    
    
    
}
