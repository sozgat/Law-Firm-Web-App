package com.hukuk.core;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

public class DavaMasrafi {
 
     
    private double davaDegeri   =0;
    private double toplamTutar  =0;    
    private String mahkemeTuru  =null;
    private String harcTuru     ="maktu";
    private String tarafSayisi  =null;
    private String sahitSayisi  =null;
    private final double diger  =56;
    private final String dizi[] ={"0","1","2","3","4","5","6","7","8","9","10"};
    
    public void temizle(){
        davaDegeri  =0;
        toplamTutar =0;    
        mahkemeTuru =null;
        harcTuru    ="maktu";
        tarafSayisi =null;
        sahitSayisi =null;
    }
    
    public double getDavaDegeri() {
        return davaDegeri;
    }

    public void setDavaDegeri(double davaDegeri) {
        this.davaDegeri = davaDegeri;
    }

    public double getToplamTutar() {
        return toplamTutar;
    }

    public void setToplamTutar(double toplamTutar) {
        this.toplamTutar = toplamTutar;
    }

    public String getMahkemeTuru() {
        return mahkemeTuru;
    }

    public void setMahkemeTuru(String mahkemeTuru) {
        this.mahkemeTuru = mahkemeTuru;
    }

    public String getHarcTuru() {
        return harcTuru;
    }

    public void setHarcTuru(String harcTuru) {
        this.harcTuru = harcTuru;
    }

    public String getTarafSayisi() {
        return tarafSayisi;
    }

    public void setTarafSayisi(String tarafSayisi) {
        this.tarafSayisi = tarafSayisi;
    }

    public String getSahitSayisi() {
        return sahitSayisi;
    }

    public void setSahitSayisi(String sahitSayisi) {
        this.sahitSayisi = sahitSayisi;
    }

    public double mahkemeTuruHesapla()
    {
        double toplam=0;
        switch (mahkemeTuru) {
            case "sulh":
                toplam = 14.5;
                break;            
            case "asliyle":
                toplam = 31.4;
                break;
            case "ticaret":
                toplam = 670.4;
                break;
            case "is":
                toplam = 48.3;
                break;
            default:
                break;
        }
        return toplam;
    }
        
    public double harcHesapla()
    {
        double toplam=0;
        if     (harcTuru.equals("maktu")) toplam = 31.4;
        else if(harcTuru.equals("nispi")) toplam =(davaDegeri/1000*(68.31));
        return toplam;
    }
               
    public double turSayisiHesap(String tur, int turSabiti)
    {
        double toplam=0;        
        int i; 
        for(i=0;i<=10;i++)
        {
            if(tur.equals(dizi[i]))
            {
                toplam = turSabiti * i; 
                break;
            } 
        }             
        return toplam;    
    }
   
    public void hesapla()
    {
        double mahkemeTuruToplam = mahkemeTuruHesapla();
        double harcToplam        = harcHesapla();
        double tarafSayisiToplam = turSayisiHesap(tarafSayisi, 45);
        double sahitSayisiToplam = turSayisiHesap(sahitSayisi, 29);
      
        toplamTutar = mahkemeTuruToplam + davaDegeri + harcToplam + tarafSayisiToplam + sahitSayisiToplam + diger;
        toplamTutar = Math.round(toplamTutar * 100.0) / 100.0;
        
        VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
        vti.sqlKomut = "INSERT INTO TBLMASRAFLAR(mahkemeTuruToplam, harcToplam, tarafSayisiToplam, sahitSayisiToplam, toplamTutar)"
                       +"VALUES("+mahkemeTuruToplam+","+harcToplam+","+tarafSayisiToplam+","
                                 +sahitSayisiToplam+","+toplamTutar+")";
       
        
        vti.uygula();
        
    }

    public void guncelle()
    {
        double mahkemeTuruToplam = mahkemeTuruHesapla();
        double harcToplam        = harcHesapla();
        double tarafSayisiToplam = turSayisiHesap(tarafSayisi, 45);
        double sahitSayisiToplam = turSayisiHesap(sahitSayisi, 29);
      
        toplamTutar = mahkemeTuruToplam + davaDegeri + harcToplam + tarafSayisiToplam + sahitSayisiToplam + diger;
        toplamTutar = Math.round(toplamTutar * 100.0) / 100.0;
        
        VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
        vti.sqlKomut = "UPDATE TBLMASRAFLAR SET MAHKEMETURUTOPLAM="+mahkemeTuruToplam+","+                                                
                                                "HARCTOPLAM="+harcToplam+","+
                                                "TARAFSAYISITOPLAM="+tarafSayisiToplam+","+
                                                "SAHITSAYISITOPLAM="+sahitSayisiToplam+","+
                                                "TOPLAMTUTAR="+toplamTutar;
        
        vti.uygula();
    }        
    
}
