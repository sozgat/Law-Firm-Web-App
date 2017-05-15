package com.hukuk.core;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class HesapAyarlari implements Serializable 
{
    private String hataMesaji;

    public String getHataMesaji() {
        return hataMesaji;
    }

    public void setHataMesaji(String hataMesaji) {
        this.hataMesaji = hataMesaji;
    }
    private String kullaniciAd;//Simdilik burada default tanimliyorum, daha sonra kullanici adini sistemden cekicez.
    private String eskiSifre;
    private String sifre1;
    private String sifre2;

    public String getKullaniciAd() {
        return kullaniciAd;
    }

    public void setKullaniciAd(String kullaniciAd) {
        this.kullaniciAd = kullaniciAd;
    }
    
    public HesapAyarlari() {
    }

    public String getEskiSifre() {
        return eskiSifre;
    }

    public void setEskiSifre(String eskiSifre) {
        this.eskiSifre = eskiSifre;
    }

    public String getSifre1() {
        return sifre1;
    }

    public void setSifre1(String sifre1) {
        this.sifre1 = sifre1;
    }

    public String getSifre2() {
        return sifre2;
    }

    public void setSifre2(String sifre2) {
        this.sifre2 = sifre2;
    }

    
    public String degistir()
    {
        if(!sifre1.equals(sifre2))
            {
                hataMesaji = "YENİ ŞİFRELER UYUŞMUYOR!";
                return "";
            }
        Connection baglanti = DbFunctions.getCon();
        PreparedStatement ps = null;
        
        if(baglanti == null)
        {
             hataMesaji = "VERİ TABANI BAĞLANTI HATASI!";
            //(Mehmet)Kullaniciya Veritabanina Baglanti Hatasi mesaji gosterelim. said sayfanin biyerine textbox mi eklersin duruma gore buraya ekleriz.
            return "hataolustu.xhtml";
        }

        String updateKomutu = "UPDATE TBLKULLANICILAR2 SET SIFRE='"+sifre1+"' WHERE KULLANICIAD='"+kullaniciAd+"'";

        try 
        {
            ps = baglanti.prepareStatement(updateKomutu);
            ps.executeUpdate();
        }
        catch (SQLException ex) 
        {
            //Hata mesaji sende Said. ("Veritabaninda guncelleme yapilirken hata olustu!");
            return "";
        }
        finally
        {
            DbFunctions.baglantiKapa(baglanti);
        }
        return "index.xhtml";
    }
}
