package com.mycompany.tft.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author AZAEL
 */
public class MailSender {
    
    private static MailSender myself;
    private String mail;
    private final String username = "azaelteror777@gmail.com";
    private final String password = "msrtcmddvflwuevn";
    private int actuator;
    
    private MailSender(){
        myself=this;
    }
    
    public static MailSender getInstance(){
        return (myself!= null) ? myself : new MailSender();
    }
    
    public void setMail(String email){
        mail=email;
    }

    public void setActuator(int actuator) {
        this.actuator = actuator;
    }
    
    

    public void sendEmail() {
        Properties props = getProperties();
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = getMessage(session);
            Transport.send(message);
        } catch (MessagingException | UnknownHostException e) {
        }
    }

    private Message getMessage(Session session) throws MessagingException, UnknownHostException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(
                Message.RecipientType.TO, new InternetAddress(mail)
        );
        message.setSubject("BlotoothLock Triggeret");
        SimpleDateFormat dateForm = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hourForm = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        message.setText("Tu dispositivo "+InetAddress.getLocalHost().getHostName()+" ha "+ getAction()+ " el "+dateForm.format(date)+" a las"+hourForm.format(date)+".");
        return message;
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return props;
    }

    private String getAction() {
        switch(actuator){
            case 0: 
                return "desconectado la conexión a internet";
            case 1:
                return "sido apagado";
            case 2:
                return "bloqueado el usuario";
            default:
                break;
        }
        return "perdido la conexion con el dispositivo llave";
    }
}
