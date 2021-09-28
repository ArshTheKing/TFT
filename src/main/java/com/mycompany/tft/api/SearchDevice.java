/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import com.mycompany.tft.gui.DeviceList;
import com.mycompany.tft.objects.Device;
import java.io.IOException;
import java.util.ArrayList;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;

/**
 *
 * @author AZAEL
 */
public class SearchDevice {
    private static DiscoveryListener search;
    private static DiscoveryListener searchDevice;
    public static final ArrayList/*<RemoteDevice>*/ devicesDiscovered = new ArrayList();
    private static final boolean[] present=new boolean[]{false};
    
    public static ArrayList search() throws BluetoothStateException, InterruptedException {
        final Object inquiryCompletedEvent = new Object();

        devicesDiscovered.clear();

        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                try {
                    System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
                    System.out.println(btDevice.getFriendlyName(true));
                    //Establecermos una conexion basica para verificar la presencia del dispositivo
                    Connection open = Connector.open("btspp://"+btDevice.getBluetoothAddress()+":1;master=false;authenticate=false;encrypt=false;");
                } catch (IOException ex) {
                    String substring = ex.getLocalizedMessage().substring(ex.getLocalizedMessage().indexOf("[")+1,ex.getLocalizedMessage().indexOf("]"));
                    if(substring.equals(10064+"")) 
                    devicesDiscovered.add(btDevice);
                    try {
                        DeviceList.getInstance().addDev(new Device(btDevice.getBluetoothAddress(), btDevice.getFriendlyName(true), null));
                    } catch (IOException ex1) {
                        
                    }
                }
            }

            public void inquiryCompleted(int discType) {
                System.out.println("Device Inquiry completed!");
                synchronized(inquiryCompletedEvent){
                    inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized(inquiryCompletedEvent) {
            search=listener;
            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
            if (started) {
                System.out.println("wait for device inquiry to complete...");
                inquiryCompletedEvent.wait();
                System.out.println(devicesDiscovered.size() +  " device(s) found");
            }
        }
        DeviceList.getInstance().searchEnd();
        return devicesDiscovered;
    }

    

    public static Boolean searchDevice(String key) throws BluetoothStateException, InterruptedException {
        final Object inquiryCompletedEvent = new Object();
        present[0]=false;

        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                try {
                    System.out.println(btDevice.getFriendlyName(true));
                    //Establecermos una conexion basica para verificar la presencia del dispositivo
                    if(btDevice.getBluetoothAddress().equals(key)) Connector.open("btspp://"+btDevice.getBluetoothAddress()+":1;master=false;authenticate=false;encrypt=false;");
                } catch (IOException ex) {
                    String substring = ex.getLocalizedMessage().substring(ex.getLocalizedMessage().indexOf("[")+1,ex.getLocalizedMessage().indexOf("]"));
                    if(substring.equals(10064+"")) present[0]=true;
                    try {
                        LocalDevice.getLocalDevice().getDiscoveryAgent().cancelInquiry(this);
                    } catch (BluetoothStateException ex1) {
                        //TODO
                    }
                    inquiryCompletedEvent.notifyAll();
                    
                }
            }

            public void inquiryCompleted(int discType) {
                System.out.println("Device Inquiry completed!");
                synchronized(inquiryCompletedEvent){
                    inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized(inquiryCompletedEvent) {
            searchDevice=listener;
            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
            if (started) {
                System.out.println("wait for device inquiry to complete...");
                inquiryCompletedEvent.wait();
            }
        }
        return present[0];
    }
    
    public static void cancelInquiry(int inquiry){
        if(inquiry==0){
            try {
                LocalDevice.getLocalDevice().getDiscoveryAgent().cancelInquiry(search);
            } catch (Exception ex) {
            }
        } else {
            try {
                LocalDevice.getLocalDevice().getDiscoveryAgent().cancelInquiry(searchDevice);
            } catch (Exception ex) {
            }
            
        }
        
    }
}
