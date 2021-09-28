/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

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
}
