
package com.hukuk.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "BeanKayitKontrol") // Bean ismi
@SessionScoped

public class KayitKontrol { // Class isimleri buyuk harfle basliyor.
       
    private String tcKimlikNo; // TC Kimlik No üzerinde işlem yapmayacağımız için String tanımladım. Değişim yapılabilir.
    private String ad;
    private String soyad;
    private String dogumTarihi; // Dogum tarihini date felan mı yapsak, veritabanında ben date yapıcam oraya kaydederken sorun yasarmıyız?(Mehmet)
    private String buroAdi;
    private int buroNo;
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

    public int getBuroNo() {
        return buroNo;
    }

    public void setBuroNo(int buroNo) {
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
        PreparedStatement ps = null;
        Connection baglanti = DbFunctions.getCon();
        
        if(baglanti == null)
        {
            //(Mehmet)Kullaniciya Veritabanina Baglanti Hatasi mesaji gosterelim. said sayfanin biyerine textbox mi eklersin duruma gore buraya ekleriz.
            return "kayitol.xhtml";
        }
        String eklemeKodu = "INSERT INTO TBLKULLANICILAR2"//---!---(Mehmet)DB de duzeltme yaptıktan sonra isimdeki 2 yi silicez.
                + "(ad,soyad,dogumTarih,tcKimlikNo,kullaniciAd,sifre,email,buroNo) "
                + "VALUES('"+ getAd() +"','"+ getSoyad() +"',"+ DbFunctions.stringToDate(getDogumTarihi())
                + ",'"+ getTcKimlikNo() +"','"+ getKullaniciAdi() +"','"+ getSifre() +"','"+ getEmail() +"',"+ getBuroNo() +")";
        
        try {
          /*Hocanin yaptigi gibi yapmaya calistim fakat sql komutu calismadi, duzeltebilirsek
            yorum satirina aldigim bu satirlari tekrar kullaniriz.(Mehmet)
            ps.setString(1, getAd());
            ps.setString(2, getSoyad());
            ps.setString(3, DbFunctions.stringToDate(getDogumTarihi()));
            ps.setString(4, "d");
            ps.setString(5, "s");
            ps.setString(6, "s");
            ps.setString(7, "d");
            ps.setInt(8, getBuroNo());
            */
            ps = baglanti.prepareStatement(eklemeKodu);
            ps.execute();
            System.out.println("Kayit eklendi.");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(KayitKontrol.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Kayit sirasinda hata olustu!");//(Mehmet)HTML kismindada Hata mesaji verebilriiz.
            return "kayitol.xhtml";
        }
        finally
        {
            DbFunctions.baglantiKapa(baglanti);
           return "yonlendirme.xhtml";
        }
        
    }
    
    
    
    
}
