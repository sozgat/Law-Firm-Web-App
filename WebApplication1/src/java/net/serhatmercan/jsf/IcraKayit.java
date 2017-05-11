/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.serhatmercan.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Toshiba
 */

@ManagedBean
@SessionScoped

public class IcraKayit 
{
    private String mahkemeYeri;
    private String hakimAdSoyad;
    private String durusmaTarihi;//Gecmiste yapilmis olan durusmanin tarihi
    private String hukumTarihi;
    private String davaKonusu;
    private String kararNo;//kararYil ve kararNo olarak db de 2ye ayirdik.
    private double alacakToplamTutari;
    private String avukatAdSoyad;
    private String ipotekKonulanUrunler;
    private String rehinKonulanUrunler;
    private String hacizAlinanUrunler;
    private double hacizToplamTutari;

    public String getMahkemeYeri() {
        return mahkemeYeri;
    }

    public void setMahkemeYeri(String mahkemeYeri) {
        this.mahkemeYeri = mahkemeYeri;
    }

    public String getHakimAdSoyad() {
        return hakimAdSoyad;
    }

    public void setHakimAdSoyad(String hakimAdSoyad) {
        this.hakimAdSoyad = hakimAdSoyad;
    }

    public String getDurusmaTarihi() {
        return durusmaTarihi;
    }

    public void setDurusmaTarihi(String durusmaTarihi) {
        this.durusmaTarihi = durusmaTarihi;
    }

    public String getHukumTarihi() {
        return hukumTarihi;
    }

    public void setHukumTarihi(String hukumTarihi) {
        this.hukumTarihi = hukumTarihi;
    }

    public String getDavaKonusu() {
        return davaKonusu;
    }

    public void setDavaKonusu(String davaKonusu) {
        this.davaKonusu = davaKonusu;
    }

    public String getKararNo() {
        return kararNo;
    }

    public void setKararNo(String kararNo) {
        this.kararNo = kararNo;
    }

    public double getAlacakToplamTutari() {
        return alacakToplamTutari;
    }

    public void setAlacakToplamTutari(double alacakToplamTutari) {
        this.alacakToplamTutari = alacakToplamTutari;
    }

    public String getAvukatAdSoyad() {
        return avukatAdSoyad;
    }

    public void setAvukatAdSoyad(String avukatAdSoyad) {
        this.avukatAdSoyad = avukatAdSoyad;
    }

    public String getIpotekKonulanUrunler() {
        return ipotekKonulanUrunler;
    }

    public void setIpotekKonulanUrunler(String ipotekKonulanUrunler) {
        this.ipotekKonulanUrunler = ipotekKonulanUrunler;
    }

    public String getRehinKonulanUrunler() {
        return rehinKonulanUrunler;
    }

    public void setRehinKonulanUrunler(String rehinKonulanUrunler) {
        this.rehinKonulanUrunler = rehinKonulanUrunler;
    }

    public String getHacizAlinanUrunler() {
        return hacizAlinanUrunler;
    }

    public void setHacizAlinanUrunler(String hacizAlinanUrunler) {
        this.hacizAlinanUrunler = hacizAlinanUrunler;
    }

    public double getHacizToplamTutari() {
        return hacizToplamTutari;
    }

    public void setHacizToplamTutari(double hacizToplamTutari) {
        this.hacizToplamTutari = hacizToplamTutari;
    }
    
    //-----------------------------Getter Setter Sonu-----------------------------
    
    public String kaydet()
    {
        VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
        vti.sqlKomut ="INSERT INTO TBLICRALAR(DAVAKONUSU, MAHKEMEYERI, HAKIMADSOYAD, AVUKATADSOYAD, DURUSMATARIHI, HUKUMTARIHI, "
                     +"KARARYIL, KARARNO, IPOTEKKONULANURUNLER, REHINKONULANURUNLER, HACIZALINANURUNLER, ALACAKTOPLAMTUTARI, HACIZTOPLAMTUTARI) "
                     +"VALUES('"+davaKonusu+"','"+mahkemeYeri+"','"+hakimAdSoyad+"','"+avukatAdSoyad+"',"+DbFunctions.stringToDate(durusmaTarihi)+","
                               +DbFunctions.stringToDate(hukumTarihi)+","+DbFunctions.stringToDateKarar(kararNo,0)+","+DbFunctions.stringToDateKarar(kararNo,1)+",'"
                               +ipotekKonulanUrunler+"','"+rehinKonulanUrunler+"','"+hacizAlinanUrunler+"',"+alacakToplamTutari+","
                               +hacizToplamTutari+")";
        //Sadece SQL Kısmı doğru yazılacak 
        vti.ekle();
        return "icrabilgilerkayit.xhtml";//Deneme
    }
}