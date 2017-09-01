/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

/**
 *
 * @author pol3waf
 */
class OutputBinding {
    
    private final String glob;
    
    // what is this???
    private final boolean loadContent;
    
    // what is this???
    private final String outputEval;
    
    
    
    protected OutputBinding(final String glob, final boolean loadContent, final String outputEval)
    {
        this.glob = glob;
        this.loadContent = loadContent;
        this.outputEval = outputEval;
    }
    
}
