/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.model.command;

import com.mycompany.tft.api.FileHandler;
import com.mycompany.tft.objects.Device;
import java.io.IOException;

/**
 *
 * @author AZAEL
 */
public class RegisterCommand implements Command{
    Exception exception;
    private Device arg;

    @Override
    public void setParameters(Object... args) {
        arg = (Device) args[0];
    }

    @Override
    public void execute() {
        try {
            FileHandler.writeDevice(arg);
        } catch (IOException ex) {
            exception=ex;
        }
    }

    @Override
    public Object getResults() {
        return exception;
    }
    
}
