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
@ManagedBean(name = "BeanDavaKayit")
@SessionScoped

public class DavaKayit{

    //Harc Degiskenleri
    /*private double davaDegeri;
    private int mahkemeHarciId;//0-Sulh, 1-Asliye, 2-Ticaret, 3-İş
    private int maktuHarc=1;//0 veya 1
    private int nispiHarc=1;//0 veya 1
    private int tarafSayisi;//0-10
    private int sahitSayisi;//0-10
    private double toplamTutar;*/
    //Mahkeme Bilgileri
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
    //Dava Bilgileri
    /*private String davaTuru;
    private String ad;
    private String soyad;
    private String tcKimlikNo;
    private String dogumTarihi;
    private String savunmasi;

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
    }*/
    
    /*-----------------------------------------------------------------*/

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
    
    /*-------------------------------------------------------------------*/

    /*public double getDavaDegeri() {
        return davaDegeri;
    }

    public void setDavaDegeri(double davaDegeri) {
        this.davaDegeri = davaDegeri;
    }

    public int getMahkemeHarciId() {
        return mahkemeHarciId;
    }

    public void setMahkemeHarciId(int mahkemeHarciId) {
        this.mahkemeHarciId = mahkemeHarciId;
    }

    public int getMaktuHarc() {
        return maktuHarc;
    }

    public void setMaktuHarc(int maktuHarc) {
        this.maktuHarc = maktuHarc;
    }

    public int getNispiHarc() {
        return nispiHarc;
    }

    public void setNispiHarc(int nispiHarc) {
        this.nispiHarc = nispiHarc;
    }

    public int getTarafSayisi() {
        return tarafSayisi;
    }

    public void setTarafSayisi(int tarafSayisi) {
        this.tarafSayisi = tarafSayisi;
    }

    public int getSahitSayisi() {
        return sahitSayisi;
    }

    public void setSahitSayisi(int sahitSayisi) {
        this.sahitSayisi = sahitSayisi;
    }

    public double getToplamTutar() {
        return toplamTutar;
    }

    public void setToplamTutar(double toplamTutar) {
        this.toplamTutar = toplamTutar;
    }
    
    public String harcHesapla()
    {
        //db harc tablosuna eklenecek degiskenler
        double mahkemeHarci=0.0, tarafSayisiTutar=0.0, sahitSayisiTutar=0.0;
        
        toplamTutar=0.0;
        
        if(mahkemeHarciId == 0)
            toplamTutar += Masraflar.sulhMahkemesi;
        else if(mahkemeHarciId == 1)
            toplamTutar += Masraflar.asliyeHukukMahkemesi;
        else if(mahkemeHarciId == 2)
            toplamTutar += Masraflar.ticaretMahkemesi;
        else if(mahkemeHarciId == 3)
            toplamTutar += Masraflar.isMahkemesi;
        
        mahkemeHarci = toplamTutar;
        
        toplamTutar += davaDegeri;//(Mehmet)Dava degeri toplam tutara eklenecekmi Said? eklenmiyecekse bu satiri silersin.
        
        toplamTutar += (maktuHarc*Masraflar.maktuHarc + nispiHarc*(Masraflar.nispiHarc*davaDegeri));
        
        tarafSayisiTutar = (tarafSayisi*Masraflar.tarafUcreti);
        toplamTutar += tarafSayisiTutar;
        sahitSayisiTutar = (sahitSayisi*Masraflar.sahitUcreti);
        toplamTutar += sahitSayisiTutar;
        
        toplamTutar += Masraflar.avukatUcreti;
        toplamTutar += Masraflar.digerMasraflar;
        
        PreparedStatement ps = null;
        Connection baglanti = DbFunctions.getCon();
        
        if(baglanti == null)
        {
            //(Mehmet)Kullaniciya Veritabanina Baglanti Hatasi mesaji gosterelim. said sayfanin biyerine textbox mi eklersin duruma gore buraya ekleriz.
            return "davakayit.xhtml";//Burayi ayri bir hata sayfasina dondurelim yada hata mesaji gosterip ayni sayfada tutalim
        }
        
        String sqlKomut = "INSERT INTO TBLMASRAFLAR(mahkemeHarci, davaDegeri, maktuHarc, nispiHarc, tarafSayisiTutar, sahitSayisiTutar, avukatUcreti, digerMasraflar)" 
                + "VALUES("+mahkemeHarci+","+davaDegeri+","+maktuHarc*Masraflar.maktuHarc+", "+nispiHarc*Masraflar.nispiHarc+","+tarafSayisiTutar+","+sahitSayisiTutar+","+Masraflar.avukatUcreti+","+Masraflar.digerMasraflar+")";
        
        try 
        {
            ps=baglanti.prepareStatement(sqlKomut);
            ps.execute();
            System.out.println("TBLMASRAFLAR tablosuna veri eklendi.");
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            return "davakayit.xhtml";//hata mesaji verdirmeliyiz.
        }
        
        ResultSet rs = null;
        
        String sqlSorgu = "Select * From TBLMASRAFLAR ORDER BY id DESC";
        
        try 
        {
            ps = baglanti.prepareStatement(sqlSorgu);
            ps.execute();
            rs = ps.getResultSet();
            
            if(rs.next())
                davaMasrafId = rs.getInt("id");//mahkemebilgileri tablosuna kayit eklerken davamasrafid degerini burdan alicaz.
            else
                return "kayitKontrolJsf.xhtml";//hata mesaji verdirmeliyiz(masraflar tablosundan veri cekilirken hata olustu)
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            return "kayitKontrolJsf.xhtml";//hata mesaji verdirmeliyiz(masraflar tablosundan veri cekilirken hata olustu)
        }
        
        DbFunctions.baglantiKapa(baglanti);
        return "";//Said buraya ne eklememiz gerekiyo? hesapla butonuna basinca ekran kapanmasin sadece tutar yazsin istiyoruz.
    }*/
    
    public String kaydet()
    {
        PreparedStatement ps = null;
        Connection baglanti = DbFunctions.getCon();
        
        if(baglanti == null)
        {
            //(Mehmet)Kullaniciya Veritabanina Baglanti Hatasi mesaji gosterelim. said sayfanin biyerine textbox mi eklersin duruma gore buraya ekleriz.
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
        
        /*try 
        {
            sqlKomut="INSERT INTO TBLDAVA_BILGILER(davaTuru, ad, soyad, tcKimlikNo, dogumTarih, savunma, idMahkemeBilgiler)"
                    + "VALUES('"+getDavaTuru()+"', '"+getAd()+"', '"+getSoyad()+"','"+getTcKimlikNo()+"',"+DbFunctions.stringToDate(getDogumTarihi())+",'"+getSavunmasi()+"',"+getMahkemeBilgilerId()+")";
            ps=baglanti.prepareStatement(sqlKomut);
            ps.execute();
            System.out.println("DavaBilgiler tablosuna kayit eklendi.");
        } 
        catch (SQLException ex) {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DavaBilgiler tablosuna kayit eklenirken hata olustu!");
            //Onceki mahkemebilgiler tablosuna eklenen verinin silinmesi gerekiyor.
            sqlKomut="DELETE FROM TBLMAHKEME_BILGILER WHERE id="+getMahkemeBilgilerId();
            ps=baglanti.prepareStatement(sqlKomut);
            ps.execute();
            return "sifremiunuttum.xhtml";//tbldavabilgiler tablosuna veri eklenirken hata olustu. farketmek icin bu sayfaya gonderdim. Hata mesaji gostermeliyiz.
        }
        finally
        {
            DbFunctions.baglantiKapa(baglanti);
            return "anasayfa.xhtml";//iki tabloyada veriler eklendi. davalari goruntuleye gidebilir.
        }*/
        return "";
    }
    /*public void clear(){
        setTarafSayisi(-1);
        setSahitSayisi(-1);
        setDavaDegeri(0);
        setToplamTutar(0);
}//end clear`*/
}
