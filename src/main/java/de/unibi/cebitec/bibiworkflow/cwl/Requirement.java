/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiworkflow.cwl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * abstract Requirement class every other requirement class extends this one.
 * @author pol3waf
 */
abstract class Requirement
{
    
    @JsonProperty
    protected String requirementClass;

//    Requirement() {
//        this.requirementClass = "";
//    }
    
}



class DockerRequirement extends Requirement
{
    protected DockerRequirement()
    {
        super.requirementClass = this.getClass().getName();
    }
    
    // TODO: do more ...
}


class EnvVarRequirement extends Requirement
{
    protected EnvVarRequirement()
    {
        super.requirementClass = this.getClass().getName();
    }
}

class InitialWorkdirRequirement extends Requirement
{
    protected InitialWorkdirRequirement()
    {
        super.requirementClass = this.getClass().getName();
    }
}

class InlineJavaScriptRequirement extends Requirement
{
    protected InlineJavaScriptRequirement()
    {
        super.requirementClass = this.getClass().getName();
    }
}

class SchemaDefRequirement extends Requirement
{
    protected SchemaDefRequirement()
    {
        super.requirementClass = this.getClass().getName();
    }
}

class ShellCommandRequirement extends Requirement
{
    protected ShellCommandRequirement()
    {
        super.requirementClass = this.getClass().getName();
    }
}

class SoftwareRequirement extends Requirement
{
    protected SoftwareRequirement()
    {
        super.requirementClass = this.getClass().getName();
    }
}

class ResourceRequirement extends Requirement
{
    protected ResourceRequirement()
    {
        super.requirementClass = this.getClass().getName();
    }
}

