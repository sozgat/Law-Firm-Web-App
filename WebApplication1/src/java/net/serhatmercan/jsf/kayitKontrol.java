
package net.serhatmercan.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

public class kayitKontrol {
       
    private String tcKimlikNo; // TC Kimlik No üzerinde işlem yapmayacağımız için String tanımladım. Değişim yapılabilir.
    private String ad;
    private String soyad;
    private String dogumTarihi;
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
    
    
}
