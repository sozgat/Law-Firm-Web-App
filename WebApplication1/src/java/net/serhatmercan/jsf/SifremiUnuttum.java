/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.serhatmercan.jsf;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.mail.*;
//import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Toshiba
 */
@ManagedBean(name = "BeanSifremiUnuttum")
@RequestScoped
public class SifremiUnuttum {
    
    final String user = "hukukwebproje@gmail.com";
    final String pass = "hukukweb1";
    
    private String to;
    
    public SifremiUnuttum() {
    }

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
        
        Session session = Session.getInstance(props, new Authenticator() 
        {
            protected PasswordAuthentication gePasswordAuthentication()
            {
                return new PasswordAuthentication(user, pass);
            }
        });
        
        
        try 
        {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Sifre Degistirme Istegi");
            message.setText("Sifrenizi degistirmek icin linke tiklayabilirsiniz.\nSifre degistirme istegini siz yapmadiysaniz hemen sistem yoneticiniz ile irtibata geciniz.");
            
            Transport.send(message);
            return "#openModal";
            
            //Ustteki kod calismazsa alttaki yontemi deneyecez.
            //MimeBodyPart messageBodyPart = new MimeBodyPart();
            //messageBodyPart.setText("Sifrenizi degistirmek icin linke tiklayabilirsiniz.\nSifre degistirme istegini siz yapmadiysaniz hemen sistem yoneticiniz ile irtibata geciniz.");
            
            //Multipart multipart = new MimeMultipart();
            //multipart.addBodyPart(messageBodyPart);
        }
        catch (MessagingException ex) 
        {
            Logger.getLogger(SifremiUnuttum.class.getName()).log(Level.SEVERE, null, ex);
            return "index.xhtml";//Mesaj gonderilemedi modal sayfasi vermeliyiz!
        }
    }
}
