/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.bs2;

import de.unibi.techfak.bibiserv.cms.TenumParam;
import de.unibi.techfak.bibiserv.cms.Tfunction;
import de.unibi.techfak.bibiserv.cms.TinputOutput;
import de.unibi.techfak.bibiserv.cms.Tparam;
import de.unibi.techfak.bibiserv.cms.TparamGroup;
import de.unibi.techfak.bibiserv.cms.TrunnableItem;
import java.net.URL;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author pol3waf
 */
public class Bs2Document implements IBs2Document {

    
    private TrunnableItem runnableItem;
    
    
    public Bs2Document(String path) throws JAXBException
    {
        this.runnableItem = loadBs2FileRootElement(path);
    }
    
    
    
    @Override
    public List<TinputOutput> getCommandLineOrder(Tfunction function)
    {
        List<JAXBElement<?>> list = function.getParamAndInputOutputOrder().getReferenceOrAdditionalString();
        for (JAXBElement<?> e : list)
        {
            System.out.println("my name is " + e.getName().toString());
            System.out.println("im of class " + e.getDeclaredType().toString());
        }
        
        return null;
    }

    @Override
    public List<Tfunction> getFunctions() {
        List functionList = runnableItem.getExecutable().getFunction();
        return functionList;
    }

    @Override
    public TinputOutput getIntputByReferenc(Tfunction.Inputref inputref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T getParamByReference(TparamGroup.Paramref paramref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPositionOfInput(Tfunction.Inputref inputref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPositionOfParameter(TenumParam enumparam) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPositionOfParameter(Tparam param) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    private TrunnableItem loadBs2FileRootElement(String path) throws JAXBException
    {
        Source source = new StreamSource(path);
        JAXBContext ctx = JAXBContext.newInstance(new Class[] {TrunnableItem.class});
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        TrunnableItem root = unmarshaller.unmarshal(source, TrunnableItem.class).getValue();
        
        return root;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
