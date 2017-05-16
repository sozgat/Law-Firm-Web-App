
package com.hukuk.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "BeanDavaKayit")
@SessionScoped

public class DavaKayit{

    private int mahkemeBilgilerId;//DB de primary keyimiz.
    private String avukatAdSoyad;
    private String mahkemeTipi;
    private String mahkemeYeri;
    private String davaTipi;
    private String davaKonusu;
    private int davaEsasNo;
    private String hakimAd;
    private String hukumTarihi;
    private String davaTarihi;
    private String kararNo;//Orn:2017/999 gibi alicaz ikiye bolup veritabaninda kararYil ve kararNo sutunlarina ekleriz.
    private String mahkemeKarari;
    private double davaMasrafi;//DavaMasraf adinda ayri bi class olusturup hesabi orda yapariz.
    private int davaMasrafId;


    public int getMahkemeBilgilerId() {
        return mahkemeBilgilerId;
    }

    public void setMahkemeBilgilerId(int mahkemeBilgilerId) {
        this.mahkemeBilgilerId = mahkemeBilgilerId;
    }
    
    public String getMahkemeTipi() {
        return mahkemeTipi;
    }

    public void setMahkemeTipi(String mahkemeTipi) {
        this.mahkemeTipi = mahkemeTipi;
    }

    public String getMahkemeYeri() {
        return mahkemeYeri;
    }

    public void setMahkemeYeri(String mahkemeYeri) {
        this.mahkemeYeri = mahkemeYeri;
    }

    public String getDavaTipi() {
        return davaTipi;
    }

    public void setDavaTipi(String davaTipi) {
        this.davaTipi = davaTipi;
    }

    public String getDavaKonusu() {
        return davaKonusu;
    }

    public void setDavaKonusu(String davaKonusu) {
        this.davaKonusu = davaKonusu;
    }

    public int getDavaEsasNo() {
        return davaEsasNo;
    }

    public void setDavaEsasNo(int davaEsasNo) {
        this.davaEsasNo = davaEsasNo;
    }

    public String getHakimAd() {
        return hakimAd;
    }

    public void setHakimAd(String hakimAd) {
        this.hakimAd = hakimAd;
    }

    public String getHukumTarihi() {
        return hukumTarihi;
    }

    public void setHukumTarihi(String hukumTarihi) {
        this.hukumTarihi = hukumTarihi;
    }

    public String getDavaTarihi() {
        return davaTarihi;
    }

    public void setDavaTarihi(String davaTarihi) {
        this.davaTarihi = davaTarihi;
    }

    public String getKararNo() {
        return kararNo;
    }

    public void setKararNo(String kararNo) {
        this.kararNo = kararNo;
    }

    public String getMahkemeKarari() {
        return mahkemeKarari;
    }

    public void setMahkemeKarari(String mahkemeKarari) {
        this.mahkemeKarari = mahkemeKarari;
    }

    public double getDavaMasrafi() {
        return davaMasrafi;
    }

    public void setDavaMasrafi(double davaMasrafi) {
        this.davaMasrafi = davaMasrafi;
    }

    public int getDavaMasrafId() {
        return davaMasrafId;
    }

    public void setDavaMasrafId(int davaMasrafId) {
        this.davaMasrafId = davaMasrafId;
    }
    
    
    public String kaydet()
    {
        PreparedStatement ps = null;
        Connection baglanti = DbFunctions.getCon();
        
        if(baglanti == null)
        {
            
            return "davakayit.xhtml";//Burayi ayri bir hata sayfasina dondurelim yada hata mesaji gosterip ayni sayfada tutalim
        }
        
        String sqlKomut = "INSERT INTO TBLMAHKEME_BILGILER(davaEsasNo, mahkemeTipi, mahkemeYeri, davaTipi, davaKonusu, hakimAd, hukumTarih, davaTarih, kararYil, kararNo"
                + ", mahkemeKarar, avukatAdSoyad, davaMasrafId) VALUES("+davaEsasNo+",'"+mahkemeTipi+"','"+mahkemeYeri+"', '"+davaTipi+"','"+davaKonusu+"','"+hakimAd+"',"+DbFunctions.stringToDate("10/11/2000")+","+DbFunctions.stringToDate("09/08/2001")+","+DbFunctions.stringToDateKarar("2005/555",0)+","+DbFunctions.stringToDateKarar("2005/555",1)
                + ", '"+mahkemeKarari+"','"+avukatAdSoyad+"',"+davaMasrafId+")";
        
        try
        {
            ps=baglanti.prepareStatement(sqlKomut);
            ps.execute();
            System.out.println("MahkemeBilgiler Tablosuna Kayit Eklendi.");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("MahkemeBilgiler tablosuna kayit eklenirken hata olustu!");
            return "hataolustu.xhtml";
        }
        //Simdi mahkemebilgiler tablosundaki son eklenen verinin id sini cekicez.
        ResultSet rs = null;
        
        String sqlSorgu = "Select * From TBLMAHKEME_BILGILER ORDER BY id DESC";
        
        try 
        {
            ps = baglanti.prepareStatement(sqlSorgu);
            ps.execute();
            rs = ps.getResultSet();
            
            if(rs.next())
                mahkemeBilgilerId = rs.getInt("id");//davaBilgiler tablosuna kayit eklerken mahkemeBilgilerId degerini burdan alicaz.
            else
                return "kayitKontrolJsf.xhtml";//hata mesaji verdirmeliyiz(mahkemeBilgiler tablosundan veri cekilirken hata olustu)
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            return "kayitKontrolJsf.xhtml";//hata mesaji verdirmeliyiz(mahkemeBilgiler tablosundan veri cekilirken hata olustu)
        }
        
        
        return "";
    }

}
