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
abstract class Input 
{
    protected String id;
    
    protected String type;
    
    protected InputBinding inputBinding;
    
    protected String format;
    
    
    
    protected String getId()
    {
        return this.id;
    }
}
