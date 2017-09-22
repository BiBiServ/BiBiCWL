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
import de.unibi.techfak.bibiserv.cms.Tfunction;
import de.unibi.techfak.bibiserv.cms.TinputOutput;
import de.unibi.techfak.bibiserv.cms.Tparam;
import de.unibi.techfak.bibiserv.cms.TrunnableItem;

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
    private void convertBaseCommand()
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
    private void convertFunctionInputs(Tfunction function)
    {
        for (String s : bs2Doc.getCommandLineArgumentOrderAsReferences(function))
        {
            InputType inputType_bs2 = bs2Doc.getTypeOfInputArgumentsById(s);
            
            if (null == inputType_bs2)
            {
                System.out.println("nothing to convert here");
            }
            else switch (inputType_bs2) {
                case additionalString:
                    System.out.println("convert additional string");
                    break;
                case enumParam:
                    System.out.println("convert enumparam");
                    break;
                case input:
                    System.out.println("convert input");
                    break;
                case param:
                    System.out.println("convert param");
                    break;
                default:
                    System.out.println("nothing to convert here");
                    break;
            }
        }
    }
    
    private void convertOutputs()
    {
        
    }
    
    
    
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
        String prefix = input.getOption();                      // the option usually encompasses the separator in the bs2 format
        boolean separate = false;                               // should I really hard-code this?
        cwlTool.addInput(position, id, type, prefix, separate);
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
        cwlTool.addInput(position, id, type, prefix, separate);
    }
    
    
    
    /**
     * Converts a bs2 TenumParam into several CWL inputs. Depending on the type
     * of the TenumParam, choices can be combined or are exclusive and an
     * appropriate model will be created in the inputs of the CWL-Tool.
     * @param enumParam
     * @param position 
     */
    private void convertEnumParam(TenumParam enumParam, int position)
    {
        int numberOfValues = enumParam.getValues().size();
        
//        enumParam.getValues().get(position)
        
        String id = enumParam.getId();                                  // combine ID with key/ name of value
        String type = enumParam.getType().value();                      // can inputs be combined? or is it exclusice of its other options?
                                                                        // this has to be checked and then handled appropriately.
        String prefix = enumParam.getOption();
        boolean separate = false;
        
        // how do you add this?????
    }
    
    
    
    
}