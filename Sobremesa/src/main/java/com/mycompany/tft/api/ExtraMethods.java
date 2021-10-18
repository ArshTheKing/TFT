/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnectionNotifier;

/**
 *
 * @author Azael
 */
public class ExtraMethods {

    /**
     *
     */
    public static final String BLUETOOTH_DISCONNECTED="BLUETOOTH_DISCONNECTED";
    
    public static String getLocalName(){
        try {
            return LocalDevice.getLocalDevice().getFriendlyName();
        } catch (BluetoothStateException ex) {
            return "BLUETOOTH_DISCONNECTED";
        }
    }
    private static final String myServiceName = "MyBtService";
    public static StreamConnectionNotifier getBTNotifier(){
        LocalDevice localDevice;
        try {
            localDevice = LocalDevice.getLocalDevice();
            System.out.println(localDevice.getBluetoothAddress());
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            String connURL = "btspp://localhost:"+"7f49f6fa12e511ec82a80242ac130003"+
                    ";name="+myServiceName;
            return (StreamConnectionNotifier) Connector.open(connURL);
        } catch (Exception ex) {
            return null;
        }
    }
    
}
