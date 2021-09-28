/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import com.mycompany.tft.ctl.Control;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

/**
 *
 * @author Azael
 */
public class ConnectionSensor extends Thread{
    
    private static final String myServiceName = "MyBtService";
    
    
    @Override
    public void run() {
        try {
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            StreamConnectionNotifier streamConnectionNotifier;
            String connURL = "btspp://localhost:"+"7f49f6fa12e511ec82a80242ac130003"+
                    ";name="+myServiceName;
            streamConnectionNotifier = (StreamConnectionNotifier) Connector.open(connURL);
            StreamConnection sc = streamConnectionNotifier.acceptAndOpen();
            RemoteDevice rd = RemoteDevice.getRemoteDevice(sc);
            InputStream stream = sc.openInputStream();
            Control.getInstance().setConnection(rd,stream);
        } catch (BluetoothStateException ex) {
            Control.getInstance().showBTDisconnected();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionSensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
