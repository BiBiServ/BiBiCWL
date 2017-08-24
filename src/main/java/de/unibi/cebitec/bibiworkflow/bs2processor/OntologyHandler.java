/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.bs2processor;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Logger;
import org.apache.derby.tools.sysinfo;
import org.mindswap.pellet.jena.PelletInfGraph;
import org.mindswap.pellet.jena.PelletReasonerFactory;

/**
 * The OntologyHandler is used for accessing the BibiTypes ontology
 * from the BibiServ group. 
 * Not to be mistaken for the ontoaccess library from Jan Krueger, Thomas Gatter
 * and Sven Hartmeier. This class just provides as much functionality to load 
 * the ontology with the pellet reasoner and access the hasBasespaceInputMapping
 * property of the formats defined in the BibiTypes ontology.
 * @author jsaydo
 */
public class OntologyHandler {
    
    private static OntModel model;
    private static Reasoner reasoner;
    //The Ontology and Data file to query against
    private static final String DATAFILEURL = "http://bibiserv.cebitec.uni-bielefeld.de/ontologies/BiBiTypes.owl";
    private static final String DATAFILERESOURCE = "/BiBiTypes.owl";
    // the nametype of the ontology
    public static final String bibitypesns = "http://bibiserv.cebitec.uni-bielefeld.de/ontologies/BiBiTypes.owl#";
    // logger
    private static final Logger LOG = Logger.getLogger(OntologyHandler.class.getName());

    
    
    
    /**
     * Default contructor. Loads the Bibiserv ontology from the website.
     */
    public OntologyHandler() 
    {
        // create ontology model with pellet reasoner
        model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        
        // get owl file from online resource
        InputStream is = FileManager.get().open(DATAFILEURL);
        // load the file into the model
        model.read(is, "");
        // preparemodel and let the reasoner do its work
        model.prepare();
        ((PelletInfGraph) model.getGraph()).classify();
        ((PelletInfGraph) model.getGraph()).realize();
    }
    
    
    
    /**
     * Alternative contructor. Loads an ontology from a local file.
     * @param filepath path to owl file
     */
    public OntologyHandler(String filepath) 
    {
        // create ontology model
        model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        
        // read from local file and create input stream
        InputStream is;
        
        try {
            
            // load local file into ontology model
            is = new FileInputStream(filepath);
            model.read(is, "");
            is.close();
            
            // preparemodel and let the reasoner do its work
            model.prepare();
            ((PelletInfGraph) model.getGraph()).classify();
            ((PelletInfGraph) model.getGraph()).realize();
            
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    
    
    /**
     * Searches for the hasBasespaceInputMapping property value of an individual
     * in the ontology.
     * @param individualName local name of the individual
     * @return value of the hasBasespaceInputMapping property as a string
     */
    public String getBasespaceInputType(String individualName) {
        
        //get the required property
        OntProperty myProperty = model.getOntProperty(
                "http://bibiserv.cebitec.uni-bielefeld.de/ontologies/BiBiTypes.owl#"
//                + "hasCardinality");
                 + "hasBasespaceInputMapping");
        
        // get the individual from ontology
        Individual myInd = model.getIndividual(
                "http://bibiserv.cebitec.uni-bielefeld.de/ontologies/BiBiTypes.owl#"
                + individualName);
        
        // access value of the hasBasespaceInputMapping property
        RDFNode value = myInd.getPropertyValue(myProperty);
        
        return value.toString();
        
    }
    
    
    
    
    
    
    
}
