/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.bs2;

import de.unibi.techfak.bibiserv.cms.TenumParam;
import de.unibi.techfak.bibiserv.cms.Tfunction;
import de.unibi.techfak.bibiserv.cms.Tfunction.Inputref;
import de.unibi.techfak.bibiserv.cms.TinputOutput;
import de.unibi.techfak.bibiserv.cms.Tparam;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pol3waf
 */
public interface IBs2Document {
    
    
    public List<Tfunction> getFunctions();
    
    TinputOutput getIntputById(String id) throws Exception;
    
    int getPositionOfInput(Inputref inputref);
    
    int getPositionOfParameter(Tparam param);
    
    int getPositionOfParameter(TenumParam enumparam);
    
    Tparam getParamById(String id) throws Exception;
    
    TenumParam getEnumParamById(String id) throws Exception;
    
    /**
     * Gets a list of references of the command line arguments in the order they 
     * should be used. 
     * @param function the function that should be checked for its argument order
     * @return ordered list of IDs of each input or parameter
     */
    ArrayList<String> getCommandLineArgumentOrderAsReferences(Tfunction function);
    
    /**
     * Get the base command of the bs2-file which would be used on the command 
     * line to start the tool described in the bs2-file. Often this base command
     * does not suffice and it has to be combined with different command line 
     * parameters/ options/ arguments (e.g. from function input and parameters).
     * @param function the function
     * @return 
     */
    String getBaseCommand() throws Exception;
    
    
    /**
     * Checks what type of "input" an id is pointing at. The function checks 
     * all TinputOutputs, Tparams, TenumParams of the TrunnableItem and the 
     * additionalStrings list of the bs2Document.
     * @param id ID of the object to be
     * @return type of the input or null if didn't find ID
     */
    InputType getTypeOfInputArgumentsById(String id) throws Exception;
    
    /**
     * Gets the "additionalString" with the assigned id.
     * @param id
     * @return 
     */
    String getAdditionalStringById(String id) throws Exception;
    
}
