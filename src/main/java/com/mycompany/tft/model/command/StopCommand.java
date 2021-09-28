/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.model.command;

import com.mycompany.tft.api.Sensor;

/**
 *
 * @author Azael
 */
public class StopCommand implements Command{
    private Sensor instance;

    @Override
    public void setParameters(Object... args) {
        instance = Sensor.getInstance();
    }

    @Override
    public void execute() {
        instance.sleep();
    }

    @Override
    public Object getResults() {
        return instance.isAlive();
    }
    
}
