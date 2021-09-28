/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.model.command;

import com.mycompany.tft.api.FileHandler;
import com.mycompany.tft.objects.Device;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author AZAEL
 */
public class ReadCommand implements Command{

    private ArrayList<Device> devices;
    private IOException exception;

    @Override
    public void setParameters(Object... args) {
        devices=null;
        exception=null;
    }

    @Override
    public void execute() {
        try {
            devices = FileHandler.readDevice();
        } catch (IOException ex) {
            exception=ex;
        }
    }

    @Override
    public Object getResults() {
        return (exception==null) ? devices : exception;
    }
    
}
