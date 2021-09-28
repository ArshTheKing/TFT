/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.model.command;

import com.mycompany.tft.api.SearchDevice;
import com.mycompany.tft.objects.Device;
import java.util.ArrayList;
import javax.bluetooth.RemoteDevice;

/**
 *
 * @author AZAEL
 */
public class SearchCommand implements Command{
    Exception exception;
    private ArrayList<Device> devices;
    
    
    @Override
    public void setParameters(Object... args) {
        exception=null;
        devices=new ArrayList<>();;
    }

    @Override
    public void execute() {
        try {
            ArrayList<RemoteDevice> search = SearchDevice.search();
            for (RemoteDevice dev : search) {
                Device device = new Device(dev.getBluetoothAddress(), dev.getFriendlyName(true), null);
                devices.add(device);
            }
        } catch (Exception ex) {
            exception=ex;
        }
    }

    @Override
    public Object getResults() {
        return (exception==null) ? devices : exception;
    }
    
}
