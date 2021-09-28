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
import com.mycompany.tft.objects.Device;
import javax.bluetooth.BluetoothStateException;

/**
 *
 * @author AZAEL
 */
public class Sensor extends Thread{
    private Device key;
    private int time;
    private static  Sensor myself;
    private Actuator action;
    private boolean exit;

    private Sensor(Device key, int time, int actuator) {
        super();
        this.key = key;
        this.time = time*1000;
        setAction(actuator);
    }
    public static Sensor getInstance(){
        return (myself== null) ? myself=new Sensor(null, 1,0): myself;
    }

    private void setAction(int actuator) {
        switch(actuator){
            case 0:this.action=new NetworkShutdownActuator();
             break;
            case 1:this.action=new ShutdownActuator();
             break;
            case 2:this.action=new UserBlockActuator();
             break;
        }
    }
    
    public void setKey(Device key) {
        this.key = key;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    public void setMode(int mode) {
        setAction(mode);
    }
    

    public void run() {
        while (true) {            
            try {
                sleep(time);
            } catch (InterruptedException ex) {
                System.out.println("Error en el sensor");
            }
                System.out.println("Busqueda iniciada");
                searchKeyDevice();
        }
    }

    private void searchKeyDevice() {
        Boolean search = false; 
        try {
            search = SearchDevice.searchDevice(key.getId());
        } catch (BluetoothStateException | InterruptedException ex) {
            System.out.println("Internal problem");
            SearchDevice.cancelInquiry(1);
        }
        if(!search) MailSender.getInstance().sendEmail();//action.actuate();
        
    }

    public void sleep() {
        SearchDevice.cancelInquiry(1);
        this.suspend();
    }

    public void begin() {
        Thread.State state=getState();
        if(state.equals(Thread.State.NEW)) this.start();
        else this.resume();
    }


    
    
}
