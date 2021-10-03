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
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

/**
 *
 * @author Azael
 */
public class ConnectionSensor extends Thread{
    
    private static final String myServiceName = "MyBtService";
    private final StreamConnectionNotifier notifier;

    public ConnectionSensor(StreamConnectionNotifier notifier) {
        this.notifier=notifier;
    }   
    
    @Override
    public void run() {
        try {
            StreamConnection sc = notifier.acceptAndOpen();
            RemoteDevice rd = RemoteDevice.getRemoteDevice(sc);
            System.out.println(rd.getBluetoothAddress());
            InputStream stream = sc.openInputStream();
            Control.getInstance().setConnection(rd,stream);
        } catch (BluetoothStateException ex) {
            Control.getInstance().showBTDisconnected();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionSensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
