/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.objects;

/**
 *
 * @author AZAEL
 */
public class Params {
    String user;
    String pass;
    int mode;

    public Params(String user, String pass, String mode) {
        this.user = user;
        this.pass = pass;
        this.mode = Integer.parseInt(mode);
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public int getMode() {
        return mode;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public void setMode(String mode) {
        this.mode = Integer.parseInt(mode);
    }

    @Override
    public String toString() {
        return "Params{" + "user=" + user + ", pass=" + pass + ", mode=" + mode + '}';
    }
    
    
}
