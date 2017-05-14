package net.serhatmercan.jsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private String avukatAdSoyad;

    
    public String getAvukatAdSoyad() {
        return avukatAdSoyad;
    }
    
    public void setAvukatAdSoyad(String avukatAdSoyad) {
        this.avukatAdSoyad = avukatAdSoyad;
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
    
    public String mahkemeBilgileriKaydet()
    {        
        PreparedStatement ps = null;
        Connection baglanti = DbFunctions.getCon();
        
        if(baglanti == null)
        {
   
            return "anasayfa.xhtml";//Burayi ayri bir hata sayfasina dondurelim yada hata mesaji gosterip ayni sayfada tutalim
        }
        
        //Once Masraflar Tablosundan Son Eklenen Verinin id sini cekicez.
        ResultSet rs = null;
        int davaMasrafId;
        String sqlSorgu = "Select * From TBLMASRAFLAR ORDER BY id DESC FETCH FIRST 1 ROWS ONLY";
        
        try 
        {
            ps = baglanti.prepareStatement(sqlSorgu);
            ps.execute();
            rs = ps.getResultSet();
            
            if(rs.next())
                davaMasrafId = rs.getInt("id");//mahkemebilgileri tablosuna kayit eklerken davamasrafid degerini burdan alicaz.
            else
                return "hataolustu.xhtml";
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            return "hataolustu.xhtml";
        }
        
        //Simdi TBLMAHKEME_BILGILER Tablosuna Veriyi Ekliyoruz.
        
        String sqlKomut = "INSERT INTO TBLMAHKEME_BILGILER(davaEsasNo, mahkemeTipi, mahkemeYeri, davaTipi, davaKonusu, "
                + "hakimAd, hukumTarih, davaTarih, kararYil, kararNo,mahkemeKarar, avukatAdSoyad, davaMasrafId) "
                + "VALUES("+davaEsasNo+",'"+mahkemeTipi+"','"+mahkemeYeri+"', '"+davaTipi+"','"+davaKonusu+"','"+hakimAd+"',"+DbFunctions.stringToDate(hukumTarihi)+","+DbFunctions.stringToDate(davaTarihi)+","+DbFunctions.stringToDateKarar(kararNo, 0)+","+DbFunctions.stringToDateKarar(kararNo, 1)+",'"
                +mahkemeKarari+"','"+avukatAdSoyad+"',"+davaMasrafId+")";
        
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
            DbFunctions.baglantiKapa(baglanti);
            return "hataolustu.xhtml";
        }
        
        DbFunctions.baglantiKapa(baglanti);
        return "davabilgilerikayit.xhtml";
    }
    
    
}
