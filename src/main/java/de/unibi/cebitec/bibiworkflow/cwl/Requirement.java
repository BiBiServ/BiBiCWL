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
    
    @JsonProperty("class")
    protected String requirementClass;
    
}




/*
------------------------------------------------------------/
    Implementations of the abstract requirement class     /
========================================================/
*/

class DockerRequirement extends Requirement
{
    @JsonProperty
    private final String dockerPull;
    
    protected DockerRequirement(String dockerPull)
    {
        super.requirementClass = this.getClass().getSimpleName();
        this.dockerPull = dockerPull;
    }
    
    // TODO: do more ...
}




class EnvVarRequirement extends Requirement
{
    protected EnvVarRequirement()
    {
        super.requirementClass = this.getClass().getSimpleName();
    }
}




class InitialWorkdirRequirement extends Requirement
{
    protected InitialWorkdirRequirement()
    {
        super.requirementClass = this.getClass().getSimpleName();
    }
}




class InlineJavaScriptRequirement extends Requirement
{
    protected InlineJavaScriptRequirement()
    {
        super.requirementClass = this.getClass().getSimpleName();
    }
}




class SchemaDefRequirement extends Requirement
{
    protected SchemaDefRequirement()
    {
        super.requirementClass = this.getClass().getSimpleName();
    }
}




class ShellCommandRequirement extends Requirement
{
    protected ShellCommandRequirement()
    {
        super.requirementClass = this.getClass().getSimpleName();
    }
}




class SoftwareRequirement extends Requirement
{
    protected SoftwareRequirement()
    {
        super.requirementClass = this.getClass().getSimpleName();
    }
}




class ResourceRequirement extends Requirement
{
    protected ResourceRequirement()
    {
        super.requirementClass = this.getClass().getSimpleName();
    }
}




