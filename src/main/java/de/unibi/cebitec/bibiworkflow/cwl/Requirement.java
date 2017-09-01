/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

/**
 * abstract Requirement class every other requirement class extends this one.
 * @author pol3waf
 */
abstract class Requirement
{

    protected final String requirementClass;

    Requirement() {
        this.requirementClass = "";
    }
    
}



class ResourceRequirement extends Requirement
{
    
}
