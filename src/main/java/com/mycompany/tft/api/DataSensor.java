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
    private Actuator action;
    private boolean exit=false;
    private InputStream deviceStream;
    private float batteryLvl;
    private int mode;
    private boolean enable;
    private Thread actionThread;
    private Thread bgThread;

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
            case 2:this.action=UserBlockActuator.getInstance();
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
            while(!exit){
                byte[] b= new byte[1024];
                deviceStream.skip(deviceStream.available()-4);
                int read = deviceStream.read(b,0,4);
                System.out.println(read);
                if(read==-1) throwActuator();
                else{
                    String data= new String(b);
                    if(data.contains("exit")) disconected();
                    else{
                        Float tmp = Float.parseFloat(data);
                        System.out.println(data);
                        if (tmp!=batteryLvl) {
                            batteryLvl=tmp;
                            Control.getInstance().updateBattery((int) batteryLvl);
                        }
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

    private void throwActuator() {
        System.out.println("Actuate");
        batteryLvl=-1;
        MailSender.getInstance().setActuator(mode);
        MailSender.getInstance().sendEmail();
        if(!enable) return;
        switch(mode){
            case 0:networkProtocol();
             break;
            case 1:action.actuate();
             break;
            case 2:userBlockProtocol();
             break;
        }
        if(enable){
            enable=false;
        }
    }

    public void setEnable(boolean b) {
        this.enable=b;
    }

    public void end(){
    }

    private void disconected() {
        Control.getInstance().cleanConnection();
        this.sleep();
    }

    private void networkProtocol() {
        action.actuate();
        Control.getInstance().reconnectConnection();
        ((NetworkShutdownActuator) action).reactuate();
    }

    private void userBlockProtocol() {
        bgThread=new Thread(){
            @Override
            public void run() {
                action.actuate();
            }   
        };
        bgThread.start();
        Control.getInstance().reconnectConnection();
        ((UserBlockActuator) action).dispose();
    }
}
