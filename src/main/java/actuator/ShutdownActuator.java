/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actuator;

import java.io.IOException;

/**
 *
 * @author AZAEL
 */
public class ShutdownActuator implements Actuator{

    @Override
    public void actuate() {
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec("shutdown -s -t 0");
        } catch (IOException ex) {
            actuate();
        }
    }
    
}
