package net.serhatmercan.jsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

public class MahkemeBilgileri {
    
    private String mahkemeTipi;
    private String mahkemeYeri;
    private String davaTipi;
    private String davaKonusu;
    private int davaEsasNo;
    private String hakimAd;
    private String hukumTarihi;
    private String davaTarihi;
    private String kararNo;
    private String mahkemeKarari;
    
 /*   public MahkemeBilgileri(){
        mahkemeTipi="";
        mahkemeYeri="";
        davaTipi="";
        davaKonusu="";
        davaEsasNo=0;
        hakimAd="";
        /*hukumTarihi="00/00/0000";
        davaTarihi="00/00/0000";
        kararNo="0000/0";
        mahkemeKarari="";
    }
*/
    
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
    
    public String mahkemeBilgileriKaydet()
    {        
        PreparedStatement ps = null;
        Connection baglanti = DbFunctions.getCon();
        
        if(baglanti == null)
        {
   
            return "anasayfa.xhtml";//Burayi ayri bir hata sayfasina dondurelim yada hata mesaji gosterip ayni sayfada tutalim
        }
        
        String sqlKomut = "INSERT INTO TBLMAHKEME_BILGILER(davaEsasNo, mahkemeTipi, mahkemeYeri, davaTipi, davaKonusu, "
                + "hakimAd,"
                + "mahkemeKarar, avukatAdSoyad, davaMasrafId) "
                + "VALUES("+davaEsasNo+",'"+mahkemeTipi+"','"+mahkemeYeri+"', '"+davaTipi+"','"+davaKonusu+"','"+hakimAd+"','"
                +mahkemeKarari+"','"+"SS MM"+"',"+1+")";
        /*
         hukumTarih, davaTarih, kararYil, kararNo"
        +DbFunctions.stringToDate("19/12/2007")+","+DbFunctions.stringToDate("19/12/2007")+","
         +DbFunctions.stringToDateKarar("2007/123",0)+","+DbFunctions.stringToDateKarar("2007/123",1)
        */
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
        
        return "";
    }
    
    
}
