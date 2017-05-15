
package com.hukuk.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "BeanKayitKontrol")
@RequestScoped

public class KayitKontrol {
    private String hataMesaji;

    public String getHataMesaji() {
        return hataMesaji;
    }

    public void setHataMesaji(String hataMesaji) {
        this.hataMesaji = hataMesaji;
    }
    private String tcKimlikNo;
    private String ad;
    private String soyad;
    private String dogumTarihi;
    private String buroAdi;
    private String buroNo;
    private String kullaniciAdi;
    private String sifre;
    private String email;

    
    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(String dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getBuroAdi() {
        return buroAdi;
    }

    public void setBuroAdi(String buroAdi) {
        this.buroAdi = buroAdi;
    }

    public String getBuroNo() {
        return buroNo;
    }

    public void setBuroNo(String buroNo) {
        this.buroNo = buroNo;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(String tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }
    
    public String kaydet()
    {
        int buroSicilNo = Integer.parseInt(buroNo);
        
        PreparedStatement ps = null;
        Connection baglanti = DbFunctions.getCon();
        
        if(baglanti == null)
        {
            hataMesaji="VERİ TABANINA BAĞLANAMADI!";
            return "kayitol.xhtml";
        }
        String eklemeKodu = "INSERT INTO TBLKULLANICILAR2"
                + "(ad,soyad,dogumTarih,tcKimlikNo,kullaniciAd,sifre,email,buroNo) "
                + "VALUES('"+ getAd() +"','"+ getSoyad() +"',"+ DbFunctions.stringToDate(getDogumTarihi())
                + ",'"+ getTcKimlikNo() +"','"+ getKullaniciAdi() +"','"+ getSifre() +"','"+ getEmail() +"',"+ buroSicilNo +")";
        
        try 
        {
            ps = baglanti.prepareStatement(eklemeKodu);
            ps.execute();
            System.out.println("Kayit eklendi.");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(KayitKontrol.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Kayit sirasinda hata olustu!");
            return "hataolustu.xhtml";
        }
        finally
        {
            DbFunctions.baglantiKapa(baglanti);
           return "yonlendirme.xhtml";
        }  
    }
}
