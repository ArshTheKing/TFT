/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.model.command;

import com.mycompany.tft.api.FileHandler;
import com.mycompany.tft.objects.Device;
import com.mycompany.tft.objects.Params;
import java.io.IOException;

/**
 *
 * @author AZAEL
 */
public class UpdateCommand implements Command{
    Exception exception;
    private Params params;

    @Override
    public void setParameters(Object... args) {
        this.params= new Params(((String) args[0]),((Device) args[1]),((String) args[2]));
    }

    @Override
    public void execute() {
        /*try {
            FileHandler.writeConfig(params.getInterval(), params.getKey().getId(),params.getMode());
        } catch (IOException ex) {
            exception=ex;
        }*/
    }

    @Override
    public Object getResults() {
        return exception;
    }
    
}
