/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.model.command;

/**
 *
 * @author AZAEL
 */
public interface Command {
    
    
    void setParameters(Object... args);
    void execute();
    Object getResults();
}
