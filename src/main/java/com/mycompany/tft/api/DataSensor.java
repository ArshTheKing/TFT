/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import actuator.Actuator;
import actuator.NetworkShutdownActuator;
import actuator.ShutdownActuator;
import actuator.UserBlockActuator;
import com.mycompany.tft.ctl.Control;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AZAEL
 */
public class DataSensor extends Thread{
    private static  DataSensor myself;
    private Actuator action;
    private boolean exit;
    private InputStream deviceStream;
    private float batteryLvl;
    private int mode;
    private boolean enable;

    public DataSensor(int actuator) {
        super();
        setAction(actuator);
    }

    private void setAction(int actuator) {
        this.mode=actuator;
        switch(actuator){
            case 0:this.action=new NetworkShutdownActuator();
             break;
            case 1:this.action=new ShutdownActuator();
             break;
            case 2:this.action=new UserBlockActuator();
             break;
        }
    }
    
    public void setKey(InputStream stream) {
        this.deviceStream = (InputStream) stream;
    }

    
    public void setMode(int mode) {
        setAction(mode);
    }
    

    public void run() {
        try {
            while(true){
                byte[] b= new byte[1024];
                int read = deviceStream.read(b,0,4);
                String data= new String(b);
                if(data.contentEquals("exit")) {
                    action.actuate();
                    sleep();
                    continue;
                }
                if(read==-1) disconected();
                else{
                    Float tmp = Float.parseFloat(data);
                    System.out.println(data);
                    if (tmp!=batteryLvl) {
                        batteryLvl=tmp;
                        Control.getInstance().updateBattery((int) batteryLvl);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DataSensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sleep() {
        this.suspend();
    }

    public void begin(InputStream stream) {
        Thread.State state=getState();
        if(state.equals(Thread.State.NEW)) this.start();
        else {
            this.deviceStream=stream;
            this.resume();
        }
    }

    private void disconected() {
        if(enable){
            action.actuate();
            this.sleep();
        }
    }

    public void setEnable(boolean b) {
        this.enable=b;
    }

    public void end(){
    }
}
