package com.hukuk.core;
public class DavaIslemleri {

    public String davaTuru;
    public String ad;
    public String soyad;
    public String tcKimlikNo;
    public String dogumTarihi;
    public String savunmasi;
    
    public DavaIslemleri(){
        
    }
    
    public DavaIslemleri(String davaTuru, String ad, String soyad, String tcKimlikNo, String dogumTarihi, String savunmasi) {
        this.davaTuru = davaTuru;
        this.ad = ad;
        this.soyad = soyad;
        this.tcKimlikNo = tcKimlikNo;
        this.dogumTarihi = dogumTarihi;
        this.savunmasi = savunmasi;
    }

    public String getDavaTuru() {
        return davaTuru;
    }

    public void setDavaTuru(String davaTuru) {
        this.davaTuru = davaTuru;
    }

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

    public String getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(String tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    public String getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(String dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getSavunmasi() {
        return savunmasi;
    }

    public void setSavunmasi(String savunmasi) {
        this.savunmasi = savunmasi;
    }

}