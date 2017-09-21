/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.converter;

import de.unibi.cebitec.bibiworkflow.bs2.Bs2Document;
import de.unibi.cebitec.bibiworkflow.bs2.InputType;
import de.unibi.cebitec.bibiworkflow.cwl.CwlTool;
import de.unibi.techfak.bibiserv.cms.Tfunction;
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
    
}
