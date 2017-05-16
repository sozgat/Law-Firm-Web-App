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
    private String kararNo;
    private double alacakToplamTutari;
    private String avukatAdSoyad;
    private String ipotekKonulanUrunler;
    private String rehinKonulanUrunler;
    private String hacizAlinanUrunler;
    private double hacizToplamTutari;
    
    public int icraId;

    public int getIcraId() {
        return icraId;
    }

    public void setIcraId(int icraId) {
        this.icraId = icraId;
    }

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
    
    
    public String kaydet()
    {
        VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
        vti.sqlKomut ="INSERT INTO TBLICRALAR(DAVAKONUSU, MAHKEMEYERI, HAKIMADSOYAD, AVUKATADSOYAD, DURUSMATARIHI, HUKUMTARIHI, "
                     +"KARARYIL, KARARNO, IPOTEKKONULANURUNLER, REHINKONULANURUNLER, HACIZALINANURUNLER, ALACAKTOPLAMTUTARI, HACIZTOPLAMTUTARI) "
                     +"VALUES('"+davaKonusu+"','"+mahkemeYeri+"','"+hakimAdSoyad+"','"+avukatAdSoyad+"',"+DbFunctions.stringToDate(durusmaTarihi)+","
                               +DbFunctions.stringToDate(hukumTarihi)+","+DbFunctions.stringToDateKarar(kararNo,0)+","+DbFunctions.stringToDateKarar(kararNo,1)+",'"
                               +ipotekKonulanUrunler+"','"+rehinKonulanUrunler+"','"+hacizAlinanUrunler+"',"+alacakToplamTutari+","
                               +hacizToplamTutari+")";        
        vti.sayfa="icrabilgilerkayit.xhtml";
        
        String donucekUrl =  vti.uygula();
        
        String sqlKomut = "SELECT ID FROM TBLICRALAR ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY";
        ResultSet rs=null;
        PreparedStatement ps=null;
        Connection baglanti = DbFunctions.getCon();
        if(baglanti == null)
            return "hataolustu.xhtml";
        
        try
        {
            ps=baglanti.prepareStatement(sqlKomut);
            ps.execute();
            rs = ps.getResultSet();
            
            if(rs.next())
            {
                icraId = rs.getInt("ID");//Son eklenen icranin id sini aldik
            }
            else
                return "hataolustu.xhtml";// veri bulunamadi!
        }
        catch (SQLException ex)
        {
            Logger.getLogger(IcraKayit.class.getName()).log(Level.SEVERE, null, ex);
            return "hataolustu.xhtml";
        }
        
        DbFunctions.baglantiKapa(baglanti);
        return donucekUrl;
    }
}