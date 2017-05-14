/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hukuk.core;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Toshiba
 */
@ManagedBean(name = "BeanSifremiUnuttum")
@SessionScoped
public class SifremiUnuttum {
    
    final String user = "hukukwebproje@gmail.com";
    final String pass = "hukukweb1";
    
    private String to="";

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    
    public String mailGonder()
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Authenticator authenticator = new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication( user, pass );
            }
        };
        Session session = Session.getInstance( props, authenticator );
        
        try 
        {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Şifre Değiştirme İsteği");
            message.setText("Şifrenizi değiştirmek için linke tıklayabilirsiniz.\nLink : http://localhost:8080/WebApplication1/faces/sifreDegistirmeOnay.xhtml?"+to
                    + "\nŞifre değiştirme isteğini siz yapmadıysanız hemen sistem yöneticiniz ile irtibata geçiniz.");
            
            Transport.send(message);
            return "index.xhtml";
        }
        catch (MessagingException ex) 
        {
            Logger.getLogger(SifremiUnuttum.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();//Said returnu hata sayfasina yonlendirirsin, hata mesaji olarakta gonderebilirsen ex.toString i gonder
        }
    }
}
