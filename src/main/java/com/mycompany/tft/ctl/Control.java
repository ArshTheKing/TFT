/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.ctl;

import com.mycompany.tft.api.ConnectionSensor;
import com.mycompany.tft.api.DataSensor;
import com.mycompany.tft.api.ExtraMethods;
import com.mycompany.tft.api.FileHandler;
import com.mycompany.tft.api.MailSender;
import com.mycompany.tft.gui.Config;
import com.mycompany.tft.gui.Linking;
import com.mycompany.tft.gui.MainFrame;
import com.mycompany.tft.model.command.*;
import com.mycompany.tft.objects.Device;
import com.mycompany.tft.objects.Params;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;
import javax.swing.JOptionPane;

/**
 *
 * @author AZAEL
 */
public class Control {
    private static Control myself;
    private static String localName;
    private static MainFrame ui;
    private static ArrayList<Device> dev=new ArrayList<>();
    private Device keyDevice;
    private Params params;
    private RemoteDevice key;
    private InputStream connection;
    private DataSensor dataSensor;
    
    private Control(){
        this.myself=this;
        ui=new MainFrame(this);
        ui.setEnabled(false);
        getLocalName();
        ui.setEnabled(true);
        try {
            dev=FileHandler.readDevice();
            params= FileHandler.readConfig();
            dataSensor= new DataSensor(params.getMode());
        } catch (IOException ex) {
            System.out.println("Error al leer los archivos");
        }
        
    }
    
    public static Control getInstance(){
        return (myself==null) ? new Control(): myself;
    }

    public DataSensor getDataSensor() {
        return dataSensor;
    }

    public void setDataSensor(DataSensor dataSensor) {
        this.dataSensor = dataSensor;
    }
    
    
    public void searchDevice() {
        ui.setEnabled(false);
        ui.toFront();
        Linking linking= Linking.getIntance();
        linking.setLocationRelativeTo(ui);
        linking.setAlwaysOnTop(true);
        ConnectionSensor sensor = new ConnectionSensor();
        sensor.start();
        try {
            sensor.join(1000*60);
        } catch (InterruptedException ex) {
        }
        if(sensor.isAlive()&&connection==null){
            linking.dispose();
            JOptionPane.showMessageDialog(ui, "No se detecta ningun dispositivo, vuelva a intentarlo");
            sensor.stop();
        } else{
            ui.enableSensor(true);
            dataSensor.setKey(connection);
        }
        enableUI();
    }
    
    public void saveConfig(String user, String pass, String option) {
        try {
            this.params=new Params(user, pass, option);
            FileHandler.writeConfig(user, pass, option);
        } catch (IOException ex) {
        }
        enableUI();
    }
    
    public void saveDevice(Device dev){
       RegisterCommand rc = new RegisterCommand();
       rc.setParameters(dev);
       rc.execute();
       rc.getResults();
       this.dev.add(dev);
    }
    
    
    public void setKeyDevice(Device dev) {
        this.keyDevice=dev;
        ui.setKeyDeviceTag(dev.getName());
        MailSender.getInstance().setMail(keyDevice.getMail());
    }

    public void stopSensor() {
        ui.setEnabled(false);
        ui.setEnabled(true);
    }
    
    public void enableSensor(boolean b) {
        dataSensor.setEnable(b);
    }
    
    public void startSensor() {
        dataSensor.setKey(connection);
        dataSensor.begin(connection);
    }

    public boolean isKeyDeviceSet() {
        return (keyDevice!=null)? true:false;
    }

    public void config() {
        Params readConfig;
        try {
            readConfig = FileHandler.readConfig();
            new Config(readConfig);
            ui.setEnabled(false);
        } catch (IOException ex) {}
    }

    public ArrayList<Device> getDevices() {
        if(dev.isEmpty()) try {
            dev = FileHandler.readDevice();
        } catch (IOException ex) {
            System.out.println("Error al leer los archivos");
        }
        return dev;
    }
    
    public void enableUI() {
        ui.setEnabled(true);
        ui.toFront();
    }

    public String getLocalName() {
        if(localName==null){
            String name = ExtraMethods.getLocalName();
            if(!name.equals(ExtraMethods.BLUETOOTH_DISCONNECTED)) localName=name;
        }
        return localName;
    }


    public void showBTDisconnected() {
        while(Control.getInstance().getLocalName()==null)
            JOptionPane.showMessageDialog(ui, "Por favor active la funcionalidad bluetooth del dispositivo");
    }

    public void setConnection(RemoteDevice rd, InputStream stream) {
        this.key=rd;
        this.connection=stream;
        loadEmail();
        dataSensor.setKey(stream);
        dataSensor.begin(stream);
        Linking.getIntance().dispose();
    }

    public void updateBattery(int batteryLvl) {
        ui.setStatusTag(batteryLvl+"",true);
    }

    private void loadEmail() {
        Device save = null;
        for (Device device : Control.dev) {
            if(device.getId().equals(this.key.getBluetoothAddress())) {
                save=device;
                break;
            }
        }
        if(save!=null) setKeyDevice(save);
        else registerDevice();
    }

    private void registerDevice() {
        try {
            Linking.getIntance().dispose();
            String mail = JOptionPane.showInputDialog(ui, "No hay registros del dispositivo, por favor introduzca un e-mail:");
            Device device = new Device(key.getBluetoothAddress(), key.getFriendlyName(true), mail);
            saveDevice(device);
            setKeyDevice(device);
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Params getParams() {
        return params;        
    }

    public void cleanConnection() {
        //TODO 
        //DataSensor.getInstance().setEnable(false);
    }

    public void rebootSensor() {
        ui.dispose();
        ui=new MainFrame(this);
        new Control();
    }


}
