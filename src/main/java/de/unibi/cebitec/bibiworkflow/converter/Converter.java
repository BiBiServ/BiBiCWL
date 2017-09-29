/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.converter;

import de.unibi.cebitec.bibiworkflow.bs2.Bs2Document;
import de.unibi.cebitec.bibiworkflow.bs2.InputType;
import de.unibi.cebitec.bibiworkflow.cwl.CwlTool;
import de.unibi.techfak.bibiserv.cms.TenumParam;
import de.unibi.techfak.bibiserv.cms.TenumValue;
import de.unibi.techfak.bibiserv.cms.Tfunction;
import de.unibi.techfak.bibiserv.cms.TinputOutput;
import de.unibi.techfak.bibiserv.cms.Tparam;
import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.util.HashMap;

/**
 *
 * @author pol3waf
 */
public class Converter implements IConverter {

    private Bs2Document bs2Doc;
    private CwlTool cwlTool;
    
    
    
    
    public Converter()
    {
        
    }
    
    
    
    /**
     * Convert the whole bs2 document into a single CWL-Tool. 
     * There will be another function which will create a workflow out
     * of CWL-Tools.
     * @param runnableItem
     * @return returns a CWL-Tool object
     * @throws Exception if Conversion fails
     */
    @Override
    public CwlTool convertBs2ToCwlTool(TrunnableItem runnableItem) throws Exception
    {
        bs2Doc = new Bs2Document(runnableItem);
        cwlTool = new CwlTool();
        
        // testing some stuff
        Tfunction function = bs2Doc.getFunctions().get(0);
        String test_output = "";
        for (String s : bs2Doc.getCommandLineArgumentOrderAsReferences(function))
        {
            test_output += "\n" + s;
        }
        System.out.println(test_output);
        
        
        // more testing ...
        convertBaseCommand();
        convertFunctionInputs(function);
        
        // DO SOMETHING !!!
        return null;
    }
    
    
    
    /**
     * Converts the base command of the bs2 file to be set as the CWL base
     * command.
     */
    private void convertBaseCommand() throws Exception
    {
        String baseCommadBs2 = bs2Doc.getBaseCommand();
        cwlTool.setBaseCommand(baseCommadBs2);
        
        System.out.println("converte basecommand: " + baseCommadBs2);
    }
    
    
    
    
    /**
     * Convert every input field (or param/ enumParam/ additionalString) 
     * of a given bs2 function into a CWL input.
     * @param function 
     */
    private void convertFunctionInputs(Tfunction function) throws Exception
    {
        int position = 0;
        for (String id : bs2Doc.getCommandLineArgumentOrderAsReferences(function))
        {
            InputType inputType_bs2 = bs2Doc.getTypeOfInputArgumentsById(id);
            
            if (null == inputType_bs2)
            {
                System.out.println("nothing to convert here");
            }
            else
            {
                position++;
                switch (inputType_bs2) {
                case additionalString:
                    System.out.println("convert additional string");
                    String additionalString = bs2Doc.getAdditionalStringById(id);
                    convertAdditionalString(additionalString, position);
                    break;
                case enumParam:
                    System.out.println("convert enumparam");
                    TenumParam enumParam = bs2Doc.getEnumParamById(id);
                    convertEnumParam(enumParam, position);
                    break;
                case input:
                    System.out.println("convert input");
                    TinputOutput input = bs2Doc.getIntputById(id);
                    convertInput(input, position);
                    break;
                case param:
                    System.out.println("convert param");
                    Tparam param = bs2Doc.getParamById(id);
                    convertParam(param, position);
                    break;
                default:
                    System.out.println("nothing to convert here");
                    break;
                }
            }
        }
    }
    
    
    
    
    private void convertOutputs()
    {
        
    }
    
    
    
    /**
     * Converts an additionalString into a CWL argument.
     * @param as 
     * @param position 
     */
    private void convertAdditionalString(String as, int position)
    {
        cwlTool.addArgument(position, as);
    }
    
    
    
    /**
     * Converts a bs2 input TinputOutput a CWL input.
     * Bs2 inputs usually are file inputs with a wide variety of file types.
     * Depending on the file type a filter will be set in the input field of 
     * the CWL-Tool.
     * @param input
     * @param position 
     */
    private void convertInput(TinputOutput input, int position)
    {
        String id = input.getId();                              // using the ID is better than the element's name, right?
        String type = "File";                                   // no string input allowed?
        
        String fileType = input.getType();                      // what about different file types? How should this be handled --> check CWL documentation
                                                                // CWL supports ontologies like EDAM! USE IT!!!!
                                                                
        String prefix = input.getOption();                      // the option usually encompasses the separator in the bs2 format
        boolean separate = false;                               // should I really hard-code this?
        cwlTool.addInputFile(position, id, prefix, separate, fileType);
    }
    
    
    /**
     * Converts a bs2 Tparam into a CWL input.
     * Depending on the type of the param (e.g. Integer, String, ...) and 
     * whether there are any conditions (e.g. min/max value) the corresponding
     * conditions will be set in the input field of the CWL-Tool.
     * @param param
     * @param position 
     */
    private void convertParam(Tparam param, int position)
    {
        String id = param.getId();
        String type = param.getType().value();
        String prefix = param.getOption();
        boolean separate = false;
        
        // what should I do about min/ max values??? There is no such thing in CWL
        // One could use javaScript to check this, right?
        
        cwlTool.addInput(position, id, type, prefix, separate);
    }
    
    
    
    /**
     * Converts a bs2 TenumParam into several CWL inputs. Depending on the type
     * of the TenumParam, choices can be combined or are exclusive and an
     * appropriate model will be created in the inputs of the CWL-Tool.
     * @param enumParam
     * @param position 
     */
    private void convertEnumParam(TenumParam enumParam, int position) throws Exception
    {
        /*
        The values + names of the enumParam need to be collected and be stored in a way so that 
        CommandInputRecords (CIR) can be created from them. The input fields of the CIR should just
        be of type boolean so that the combination of the prefix and the values of the enumParam
        can be joined and form a flag which can be toggled on or off.
        
        */
        
        int numberOfValues = enumParam.getValues().size();
        HashMap<String, String> options = new HashMap<>();
        
//        enumParam.getValues().get(position)
        
        String id = enumParam.getId();                                  // combine ID with key/ name of value
        String type = enumParam.getType().value();                      // !!! THIS MUST BE BOOLEAN in order to support multiple choices which are handled as flags !!!
                                                                        
        String prefix = enumParam.getOption();
        boolean separate = false;
        
        if (enumParam.getGuiElement().equals("SELECTIONRADIO"))
        {
            for (TenumValue value : enumParam.getValues())
            {
                String key = value.getKey();
                String val = value.getValue();
                String name = value.getName().get(0).getValue();        // de-hardcode this??? or should I use the key instead???
                
                // add the value of the inputfield to the prefix so it can be toggled on or off
                // it's not nice but it should work!
                String combinedValue = prefix + val;
                
                // add the prefix+value and the name to the hashmap
                options.put(name, combinedValue);
            }
            
            // create the the exclusive multi-field input
            cwlTool.addExclusiveMultiFieldInput(position, id, options);
            
        }
        else
        {
            // TODO: Does this case ever occur?
            throw new Exception("Non-exclusive multi-field input found!");
        }
        
        /*
        how do you do this?????
        
        --> type: CommandInputRecord - _darin_ kann man verschiedene Inputs auflisten und je nachdem,
        Wie man die Felder gruppiert, kann man sie exklusiev oder unabhängig machen.
        Die exklusieven Felder sind das was für Radiobuttons interessant sind. 
        Der Rest ist wahrscheinlich einfach mit normalen Feldern zu lösen ...
        
        inputs:
            # dependent
            - label1:                   <-- this is just a normal declaration of an input (as always)
                type:                   <-- do i need this? looks .. inconsistent
                    - type: record          <-- this is the CommandInputRecord, which can have multiple fields (i.e. inputs, that are dependant)
                      [...]
                      fields: 
                        - itemA [...]
                        - itemB [...]
            # exclusive
            label2:                     <-- yet another input - nothing special but ...
                type:
                    - type: record          <----- it has two CommandInputRecords, which exclude each other but can again contain multiple fields in themselves (which are again dependant)
                      [...]                    |
                      fields:                  |
                        - itemC                |
                        - itemD                |
                    - type: record          <--|
                      [...]
                      fields:
                        - itemE
        */
    }
    
    
    
    
}