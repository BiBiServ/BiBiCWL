/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.converter;

import de.unibi.cebitec.bibiworkflow.bs2.Bs2Document;
import de.unibi.cebitec.bibiworkflow.bs2.ArgumentType;
import de.unibi.cebitec.bibiworkflow.cwl.CwlTool;
import de.unibi.techfak.bibiserv.cms.TenumParam;
import de.unibi.techfak.bibiserv.cms.TenumValue;
import de.unibi.techfak.bibiserv.cms.Tfunction;
import de.unibi.techfak.bibiserv.cms.TinputOutput;
import de.unibi.techfak.bibiserv.cms.ToutputFile;
import de.unibi.techfak.bibiserv.cms.Tparam;
import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
public class Converter implements IConverter
{
    
    private final static Logger LOGGER = Logger.getLogger(Converter.class.getName());
    private Bs2Document bs2Doc;
    private final ArrayList<String> outputsThatUseInputs = new ArrayList<>();
    
    
    
    
    public Converter()
    {
        
    }
    
    
    
    /**
     * Convert the whole bs2 document into a multiple CWL-Tools. 
     * There will be another function which will create a workflow out
     * of CWL-Tools.
     * @param runnableItem
     * @return returns a List of CWL-Tool objects
     * @throws Exception if Conversion fails
     */
    @Override
    public HashMap<String, CwlTool> convertBs2(TrunnableItem runnableItem) throws Exception
    {
        HashMap<String, CwlTool> cwlTools = new HashMap<>();
        this.bs2Doc = new Bs2Document(runnableItem);
        
        // testing some stuff
        for (Tfunction function : bs2Doc.getFunctions())
        {
            CwlTool cwlTool = convertFunctionToCwlTool(function);
            cwlTools.put(function.getName().get(0).getValue(), cwlTool);
        }
        
        
        // DO SOMETHING !!!
        return cwlTools;
    }
    
    
    
    private CwlTool convertFunctionToCwlTool(Tfunction function) throws Exception
    {
        CwlTool cwlTool = new CwlTool();
        convertBaseCommand(cwlTool);
        convertFunctionInputs(function, cwlTool);
        convertFunctionOutputs(function, cwlTool);
        return cwlTool;
    }
    
    
    
    /**
     * Converts the base command of the bs2 file to be set as the CWL base
     * command.
     */
    private void convertBaseCommand(CwlTool cwlTool) throws Exception
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
    private void convertFunctionInputs(Tfunction function, CwlTool cwlTool) throws Exception
    {
        int position = 0;
        for (String id : bs2Doc.getCommandLineArgumentOrderAsReferences(function))
        {
            ArgumentType inputType_bs2 = bs2Doc.getTypeOfArgumentById(id);
            
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
                    convertAdditionalString(additionalString, position, cwlTool);
                    break;
                case enumParam:
                    System.out.println("convert enumparam");
                    TenumParam enumParam = bs2Doc.getEnumParamById(id);
                    convertEnumParam(enumParam, position, cwlTool);
                    break;
                case input:
                    System.out.println("convert input");
                    TinputOutput input = bs2Doc.getIntputById(id);
                    convertInput(input, position, cwlTool);
                    break;
                case param:
                    System.out.println("convert param");
                    Tparam param = bs2Doc.getParamById(id);
                    convertParam(param, position, cwlTool);
                    break;
                    
                    // move this somewhere else???????????????????????????????????
                case output:
                    // ??? DOES THIS MAKE SENSE ???
                    System.out.println("convert output ... yes, checks for outputs should be moved somewhere else ...");
                    TinputOutput output = bs2Doc.getOutputById(id);
                    convertOutputArguments(output, position, cwlTool);
                    break;
                case outputFile:
                    // ????
                    System.out.println("convert outputFile ... yes, checks for outputFiles should probably be moved somewhere else ...");
                    ToutputFile outputFile = bs2Doc.getOutputFileById(id);
                    //??? what to do with this?
                    break;
                default:
                    System.out.println("nothing to convert here");
                    break;
                }
            }
        }
    }
    
    
    
    /**
     * Convert bs2 outputs and file-outputs of a given bs2 function into CWL 
     * outputs.
     * @param function
     * @param cwlTool 
     */
    private void convertFunctionOutputs(Tfunction function, CwlTool cwlTool)
    {
        // convert the single output (of type TinputOutput) of the function
        TinputOutput output = bs2Doc.getFunctionOutput(function);
        
        String oId = output.getId();
        String oType = output.getType();
        String oHandling = output.getHandling();
        
        if (oHandling.equals("stdout"))
        {
            cwlTool.addOutput(oId, "stdout", null);              // doesn't need the "glob" field because this is set in the stdout field of the CwlTool
            String inputReference = oId + "_inputFileName";      // this will be used to find the suitable input with the file's name
            cwlTool.setupStdout(inputReference);
        }
        else
        {
            if (outputsThatUseInputs.contains(oId))
            {
                String inputReference = oId + "_inputFileName";
                cwlTool.addOutput(oId, oType, inputReference);
            }
            else
            {
                cwlTool.addOutput(oId, oType, null);              // @TODO: should probably use something else ...
            }
        }
        
        
        // convert all outputFiles (of type ToutputFile) of the function
        ArrayList<ToutputFile> outputFiles = bs2Doc.getFunctionOutputFiles(function);
        for (ToutputFile of : outputFiles)
        {
            String ofId = of.getId();
            String ofType = "File";
            String ofFileType = of.getContenttype();                // check if stuff is set up properly before applying things ...
            String ofFileName = of.getFilename();
            String ofFirectory = of.getFolder();
            String ofName = of.getName().get(0).getValue();        // leave as is ???
            
            if (outputsThatUseInputs.contains(ofId))
            {
                String inputReference = ofId + "_inputFileName";
                cwlTool.addOutput(ofId, ofType, inputReference);
            }
            else
            {
                cwlTool.addOutput(ofId, ofType, ofFileName);        // just use the filename from the outputFile object ???
            }                                                       // or read some input field?
        }
        
        
    }
    
    
    
    /**
     * Converts an additionalString into a CWL argument.
     * @param as 
     * @param position 
     */
    private void convertAdditionalString(String as, int position, CwlTool cwlTool)
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
    private void convertInput(TinputOutput input, int position, CwlTool cwlTool)
    {
        String id = input.getId();                              // using the ID is better than the element's name, right?
        String type = "File";                                   // no string input allowed?
        String prefix;
        String fileType = input.getType();                      // what about different file types? How should this be handled --> check CWL documentation
                                                                // CWL supports ontologies like EDAM! USE IT!!!!
        
        if (input.isSetOption())
        {
            prefix = input.getOption();                      // the option usually encompasses the separator in the bs2 format
        }
        else
        {
            prefix = null;
        }
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
    private void convertParam(Tparam param, int position, CwlTool cwlTool)
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
    private void convertEnumParam(TenumParam enumParam, int position, CwlTool cwlTool) throws Exception
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
        
        if (enumParam.getGuiElement().equals("SELECTONERADIO"))
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
            // TODO: Does this case ever occur?   --- yes of course, see: CHECKBOXFIELD
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
    
    
    
    /**
     * Convert a given bs2 output object to the equivalent CWL output object.
     * This process does not cover the command line argument in which the output
     * type or file is processed (this would be part of the input (/argument) 
     * conversion).
     * @param output bs2 output to be converted
     * @param cwlTool CwlTool which should receive the output
     */
    private void convertOutput(TinputOutput output, CwlTool cwlTool)
    {
        
    }
    
    
    
    /**
     * Converts the command line argument for the output of the bs2 tool into
     * CWL input (or arguments) (while taking its position and the types of outputs into
     * consideration).
     * 
     * @TODO: Macht das so Sinn???
     * Evtl muss man checken, ob der BibiServ da manuell ein pipien nach STDOUT macht ...
     * Dann dürfte das hier gar nicht als input mit aufgeführt werden, sondern müsste einfach über
     * das stdout feld vom CWL Tool behandelt werden, da man da ja einfach über $( inputs.inputname )
     * ein nicht in den inputs spezifiziertes input feld referenzieren kann.
     * ??? ??? ???
     * HELP !!!
     * ??? ??? ???
     * 
     * @param output
     * @param position
     * @param cwlTool 
     */
    private void convertOutputArguments(TinputOutput output, int position, CwlTool cwlTool)
    {
        
        String id = output.getId() + "_ouputFileName";      // how to name this ???
        String handling = output.getHandling();
        
        // check the handling: if it is stdout the argument shoudl not be used 
        // in CWL as a declared input but rather as a reference in the stdout
        // field. The latter will be done when checking and creating outputs.
        if (handling.equals("stdout"))
        {
            LOGGER.info("Entry" + id + " in bs2 ParamAndInputputOrder will be omitted, because stdout is handled differently in CWL and will be processed in the convertFunctionOuputs() function.");
        }
        else
        {
    //        String type = output.getType();                     // what about this ???
            String type = "String";                             // is this OK ???
            boolean separate = false;
            String prefix;
            if (output.isSetOption())
            {
                prefix = output.getOption();
            }
            else
            {
                prefix = null;
            }
            cwlTool.addInput(position, id, type, prefix, separate);
        }
        outputsThatUseInputs.add(id);
        
        
    }
    
    
    
    
}