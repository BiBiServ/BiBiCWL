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
import de.unibi.techfak.bibiserv.cms.TparamGroup.Paramref;
import java.net.URL;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author pol3waf
 */
public interface IBs2Document {
    
    
    public List<Tfunction> getFunctions();
    
    TinputOutput getIntputByReferenc(Inputref inputref);
    
    int getPositionOfInput(Inputref inputref);
    
    int getPositionOfParameter(Tparam param);
    
    int getPositionOfParameter(TenumParam enumparam);
    
    <T> T getParamByReference(Paramref paramref);
    
    List<TinputOutput> getCommandLineOrder(Tfunction function);
}
