/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hukuk.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Toshiba
 */
@ManagedBean(name = "BeanKullaniciGiris")
@SessionScoped
public class KullaniciGiris {

    private String kullaniciAd, sifre;

    public String getKullaniciAd() {
        return kullaniciAd;
    }

    public void setKullaniciAd(String kullaniciAd) {
        this.kullaniciAd = kullaniciAd;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
    
    public String girisKontrol()
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection baglanti = DbFunctions.getCon();
        
        if(baglanti == null)
        {
            
            //(Mehmet)Kullaniciya Veritabanina Baglanti Hatasi mesaji gosterelim. said sayfanin biyerine textbox mi eklersin duruma gore buraya ekleriz.
             FacesContext.getCurrentInstance().addMessage("myForm:newPassword1", new FacesMessage("VERİ TABANINA BAĞLANMADI", "VERİ TABANINA BAĞLANMADI"));
            return "";
        }
        
        String sqlSorgu = "Select * From TBLKULLANICILAR2 Where KULLANICIAD='"+getKullaniciAd()+"'";
        
        try 
        {
            ps = baglanti.prepareStatement(sqlSorgu);
            ps.execute();
            rs = ps.getResultSet();
            
            if(rs.next())
            {
                if(rs.getString("SIFRE").equals(getSifre()))
                {
                    DbFunctions.baglantiKapa(baglanti);
                    return "anasayfa.xhtml";//(Mehmet)Sifre dogru
                }
                else
                {
                    System.out.println("Sifrenizi hatali girdiniz!");
                    DbFunctions.baglantiKapa(baglanti);
                     FacesContext.getCurrentInstance().addMessage("myForm:newPassword1", new FacesMessage("ŞİFRENİZİ HATALI GİRDİNİZ!", "ŞİFRENİZİ HATALI GİRDİNİZ!"));
                    return "";
                    //(Mehmet)Sifre Hatali popup'i verdirebiliriz. Ben farketmemiz icin bu sayfaya yonlendiridim.
                }
            }
            else
            {
                DbFunctions.baglantiKapa(baglanti);
                FacesContext.getCurrentInstance().addMessage("myForm:newPassword1", 
                        new FacesMessage("BÖYLE BİR KULLANICI BULUNAMADI!", "BÖYLE BİR KULLANICI BULUNAMADI!"));
                    return "";
                //(Mehmet)Boyle bir kullanici bulunamadi mesaji verdiricez.
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(KullaniciGiris.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Sorgulama yaparken hata olustu!");
            DbFunctions.baglantiKapa(baglanti);
            FacesContext.getCurrentInstance().addMessage("myForm:newPassword1", new FacesMessage("VERİ TABANI SORGULAMA HATASI", "VERİ TABANI ERİŞİM SORUNU"));
            return "";//(Mehmet)Veritabani sorgulamasinda hata meydana geldi mesaji
        }
    }
}
