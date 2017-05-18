package com.hukuk.core;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class IcralariGor implements Serializable {
    
    private String durusmaGun;
    private String durusmaAy;
    private String durusmaYil;
    private String avukatAd;
    private String avukatSoyad;
    private String kararYil;
    private String kararNo;
    private String hukumGun;
    private String hukumAy;
    private String hukumYil;
    private String alacakMin;
    private String alacakMax;
    private int icraId=0;
    
    List<IcraGorClass> dbKayitlari = null;
    
    public IcralariGor() {
    }

    public int getIcraId() {
        return icraId;
    }

    public void setIcraId(int icraId) {
        this.icraId = icraId;
    }
    
    public String getDurusmaGun() {
        return durusmaGun;
    }

    public void setDurusmaGun(String durusmaGun) {
        this.durusmaGun = durusmaGun;
    }

    public String getDurusmaAy() {
        return durusmaAy;
    }

    public void setDurusmaAy(String durusmaAy) {
        this.durusmaAy = durusmaAy;
    }

    public String getDurusmaYil() {
        return durusmaYil;
    }

    public void setDurusmaYil(String durusmaYil) {
        this.durusmaYil = durusmaYil;
    }

    public String getAvukatAd() {
        return avukatAd;
    }

    public void setAvukatAd(String avukatAd) {
        this.avukatAd = avukatAd;
    }

    public String getAvukatSoyad() {
        return avukatSoyad;
    }

    public void setAvukatSoyad(String avukatSoyad) {
        this.avukatSoyad = avukatSoyad;
    }

    public String getKararYil() {
        return kararYil;
    }

    public void setKararYil(String kararYil) {
        this.kararYil = kararYil;
    }

    public String getKararNo() {
        return kararNo;
    }

    public void setKararNo(String kararNo) {
        this.kararNo = kararNo;
    }

    public String getHukumGun() {
        return hukumGun;
    }

    public void setHukumGun(String hukumGun) {
        this.hukumGun = hukumGun;
    }

    public String getHukumAy() {
        return hukumAy;
    }

    public void setHukumAy(String hukumAy) {
        this.hukumAy = hukumAy;
    }

    public String getHukumYil() {
        return hukumYil;
    }

    public void setHukumYil(String hukumYil) {
        this.hukumYil = hukumYil;
    }

    public String getAlacakMin() {
        return alacakMin;
    }

    public void setAlacakMin(String alacakMin) {
        this.alacakMin = alacakMin;
    }

    public String getAlacakMax() {
        return alacakMax;
    }

    public void setAlacakMax(String alacakMax) {
        this.alacakMax = alacakMax;
    }

    public List<IcraGorClass> getDbKayitlari() {
        return dbKayitlari;
    }

    public void setDbKayitlari(List<IcraGorClass> dbKayitlari) {
        this.dbKayitlari = dbKayitlari;
    }
    
    public void sil()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();                                
        String no =  params.get("icraId"); 
        icraId = Integer.parseInt(no);        
        
        VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
        vti.sqlKomut = "DELETE FROM TBLICRA_BILGILER WHERE IDICRALAR="+icraId;                       
        vti.uygula();
                
        vti.sqlKomut = "DELETE FROM TBLICRALAR WHERE ID="+icraId;
        vti.uygula();
        
    }
    
    public void goruntule()
    {
        Connection baglanti = DbFunctions.getCon();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String bilgiler = " WHERE ";
        dbKayitlari = new ArrayList<IcraGorClass>();
        
        if(!durusmaGun.equals("") && !durusmaAy.equals("") && !durusmaYil.equals(""))
            bilgiler += ("DURUSMATARIHI="+DbFunctions.stringToDate(durusmaGun+"/"+durusmaAy+"/"+durusmaYil)+" AND ");
        else if(!durusmaGun.equals("") && !durusmaAy.equals(""))
            bilgiler += ("DAY(DURUSMATARIHI)="+durusmaGun+" AND MONTH(DURUSMATARIHI)="+durusmaAy+" AND ");
        else if(!durusmaGun.equals("") && !durusmaYil.equals(""))
            bilgiler += ("DAY(DURUSMATARIHI)="+durusmaGun+" AND YEAR(DURUSMATARIHI)="+durusmaYil+" AND ");
        else if(!durusmaAy.equals("") && !durusmaYil.equals(""))
            bilgiler += ("MONTH(DURUSMATARIHI)="+durusmaAy+" AND YEAR(DURUSMATARIHI)="+durusmaYil+" AND ");
        else if(!durusmaGun.equals(""))
            bilgiler += ("DAY(DURUSMATARIHI)="+durusmaGun+" AND ");
        else if(!durusmaAy.equals(""))
            bilgiler += ("MONTH(DURUSMATARIHI)="+durusmaAy+" AND ");
        else if(!durusmaYil.equals(""))
            bilgiler += ("YEAR(DURUSMATARIHI)="+durusmaYil+" AND ");
        
        if(!avukatAd.equals("") && !avukatSoyad.equals("") ) bilgiler += ("AVUKATADSOYAD='"+avukatAd+" "+avukatSoyad+"' AND ");
        else if(!avukatAd.equals("")) bilgiler += ("AVUKATADSOYAD LIKE '%"+avukatAd+"%' AND ");
        else if(!avukatSoyad.equals("")) bilgiler += ("AVUKATADSOYAD LIKE '%"+avukatSoyad+"%' AND ");
        
        if(!kararYil.equals("")) bilgiler += ("YEAR(KARARYIL)="+kararYil+" AND ");
        if(!kararNo.equals("")) bilgiler += ("KARARNO="+Integer.parseInt(kararNo)+" AND ");//INTEGER PARSE!!!
        
        if(!hukumGun.equals("") && !hukumAy.equals("") && !hukumYil.equals(""))
            bilgiler += ("HUKUMTARIHI="+DbFunctions.stringToDate(hukumGun+"/"+hukumAy+"/"+hukumYil)+" AND ");
        else if(!hukumGun.equals("") && !hukumAy.equals(""))
            bilgiler += ("DAY(HUKUMTARIHI)="+hukumGun+" AND MONTH(HUKUMTARIHI)="+hukumAy+" AND ");
        else if(!hukumGun.equals("") && !hukumYil.equals(""))
            bilgiler += ("DAY(HUKUMTARIHI)="+hukumGun+" AND YEAR(HUKUMTARIHI)="+hukumYil+" AND ");
        else if(!hukumAy.equals("") && !hukumYil.equals(""))
            bilgiler += ("MONTH(HUKUMTARIHI)="+hukumAy+" AND YEAR(HUKUMTARIHI)="+hukumYil+" AND ");
        else if(!hukumGun.equals(""))
            bilgiler += ("DAY(HUKUMTARIHI)="+hukumGun+" AND ");
        else if(!hukumAy.equals(""))
            bilgiler += ("MONTH(HUKUMTARIHI)="+hukumAy+" AND ");
        else if(!hukumYil.equals(""))
            bilgiler += ("YEAR(HUKUMTARIHI)="+hukumYil+" AND ");
        
        double alMin=0.0, alMax=999999999.0;
        if(!alacakMin.equals("") && !alacakMax.equals(""))
        {
            alMin = Double.parseDouble(alacakMin);
            alMax = Double.parseDouble(alacakMax);
        }
        else if(!alacakMin.equals(""))
            alMin = Double.parseDouble(alacakMin);
        else if(!alacakMax.equals(""))
            alMax = Double.parseDouble(alacakMax);

        bilgiler += (" ALACAKTOPLAMTUTARI BETWEEN "+alMin+" AND "+alMax);
        
        if(bilgiler.substring(bilgiler.length()-4).equals("AND "))
            bilgiler =bilgiler.substring(0, bilgiler.length()-5);
            
        if(bilgiler.equals(" WHERE ")) bilgiler = "";
        
        try
        {
            ps = baglanti.prepareStatement("SELECT ID,DURUSMATARIHI, AVUKATADSOYAD, KARARYIL, KARARNO, HUKUMTARIHI, ALACAKTOPLAMTUTARI FROM TBLICRALAR"+bilgiler);
            ps.execute();
            rs = ps.getResultSet();
            
            while(rs.next())
            {
                String []dizi = rs.getDate("KARARYIL").toString().split("-");
                IcraGorClass veriler = new IcraGorClass();
                veriler.setDurusmaTarih(DbFunctions.dateToString(rs.getDate("DURUSMATARIHI").toString()));
                veriler.setAvukatAdSoyad(rs.getString("AVUKATADSOYAD"));
                veriler.setKararNo(dizi[0]+"/"+ rs.getString("KARARNO"));
                veriler.setHukumTarih(DbFunctions.dateToString(rs.getDate("HUKUMTARIHI").toString()));
                veriler.setAlacakTutar(rs.getDouble("ALACAKTOPLAMTUTARI"));
                veriler.setId(rs.getInt("ID"));
                dbKayitlari.add(veriler);
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(IcralariGor.class.getName()).log(Level.SEVERE, null, ex);
        }
        DbFunctions.baglantiKapa(baglanti);
    }
}
