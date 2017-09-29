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
public class FileInput extends Input
{
    String fileType;
    
    public FileInput(int position, String id, String prefix, boolean separate, String fileType)
    {
        super.type = "File";
        super.id = id;
        super.inputBinding = new InputBinding(prefix, separate, position);
        this.fileType = fileType;
    }
}
