/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import com.mycompany.tft.ctl.Control;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Azael
 */
public class LockScreen {

    private final String user;
    private final String password;
    
    private static File bg=new File("src/main/java/com/mycompany/tft/api/background.jpg");
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final JFrame frame;
    
    public LockScreen(String user,String pass){
        this.user=user;
        this.password=pass;
        
        frame= new JFrame();
        frame.setBounds(0,-10,screenSize.width,screenSize.height);
        frame.setUndecorated(true); //Uncomment at realese
        JLayeredPane jLayeredPane1 = new javax.swing.JLayeredPane();
        jLayeredPane1.setLayout(null);
        JPanel bgPanel = backgroundPanel();
        jLayeredPane1.add(bgPanel);
        jLayeredPane1.setLayer(bgPanel, 0);
        JPanel formPanel=formPanel();
        jLayeredPane1.add(formPanel);
        jLayeredPane1.setLayer(formPanel, 1);
        frame.add(jLayeredPane1);
        frame.setVisible(true);;
    }
    
    private JFrame MainFrame(){
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true); //Uncomment at realese
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        return frame;
    }
    
    private javax.swing.JTextField userField=new JTextField(20);
    private javax.swing.JTextField passwordField=new JPasswordField(20);
    
    private void loadLockFrame(JFrame frame) {
        JLayeredPane jLayeredPane1 = new javax.swing.JLayeredPane();
        jLayeredPane1.setLayout(null);
        JPanel bgPanel = backgroundPanel();
        jLayeredPane1.add(bgPanel);
        jLayeredPane1.setLayer(bgPanel, 0);
        JPanel formPanel=formPanel();
        jLayeredPane1.add(formPanel);
        jLayeredPane1.setLayer(formPanel, 1);
        frame.add(jLayeredPane1);
    }

    private JPanel backgroundPanel() {
        JPanel panel= new JPanel();
        JLabel background=new JLabel();
        try {
            BufferedImage img = ImageIO.read(bg);
            Image scaledInstance = img.getScaledInstance((int) screenSize.width,
                                                        (int) screenSize.height+10, 8);
            background=new JLabel(new ImageIcon(scaledInstance));
        } catch (IOException ex) {
            background.setBackground(Color.cyan);
        }
        panel.add(background);
        background.setBounds(0, 0, screenSize.width, screenSize.height);
        panel.setBounds(0,-10,screenSize.width,screenSize.height+10);
        return panel;
    }

    private JPanel formPanel() {
        JPanel form = new JPanel();
        form.add(new JLabel("User and Password"));
        form.add(userField);
        form.add(passwordField);
        JButton verify = new JButton("Submit");
        verify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = userField.getText();
                String text1 = passwordField.getText();
                if(text.equals(user)&&text1.equals(password)) unlock();
             }

            private void unlock() {
                frame.dispose();
                Control.getInstance().reconnectConnection();
            }
        });
        form.add(verify);
        frame
                .getRootPane().setDefaultButton(verify);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e) {
              System.exit(0);
          }
        });
        form.add(closeButton);
        form.setBounds(screenSize.width/2-125,screenSize.height/2-60,250,120);
        form.setOpaque(false);
        return form;
    }

    public void dispose() {
        frame.dispose();
    }
}
