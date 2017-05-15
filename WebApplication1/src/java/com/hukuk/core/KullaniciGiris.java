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
    private String hataMesaji;

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

    public String getHataMesaji() {
        return hataMesaji;
    }

    public void setHataMesaji(String hataMesaji) {
        this.hataMesaji = hataMesaji;
    }

    public KullaniciGiris() {
        hataMesaji = "";
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
    
    public String dbOlustur()
    {
        int tabloSayisi = 7;//DB deki Tablo Sayimiz
        String[] dizi = new String[tabloSayisi];
        dizi[0] = "CREATE TABLE TBLKULLANICILAR2(id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
                "ad VARCHAR(50)," +
                "soyad VARCHAR(50)," +
                "dogumTarih DATE," +
                "tcKimlikNo VARCHAR(11) NOT NULL," +
                "kullaniciAd VARCHAR(100) NOT NULL," +
                "sifre VARCHAR(100) NOT NULL," +
                "email VARCHAR(150) NOT NULL," +
                "buroNo INT)";
        
        dizi[1] = "CREATE TABLE TBLMAHKEME_BILGILER("
                + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
                + "davaEsasNo INT NOT NULL,"
                + "mahkemeTipi VARCHAR(50),"
                + "mahkemeYeri VARCHAR(500),"
                + "davaTipi VARCHAR(50),"
                + "davaKonusu VARCHAR(200),"
                + "hakimAd VARCHAR(50),"
                + "hukumTarih DATE,"
                + "davaTarih DATE,"
                + "kararYil DATE,"
                + "kararNo INT,"
                + "mahkemeKarar VARCHAR(100),"
                + "avukatAdSoyad VARCHAR(150),"
                + "davaMasrafId INT)";
        
        dizi[2] = "CREATE TABLE TBLDAVA_BILGILER("
                + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
                + "davaTuru VARCHAR(50),"
                + "ad VARCHAR(100),"
                + "soyad VARCHAR(100),"
                + "tcKimlikNo VARCHAR(11),"
                + "dogumTarih DATE,"
                + "savunma VARCHAR(500),"
                + "idMahkemeBilgiler INT)";
        
        dizi[3] = "CREATE TABLE TBLMASRAFLAR("
                + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
                + "mahkemeTuruToplam DOUBLE,"
                + "harcToplam DOUBLE,"
                + "tarafSayisiToplam DOUBLE,"
                + "sahitSayisiToplam DOUBLE,"
                + "toplamTutar DOUBLE)";
        
        dizi[4] = "CREATE TABLE TBLICRA_BILGILER("
                + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
                + "tip VARCHAR(50),"
                + "ad VARCHAR(100),"
                + "soyad VARCHAR(100),"
                + "tcKimlikNo VARCHAR(11),"
                + "dogumTarih DATE,"
                + "savunma VARCHAR(500),"
                + "idIcralar INT)";
        
        dizi[5] = "CREATE TABLE TBLICRALAR("
                + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
                + "davaKonusu VARCHAR(100),"
                + "mahkemeYeri VARCHAR(500),"
                + "hakimAdSoyad VARCHAR(150),"
                + "avukatAdSoyad VARCHAR(150),"
                + "durusmaTarihi DATE,"
                + "hukumTarihi DATE,"
                + "kararYil DATE,"
                + "kararNo INT,"
                + "ipotekKonulanUrunler VARCHAR(1500),"
                + "rehinKonulanUrunler VARCHAR(1500),"
                + "hacizAlinanUrunler VARCHAR(1500),"
                + "alacakToplamTutari DOUBLE,"
                + "hacizToplamTutari DOUBLE)";
        
        dizi[6] = "CREATE TABLE TBLTAKVIM("
                + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
                + "baslik VARCHAR(100),"
                + "baslangic TIMESTAMP,"
                + "bitis TIMESTAMP)";
        
        String sqlKomut;
        
        Connection baglanti = DbFunctions.getCon();
        PreparedStatement ps = null;
        
        if(baglanti == null)
        {
            return "hataolustu.xhtml";
        }
        
        for(int i=0; i<tabloSayisi; i++)
        {
            sqlKomut = dizi[i];
            try 
            {
                ps = baglanti.prepareStatement(sqlKomut);
                ps.execute();
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(KullaniciGiris.class.getName()).log(Level.SEVERE, null, ex);
                DbFunctions.baglantiKapa(baglanti);
                return "hataolustu.xhtml";
            }
        }
        DbFunctions.baglantiKapa(baglanti);
        hataMesaji = "Database olusturuldu, DB olustur butonuna bir daha basmayiniz, hata alirsiniz!";
        return "";
    }
}