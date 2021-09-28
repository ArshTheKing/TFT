/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actuator;

import com.mycompany.tft.api.LockScreen;
import com.mycompany.tft.ctl.Control;
import com.mycompany.tft.objects.Params;

/**
 *
 * @author AZAEL
 */
public class UserBlockActuator implements Actuator{

    @Override
    public void actuate() {
        Params params=Control.getInstance().getParams();
        LockScreen lockScreen = new LockScreen(params.getUser(),params.getPass());
    }
    
}
