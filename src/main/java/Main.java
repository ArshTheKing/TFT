import com.mycompany.tft.ctl.Control;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AZAEL
 */
public class Main {
    public static void main(String[] args) {
        Control.getInstance();
        
        /*FileHandler fileHandler = new FileHandler();
        fileHandler.writeDevice(new Device("1", "algo", "distinto"));
        SearchCommand sC = new SearchCommand();
        sC.setParameters(args);
        sC.execute();
        ArrayList<RemoteDevice> results =(ArrayList) sC.getResults();
        for (RemoteDevice dev : results) {
        System.out.println(dev.getFriendlyName(true)+" loacated");
        }*/
        
        
    }
}
