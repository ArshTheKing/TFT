/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import com.mycompany.tft.objects.Device;
import com.mycompany.tft.objects.Params;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author AZAEL
 */
public class FileHandler {
    private static String dirPath="c:\\\\BluetoothLock\\\\savedDevices";
    private static File deviceFile=new File("c:\\\\BluetoothLock\\\\savedDevices\\\\deviceFile.txt");
    private static File paramFile=new File("c:\\\\BluetoothLock\\\\savedDevices\\\\paramFile.txt");
    static {
        if(!deviceFile.exists()) 
        {
            (new File(dirPath)).mkdirs();
            try {
                deviceFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(!paramFile.exists()) 
        {
            (new File(dirPath)).mkdirs();
            try {
                paramFile.createNewFile();
                writeConfig("admin", "admin", "2");
            } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static ArrayList<Device> readDevice() throws IOException, FileNotFoundException{
        ArrayList<Device> temp= new ArrayList<>();
        BufferedReader reader;

        reader = new BufferedReader(new FileReader(deviceFile));
        String line = reader.readLine();
        while (line != null) {
                String[] split = line.split(";");
                temp.add(new Device(split[0], split[1], split[2]));
                line = reader.readLine();
        }
        reader.close();
        
        return temp;
    }
    
    public static void writeDevice(Device dev) throws IOException{
        FileWriter fr;
            fr = new FileWriter(deviceFile, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.append(dev.getId()+";"+dev.getName()+";"+dev.getMail()+System.lineSeparator());
            br.close();
            fr.close();
        

    }
    
    public static Params readConfig() throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(paramFile));
        String user,pass,mode;
        String line = reader.readLine();
        user = line;
        line = reader.readLine();
        pass=line;
        line = reader.readLine();
        mode=line;
        reader.close();
        return new Params(user,pass,line);
        
    }
    
    public static void writeConfig(String user, String pass, String option) throws IOException {
        FileWriter fileWriter;
            fileWriter = new FileWriter(paramFile);
        
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, 10);
        bufferedWriter.write(user);
        bufferedWriter.newLine();
        bufferedWriter.write(pass);
        bufferedWriter.newLine();
        bufferedWriter.write(option);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }
    
    
}
