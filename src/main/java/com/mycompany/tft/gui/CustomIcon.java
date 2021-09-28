/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 *
 * @author AZAEL
 */
public class CustomIcon {
    
    public static BufferedImage getIcon() {
        int size=32;
        BufferedImage i = new BufferedImage(
                size, size, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g = i.createGraphics();
        Image icon = Toolkit.getDefaultToolkit().getImage("images/test.png");
        g.drawImage(icon, 0, 0, size, size, null);
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, size, size);
        g.setColor(Color.BLUE);
        g.fillRect(5, 5, size-10, size-10);
        g.setColor(Color.WHITE);
        int off = (size>17 ? 3 : 1);
        if (off>1) g.drawRect(0, 0, size-1, size-1);
        Font font = new Font("Agency FB", Font.BOLD, 16);
        g.setFont(font);
        g.drawString("BL", off+8, size-off-8);

        g.dispose();

        return i;
    }    
}

