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
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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
    private int icraId=0;
    

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
    
    public String detaylariGor(){
                
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();                                
        String no =  params.get("icraId"); 
        icraId = Integer.parseInt(no);
        
        Connection con = DbFunctions.getCon();
        PreparedStatement ps = null;
        ResultSet rs = null;
 
    try {                                    
            ps = con.prepareStatement("SELECT * FROM TBLICRALAR WHERE ID="+icraId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                                
                mahkemeYeri     = rs.getString("MAHKEMEYERI");
                hakimAdSoyad    = rs.getString("HAKIMADSOYAD");
                durusmaTarihi   = DbFunctions.dateToString(rs.getDate("DURUSMATARIHI").toString());                
                hukumTarihi     = DbFunctions.dateToString(rs.getDate("HUKUMTARIHI").toString());
                davaKonusu      = rs.getString("DAVAKONUSU");
                Date kararYilDate       = rs.getDate("KARARYIL");
                String kararYilString   = kararYilDate.toString();
                int kararNoInteger      = rs.getInt("KARARNO");
                kararNo = kararYilString.substring(0, 4)+ "/"+Integer.toString(kararNoInteger) ;
                alacakToplamTutari      = rs.getDouble("ALACAKTOPLAMTUTARI");
                avukatAdSoyad   = rs.getString("AVUKATADSOYAD");
                ipotekKonulanUrunler    = rs.getString("IPOTEKKONULANURUNLER");
                rehinKonulanUrunler     = rs.getString("REHINKONULANURUNLER");
                hacizAlinanUrunler      = rs.getString("HACIZALINANURUNLER");                
                hacizToplamTutari       = rs.getDouble("HACIZTOPLAMTUTARI");
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
    
  
        
        return "icradavadetayi.xhtml?faces-redirect=true";
        
    } 
    
    public void guncelle(){
    
    VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
    vti.sqlKomut = "UPDATE TBLICRALAR SET DAVAKONUSU='"+davaKonusu+"',"+
                                           "MAHKEMEYERI='"+mahkemeYeri+"',"+
                                           "HAKIMADSOYAD='"+hakimAdSoyad+"',"+                                                   
                                           "DURUSMATARIHI="+DbFunctions.stringToDate(durusmaTarihi)+","+
                                           "HUKUMTARIHI="+DbFunctions.stringToDate(hukumTarihi)+","+
                                           "KARARYIL="+DbFunctions.stringToDateKarar(kararNo, 0)+","+
                                           "KARARNO="+DbFunctions.stringToDateKarar(kararNo, 1)+","+
                                           "IPOTEKKONULANURUNLER='"+ipotekKonulanUrunler+"',"+
                                           "REHINKONULANURUNLER='"+rehinKonulanUrunler+"',"+
                                           "HACIZALINANURUNLER='"+hacizAlinanUrunler+"',"+
                                           "ALACAKTOPLAMTUTARI="+alacakToplamTutari+","+
                                           "HACIZTOPLAMTUTARI="+hacizToplamTutari+" WHERE AVUKATADSOYAD='"+avukatAdSoyad+"'";
    vti.uygula();
    }
    
}