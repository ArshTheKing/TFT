/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.gui;

import com.mycompany.tft.ctl.Control;
import com.mycompany.tft.objects.Device;
import com.mycompany.tft.objects.Params;
import java.awt.Component;
import java.util.Enumeration;
import javax.swing.AbstractButton;

/**
 *
 * @author Azael
 */
public class Config extends javax.swing.JFrame {

    private Device loaded;

    /**
     * Creates new form Config
     */
    public Config(Params params) {
        initComponents();
        this.setIconImage(CustomIcon.getIcon());
        this.setLocationRelativeTo(null);
        userField.setText(params.getUser());
        passwordField.setText(params.getPass());
        switch(params.getMode()){
            case 0:buttonGroup1.setSelected(red.getModel(), true);
                break;
            case 1:buttonGroup1.setSelected(apagar.getModel(), true);
                break;
            case 2:buttonGroup1.setSelected(sesion.getModel(), true);
                break;
        }
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        red = new javax.swing.JRadioButton();
        apagar = new javax.swing.JRadioButton();
        sesion = new javax.swing.JRadioButton();
        confirmar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        userPasswordChangeButton = new javax.swing.JButton();
        userField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Configuracion");

        jLabel6.setText("Efecto de perder la conexion con el smartphone:");

        buttonGroup1.add(red);
        red.setText("Desactivar interfaces de red (se reactivar?? al acercar el dispositivo llave)");

        buttonGroup1.add(apagar);
        apagar.setText("Apagar sistema automaticamente");

        buttonGroup1.add(sesion);
        sesion.setText("Cerrar sesion del usuario actual");

        confirmar.setText("Confirmar");
        confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarActionPerformed(evt);
            }
        });

        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        jLabel2.setText("Usuario y Contrase??a");

        userPasswordChangeButton.setText("Cambiar usuario y contrase??a");
        userPasswordChangeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userPasswordChangeButtonActionPerformed(evt);
            }
        });

        userField.setEditable(false);
        userField.setText("admin");
        userField.setDoubleBuffered(true);
        userField.setEnabled(false);

        passwordField.setEditable(false);
        passwordField.setText("admin");
        passwordField.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(confirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelar)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(userPasswordChangeButton)
                        .addGap(44, 44, 44))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(apagar)
                                    .addComponent(sesion)
                                    .addComponent(red)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel1))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 143, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(userPasswordChangeButton))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(red)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(apagar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sesion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmar)
                    .addComponent(cancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarActionPerformed
        Enumeration<AbstractButton> elements = buttonGroup1.getElements();
        String option = null;
        for (int i=0;elements.hasMoreElements();i++) {
            AbstractButton nextElement = elements.nextElement();
            if(nextElement.isSelected())option=String.valueOf(i);
        }
        String user = userField.getText();
        String pass = passwordField.getText();
        Control.getInstance().saveConfig(user,pass,option);
        this.dispose();
    }//GEN-LAST:event_confirmarActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        Control.getInstance().enableUI();
        this.dispose();
    }//GEN-LAST:event_cancelarActionPerformed

    private void userPasswordChangeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userPasswordChangeButtonActionPerformed
        this.setEnabled(false);
        new UserPasswordEdit(this, userField.getText(), passwordField.getText());
    }//GEN-LAST:event_userPasswordChangeButtonActionPerformed
    
    void setCredentials(String user, String pass) {
        userField.setText(user);
        passwordField.setText(pass);
        this.setEnabled(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton apagar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelar;
    private javax.swing.JButton confirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JRadioButton red;
    private javax.swing.JRadioButton sesion;
    private javax.swing.JTextField userField;
    private javax.swing.JButton userPasswordChangeButton;
    // End of variables declaration//GEN-END:variables
    

}
