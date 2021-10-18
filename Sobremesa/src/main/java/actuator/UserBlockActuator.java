/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actuator;

import com.mycompany.tft.gui.LockScreen;
import com.mycompany.tft.ctl.Control;
import com.mycompany.tft.objects.Params;

/**
 *
 * @author AZAEL
 */
public class UserBlockActuator implements Actuator{

    private static UserBlockActuator myself;
    private LockScreen lockScreen;
    
    private UserBlockActuator(){
        myself=this;
    }
    
    public static UserBlockActuator getInstance(){
        return (myself!=null)? myself: new UserBlockActuator();
    }
    @Override
    public void actuate() {
        Params params=Control.getInstance().getParams();
        lockScreen = new LockScreen(params.getUser(),params.getPass());
    }
    
    public void dispose(){
        lockScreen.dispose();
    }
}
