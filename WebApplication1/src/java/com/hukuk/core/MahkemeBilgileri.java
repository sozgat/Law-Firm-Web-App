package com.hukuk.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped

public class MahkemeBilgileri {
    
    public String mahkemeTipi;
    public String mahkemeYeri;
    public String davaTipi;
    public String davaKonusu;
    public int davaEsasNo;
    public String hakimAd;
    public String hukumTarihi;
    public String davaTarihi;
    public String kararNo;
    public String mahkemeKarari;
    public String avukatAdSoyad;
    public double davaTutar;
    private int davaEsasNoYeni;

    public double getDavaTutar() {
        return davaTutar;
    }

    public void setDavaTutar(double davaTutar) {
        this.davaTutar = davaTutar;
    }
    
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
    public String degis(){
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();                                
        String no =  params.get("davaEsasNo"); 
        davaEsasNo = Integer.parseInt(no);
        davaEsasNoYeni = davaEsasNo;
        
        Connection con = DbFunctions.getCon();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        
        
    try {                                    
            ps = con.prepareStatement("SELECT TOPLAMTUTAR,MAHKEMETIPI,HAKIMAD,MAHKEMEYERI,HUKUMTARIH,DAVATIPI,DAVATARIH,DAVAKONUSU,DAVAESASNO,MAHKEMEKARAR,AVUKATADSOYAD,KARARYIL,KARARNO FROM TBLMAHKEME_BILGILER,TBLMASRAFLAR WHERE DAVAESASNO="+davaEsasNo+" AND DAVAMASRAFID=TBLMASRAFLAR.ID");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                mahkemeTipi = rs.getString("MAHKEMETIPI");
                hakimAd =     rs.getString("HAKIMAD"); 
                mahkemeYeri = rs.getString("MAHKEMEYERI");
                hukumTarihi = DbFunctions.dateToString(rs.getDate("HUKUMTARIH").toString());
                davaTipi =    rs.getString("DAVATIPI");
                davaTarihi =  DbFunctions.dateToString(rs.getDate("DAVATARIH").toString());
                davaKonusu =  rs.getString("DAVAKONUSU");
                davaEsasNo =  rs.getInt("DAVAESASNO");
                mahkemeKarari = rs.getString("MAHKEMEKARAR");
                avukatAdSoyad = rs.getString("AVUKATADSOYAD");
                
                Date kararYilDate = rs.getDate("KARARYIL");
                String kararYilString = kararYilDate.toString();
                int kararNoInteger = rs.getInt("KARARNO");
                
                davaTutar = rs.getDouble("TOPLAMTUTAR");
                
                kararNo = kararYilString.substring(0, 4)+ "/"+Integer.toString(kararNoInteger) ;
                
                           
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DavalariGor.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();                
            } catch (SQLException ex) {
                Logger.getLogger(DavalariGor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
             
    return "davadetaylarigor.xhtml?faces-redirect=true";
    }
    
    public void guncelle(){
    
    VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
    vti.sqlKomut = "UPDATE TBLMAHKEME_BILGILER SET DAVAESASNO="+davaEsasNo+","+
                                                   "MAHKEMETIPI='"+mahkemeTipi+"',"+
                                                   "MAHKEMEYERI='"+mahkemeYeri+"',"+
                                                   "DAVATIPI='"+davaTipi+"',"+
                                                   "DAVAKONUSU='"+davaKonusu+"',"+
                                                   "HAKIMAD='"+hakimAd+"',"+
                                                   "HUKUMTARIH="+DbFunctions.stringToDate(hukumTarihi)+","+
                                                   "DAVATARIH="+DbFunctions.stringToDate(davaTarihi)+","+
                                                   "KARARYIL="+DbFunctions.stringToDateKarar(kararNo, 0)+","+
                                                   "KARARNO="+DbFunctions.stringToDateKarar(kararNo, 1)+","+
                                                   "MAHKEMEKARAR='"+mahkemeKarari+"',"+
                                                   "AVUKATADSOYAD='"+avukatAdSoyad+"' WHERE DAVAESASNO="+davaEsasNoYeni;                                                               
    vti.uygula();
             
    }
    
    
}
