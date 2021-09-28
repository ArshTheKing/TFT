/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.model.command;

import com.mycompany.tft.api.Sensor;
import com.mycompany.tft.objects.Device;

/**
 *
 * @author Azael
 */
public class StartCommand implements Command{
    Exception exception;

    private Device key;
    private Sensor sensor;
    private int time;
    private int mode;

    @Override
    public void setParameters(Object... args) {
        key = (Device) args[0];
        time= (int) args[1];
        mode= (int) args[2];
    }

    @Override
    public void execute() {
        this.sensor=Sensor.getInstance();
        sensor.setKey(key);
        sensor.setTime(time);
        sensor.setMode(mode);
        sensor.begin();
    }

    @Override
    public Object getResults() {
        return sensor.getState().toString();
    }
    
}
