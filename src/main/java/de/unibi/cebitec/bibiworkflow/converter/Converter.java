/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.converter;

import de.unibi.cebitec.bibiworkflow.app.IModelListener;
import de.unibi.cebitec.bibiworkflow.bs2.Bs2Document;
import de.unibi.cebitec.bibiworkflow.bs2.EArgumentType;
import de.unibi.cebitec.bibiworkflow.bs2.IBs2Document;
import de.unibi.cebitec.bibiworkflow.cwl.CwlTool;
import de.unibi.cebitec.bibiworkflow.cwl.ICwlTool;
import de.unibi.techfak.bibiserv.cms.TenumParam;
import de.unibi.techfak.bibiserv.cms.TenumValue;
import de.unibi.techfak.bibiserv.cms.Tfunction;
import de.unibi.techfak.bibiserv.cms.TinputOutput;
import de.unibi.techfak.bibiserv.cms.ToutputFile;
import de.unibi.techfak.bibiserv.cms.Tparam;
import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 *
 * @author pol3waf
 */
public class Converter implements IConverter
{
    
    private final static Logger LOGGER = Logger.getLogger(Converter.class.getName());
    private IBs2Document bs2Doc;
    private final ArrayList<String> outputsThatUseInputs = new ArrayList<>();
    
    private final HashMap<String, ICwlTool> cwlTools = new HashMap<>();
    
    private final ArrayList<IModelListener> modelListeners = new ArrayList<>();
    
    private final ArrayList<String> outputFileFolderList = new ArrayList<>();
    
    
    
    
    
    
    
    
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
    public HashMap<String, ICwlTool> convertBs2(TrunnableItem runnableItem) throws Exception
    {
//        HashMap<String, ICwlTool> cwlTools = new HashMap<>();
        
        // clear the HashMap so that tools from previous runs don't end up in 
        // the current run.
        this.cwlTools.clear();
        
        this.bs2Doc = new Bs2Document(runnableItem);
        
        for (Tfunction function : bs2Doc.getFunctions())
        {
            ICwlTool cwlTool = convertFunctionToCwlTool(function);
            this.cwlTools.put(function.getId(), cwlTool);               // using the function's ID as CWLTool name instead of the function's
                                                                        // name, because it's more robust
        }
        
        return (HashMap<String, ICwlTool>) this.cwlTools.clone();
    }
    
    
    
    
    public HashMap<String, ICwlTool> getCwlTools()
    {
        if (this.cwlTools.isEmpty())
        {
            LOGGER.info("Trying to gather CwlTools but there are none. Remember to create them first (e.g. by converting bs2 documents)");
        }
        return (HashMap<String, ICwlTool>) this.cwlTools.clone();
    }
    
    
    
    
    
    private ICwlTool convertFunctionToCwlTool(Tfunction function) throws Exception
    {
        ICwlTool cwlTool = new CwlTool();
        convertBaseCommand(cwlTool);
        
        // the order of these two matters!
        convertFunctionInputs(function, cwlTool);
        convertFunctionOutputs(function, cwlTool);
        
        checkForDocker(cwlTool);
        return cwlTool;
    }
    
    
    
    
    /**
     * Converts the base command of the bs2 file to be set as the CWL base
     * command.
     */
    private void convertBaseCommand(ICwlTool cwlTool) throws Exception
    {
        String baseCommadBs2 = bs2Doc.getBaseCommand();
        cwlTool.setBaseCommand(baseCommadBs2);
        
        LOGGER.info("converted basecommand: " + baseCommadBs2);
    }
    
    
    
    
    /**
     * Convert every input field (or param/ enumParam/ additionalString) 
     * of a given bs2 function into a CWL input.
     * @param function 
     */
    private void convertFunctionInputs(Tfunction function, ICwlTool cwlTool) throws Exception
    {
        int position = 0;
        for (String id : bs2Doc.getCommandLineArgumentOrderAsReferences(function))
        {
            EArgumentType inputType_bs2 = bs2Doc.getTypeOfArgumentById(id);
            
            if (null == inputType_bs2)
            {
                LOGGER.info("No Input to convert here");
            }
            else
            {
                position++;
                switch (inputType_bs2) {
                case additionalString:
                    LOGGER.fine("convert additional string");
                    String additionalString = bs2Doc.getAdditionalStringById(id);
                    convertAdditionalString(additionalString, position, cwlTool);
                    break;
                case enumParam:
                    LOGGER.fine("convert enumparam");
                    TenumParam enumParam = bs2Doc.getEnumParamById(id);
                    convertEnumParam(enumParam, position, cwlTool);
                    break;
                case input:
                    LOGGER.fine("convert input");
                    TinputOutput input = bs2Doc.getIntputById(id);
                    convertInput(input, position, cwlTool);
                    break;
                case param:
                    LOGGER.fine("convert param");
                    Tparam param = bs2Doc.getParamById(id);
                    convertParam(param, position, cwlTool);
                    break;
                    
                case output:
                    // ??? DOES THIS MAKE SENSE ???
                    LOGGER.fine("convert output ... yes, checks for outputs should be moved somewhere else ...");
                    TinputOutput output = bs2Doc.getOutputById(id);
                    convertOutputArguments(output, position, cwlTool);
                    break;
//                case outputFile:
//                    // not needed ... paramAndInputOutputOrder does not contain references to outputFiles
//                    LOGGER.fine("convert outputFile ... yes, checks for outputFiles should probably be moved somewhere else ...");
//                    ToutputFile outputFile = bs2Doc.getOutputFileById(id);
//                    //??? what to do with this?
//                    break;
                default:
                    LOGGER.fine("Input is of unknow type ...");
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
    private void convertFunctionOutputs(Tfunction function, ICwlTool cwlTool)
    {
        
        //  convert the single output (of type TinputOutput) of the function
        LOGGER.finest("Processing bs2 function output.");
        TinputOutput output = bs2Doc.getFunctionOutput(function);
        processBiBiOutputToCwl(output, cwlTool);
        
        
        //  convert all outputFiles (of type ToutputFile) of the function and 
        //  check whether directories are defined.
        ArrayList<ToutputFile> outputFiles = bs2Doc.getFunctionOutputFiles(function);
        HashSet<String> directories = new HashSet<>();
        for (ToutputFile of : outputFiles)
        {
            processBiBiOutputFileToCwl(of, cwlTool);
            
            if (of.isSetFolder() && !of.getFolder().isEmpty())
            {
                directories.add(of.getFolder());
            }
        }
        
        
        //  if available, create CWL output directories from the directories
        //  which were specified in the BiBiApp description
        createCwlOutputDirectories(directories, cwlTool);
    }
    
    
    
    
    /**
     * Creates a CWL output from the BiBiApp output element.
     * @param output Output element which is used to create the CWL output
     * @param cwlTool CWL tool for which the output should be created
     */
    private void processBiBiOutputToCwl(TinputOutput output, ICwlTool cwlTool)
    {
        
        String oId = output.getId();
        String oHandling = output.getHandling();
        
        if (oHandling.toLowerCase().equals("stdout"))
        {
            cwlTool.addOutput(oId, "stdout", null, null);          // doesn't need the "glob" field because this is set in the stdout field of the CwlTool
//            String inputReference = oId + "_inputFileName";      // this will be used to find the suitable input with the file's name
            String inputReference = oId + "_outputFileName";        // perhaps make this hardcoded (i.e. make this an argument instead of an input)?
            cwlTool.setupStdout(inputReference);
        }
        else if (oHandling.toLowerCase().equals("file"))
        {
            String oType = "File";
            
            if (outputsThatUseInputs.contains(oId + "_outputFileName"))
            {
                String inputReference = "$(inputs." + oId + "_outputFileName)";
                cwlTool.addOutput(oId, oType, inputReference, null);
            }
            else
            {
                cwlTool.addOutput(oId, oType, null, null);              // @TODO: should probably use something else ...
            }
        }
        else
        {
            LOGGER.finer("Handling of BiBi output unknown.");
        }
        
    }
    
    
    
    
    /**
     * Creates a CWL output from a BibiApp outputFile.
     * @param of BiBi outputFile which is to be converted to a CWL output
     * @param cwlTool CWL tool for which the output should be created
     */
    private void processBiBiOutputFileToCwl(ToutputFile of, ICwlTool cwlTool)
    {
        String ofId = of.getId();
            String ofType = "File";
            String ofFileType = of.getContenttype();                // check if stuff is set up properly before applying things ...
            String ofFileName = of.getFilename();
            String ofDirectory = of.getFolder();
            String ofName = of.getName().get(0).getValue();        // leave as is ???
            
            if (outputsThatUseInputs.contains("$(inputs." + ofId + "_outputFileName)"))
            {
                String inputReference = ofId + "_outputFileName";
                cwlTool.addOutput(ofId, ofType, inputReference, ofFileType);
            }
            else
            {
                String glob;
                if (of.isSetFolder())
                {
                    glob = of.getFolder() + "/" + ofFileName;
                }
                else
                {
                    glob = ofFileName;
                }
                cwlTool.addOutput(ofId, ofType, glob, ofFileType);        // just use the filename from the outputFile object ???
            }
    }
    
    
    
    
    /**
     * Creates a set of CWL directory outputs from a given HashSet of directory 
     * paths.
     * 
     * e.g. for a path "some/directory/with/sub/directories" a CWL directory
     * output with the id "some_directory" and glob pattern "some" will be
     * created.
     * If another path with another directory "some/other/directory" is in the
     * set, no other CWL output will be created because the directory "some"
     * is parent to all subdirectories.
     * If yet another path with "another/directory" is in the set, a new CWL
     * directory output will be created.
     * 
     * @param directories set of directory paths
     * @param cwlTool CWL tool for which the outputs should be created
     */
    private void createCwlOutputDirectories(HashSet<String> directories, ICwlTool cwlTool)
    {
        if (!directories.isEmpty())
        {
            // get parent directory names and dispose of duplicates
            HashSet<String> parentDirectoriesWithoutDuplicates = new HashSet<>();
            for (String s : directories)
            {
                String parentDirectory = getParentDirectoryNameFromPath(s);
                parentDirectoriesWithoutDuplicates.add(parentDirectory);
            }
            
            // create CWL directory outputs (no duplicates left hopefully)
            for (String parentDirectory : parentDirectoriesWithoutDuplicates)
            {
                String id = parentDirectory + "_directory";
                String type = "Directory";
                cwlTool.addOutput(id, type, parentDirectory, null);
            }
            
        }
    }
    
    
    
    
    
    /**
     * Converts an additionalString into a CWL argument.
     * @param as 
     * @param position 
     */
    private void convertAdditionalString(String as, int position, ICwlTool cwlTool)
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
    private void convertInput(TinputOutput input, int position, ICwlTool cwlTool)
    {
        String id = input.getId();                              // using the ID is better than the element's name, right?
        String type = "File";                                   // no string input allowed?
        String prefix = null;
        Boolean separate = null;
        String fileType = null;                                 // what about different file types? How should this be handled --> check CWL documentation
                                                                // CWL supports ontologies like EDAM! USE IT!!!!
        // check for "ToolDependentRepresentation" in filetype
        if (input.isSetType())
        {
            if (input.getType().toLowerCase().equals("tooldependentrepresentation"))
            {
                fileType = null;
            }
            else
            {
                fileType = input.getType();
            }
        }
        
        if (input.isSetOption())
        {
            prefix = input.getOption();                      // the option usually encompasses the separator in the bs2 format
            separate = false;
        }
        else
        {
            prefix = null;
            separate = null;
        }
        
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
    private void convertParam(Tparam param, int position, ICwlTool cwlTool)
    {
        String id = param.getId();
        String type = param.getType().value().toLowerCase();                // lower case? is this always correct ???
        String prefix = null;
        Boolean separate = null;
        
        if (param.isSetOption())
        {
            prefix = param.getOption();
            separate = false;
        }
        
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
    private void convertEnumParam(TenumParam enumParam, int position, ICwlTool cwlTool) throws Exception
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
        String type = enumParam.getType().value().toLowerCase();        // !!! THIS MUST BE BOOLEAN in order to support multiple choices which are handled as flags !!!
                                                                        // ??? lower case ???
        String prefix = "";
        Boolean separate = null;
        if (enumParam.isSetOption())
        {
            prefix = enumParam.getOption();
            separate = false;
        }
        
        
        
        
        if (enumParam.getGuiElement().equals("SELECTONERADIO"))
        {
            for (TenumValue value : enumParam.getValues())
            {
                String key = value.getKey();
                String val = value.getValue();
//                String name = value.getName().get(0).getValue();        // de-hardcode this??? or should I use the key instead??? --> yes!
                
                // add the value of the inputfield to the prefix so it can be toggled on or off
                // it's not nice but it should work!
                String combinedValue = prefix + val;
                
                // add the prefix+value and the name to the hashmap
                options.put(key, combinedValue);
            }
            
            // create the the exclusive multi-field input
            cwlTool.addExclusiveMultiFieldInput(position, id, options);
            
        }
        else
        {
            // TODO: Does this case ever occur?   --- yes of course, see: CHECKBOXFIELD
            throw new Exception("Non-exclusive multi-field input found! Only SELECTONERADIO type is currently supported.");
        }
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
    
    
    
    
    
    /**
     * Convert a given bs2 output object to the equivalent CWL output object.
     * This process does not cover the command line argument in which the output
     * type or file is processed (this would be part of the input (/argument) 
     * conversion).
     * @param output bs2 output to be converted
     * @param cwlTool CwlTool which should receive the output
     */
    @Deprecated
    private void convertOutput(TinputOutput output, ICwlTool cwlTool)
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
    private void convertOutputArguments(TinputOutput output, int position, ICwlTool cwlTool)
    {
        
        String id = output.getId() + "_outputFileName";      // how to name this ??? is this even needed? 
        String handling = output.getHandling();
        
        // check the handling: if it's "stdout", the argument should not be used 
        // in CWL as a declared input but rather as a reference in the stdout
        // field. The latter will be done when checking and creating outputs.
        if (handling.toLowerCase().equals("stdout"))
        {
            outputsThatUseInputs.add(id);
            LOGGER.info("Entry" + id + " in bs2 ParamAndInputputOrder will be omitted, because stdout is handled differently in CWL and will be processed in the convertFunctionOuputs() function.");
        }
        else
        {
            // add an input which is used to determine the output location/ file
            // of the corresponding output.
            
            String type = "string";                             // hard-coded ... is this OK ???
            Boolean separate = null;                            // really ???
            String prefix;
            if (output.isSetOption())
            {
                LOGGER.info("Output " + id + " uses an option. Create input field for specifying output file or directory");
                prefix = output.getOption();
                outputsThatUseInputs.add(id);
                cwlTool.addInput(position, id, type, prefix, separate);         // this is duplicated from the lines below.
            }
//            else
//            {
//                prefix = null;
//            }
//            cwlTool.addInput(position, id, type, prefix, separate);           // this is probably needed ... ???
            
        }
        
        
    }
    
    
    
    
    /**
     * @TODO: everything ......
     * @param output
     * @param position
     * @param cwlTool 
     */
    @Deprecated
    private void convertOutputArguments(ToutputFile output, int position, ICwlTool cwlTool)
    {
        throw new UnsupportedOperationException("converting OutputFile fields as arguments is not yet implemented ...");
    }
    
    
    
    
    
    /**
     * Check whether docker is being used in the bs2 file and add a hint to the
     * CWLTool if it is.
     * @param cwlTool 
     */
    private void checkForDocker(ICwlTool cwlTool) 
    {
        /*
            
        TODO: !!! ??? !!!!
            Should the Docker requiremnet be set as hint or as requirement?
            
        */
        if (bs2Doc.isUsingDocker())
        {
            cwlTool.addHint(ICwlTool.ERequirementClass.DockerRequirement, bs2Doc.getDockerImageLocation());
        }
    }
    
    
    
    
    
    
    
    
    
    
    /*
        =========== OPTIONS ============
    */
    
    
    
    /**
     * Set up the CWL Tool to not use shellQuotes. Therefore a 
     * ShellCommandlineRequirement is going to be created, and the option 
     * shellQuote: false is going to be added to every input.
     */
    @Override
    public void setOption_noShellQuote()
    {
        for (ICwlTool cwlTool : cwlTools.values())
        {
            cwlTool.setUpOption_noShellQuote();
        }
    }
    
    
    
    
    @Override
    public void setOption_optionalInputFiles()
    {
        for (ICwlTool cwlTool : cwlTools.values())
        {
            cwlTool.setUpOption_optionalInputFiles();
        }
    }
    
    
    
    
    @Override
    public void setOption_arrayFileInputs(String elementSeparator)
    {
        for (ICwlTool cwlTool : cwlTools.values())
        {
            cwlTool.setUpOption_inputArray(elementSeparator);
        }
    }
    
    
    
    
    @Deprecated
    @Override
    public void setOption_useDirectoryOutputs()
    {
        for (Tfunction function : bs2Doc.getFunctions())
        {
            
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    /*
        ============= Set up listener stuff ==============
    */
    
    
    /*
        adding and removing Listners and nofitying them about stuff ...
    */
    
    @Override
    public void addModelListener(IModelListener modelListener)
    {
        this.modelListeners.add(modelListener);
    }
    
    
    
    @Override
    public void removeModelListener(IModelListener modelListener)
    {
        this.modelListeners.remove(modelListener);
    }
    
    
    
    @Deprecated
    private void notifyModelListenersAboutNewDocument()
    {
        for (IModelListener l : this.modelListeners)
        {
            l.newDocumentCreated();
        }
    }
    
    
    
    @Deprecated
    private void notifyModelListenersAboutDocumentChange()
    {
        for (IModelListener l : this.modelListeners)
        {
            l.documentHasChanged();
        }
    }

    
    
    
    /**
     * Returns the first part of the given file path.
     * @param filePath
     * @return 
     */
    private String getParentDirectoryNameFromPath(String filePath)
    {
        System.out.println("Extracting parent dir from:   " + filePath);
        String name;
        int end = filePath.indexOf("/");
        
        if (end > 0)
        {
            name = filePath.substring(0, end);
        }
        else
        {
            name = filePath;
        }
        System.out.println("ParentDir is:   " + name);
        return name;
    }
}