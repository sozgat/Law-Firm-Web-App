/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.serhatmercan.jsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Toshiba
 */
@ManagedBean(name="BeanSifreDegistirmeOnay")
@RequestScoped
public class SifreDegistirmeOnay 
{
    private String mail;
    private int sifre;
    private String message;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public SifreDegistirmeOnay() 
    {
        HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        mail=origRequest.getQueryString();
        setMessage(mail);
        
        Connection baglanti = DbFunctions.getCon();
        PreparedStatement ps = null;
        
        if(baglanti == null)
        {
            //(Mehmet)Kullaniciya Veritabanina Baglanti Hatasi mesaji gosterelim. said sayfanin biyerine textbox mi eklersin duruma gore buraya ekleriz.
        }
        
        Random rand = new Random();
        sifre = rand.nextInt(9999+1);
        
        String updateKomutu = "UPDATE TBLKULLANICILAR2 SET SIFRE='"+"Aa"+sifre+"' WHERE EMAIL='"+mail+"'";
        
        try 
        {
            ps = baglanti.prepareStatement(updateKomutu);
            ps.executeUpdate();
            setMessage("Şifreniz sıfırlandı. Yeni şifreniz: Aa"+sifre);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(SifreDegistirmeOnay.class.getName()).log(Level.SEVERE, null, ex);
            setMessage("Veritabaninda guncelleme yapilirken hata olustu!");
        }
        finally
        {
            DbFunctions.baglantiKapa(baglanti);
        }
    }
    
    public String giriseDon()
    {
        return "index.xhtml";
    }
}
