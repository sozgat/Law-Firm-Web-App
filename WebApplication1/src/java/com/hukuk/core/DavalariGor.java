package com.hukuk.core;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Toshiba
 */
@ManagedBean(name="BeanDavalariGor")
@SessionScoped
public class DavalariGor implements Serializable 
{
    private String mahkemeTipi;
    private String davaTipi;
    private int davaEsasNo=0;
    private String durusmaGun;
    private String durusmaAy;
    private String durusmaYil;
    private String avukatAd;
    private String avukatSoyad;
    private String mahkemeKarari;
    List<DavaGorClass> dbKayitlari = null;

    public List<DavaGorClass> getDbKayitlari() {
        return dbKayitlari;
    }

    public void setDbKayitlari(List<DavaGorClass> dbKayitlari) {
        this.dbKayitlari = dbKayitlari;
    }

    public String getMahkemeTipi() {
        return mahkemeTipi;
    }

    public void setMahkemeTipi(String mahkemeTipi) {
        this.mahkemeTipi = mahkemeTipi;
    }

    public String getDavaTipi() {
        return davaTipi;
    }

    public void setDavaTipi(String davaTipi) {
        this.davaTipi = davaTipi;
    }

    public int getDavaEsasNo() {
        return davaEsasNo;
    }

    public void setDavaEsasNo(int davaEsasNo) {
        this.davaEsasNo = davaEsasNo;
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

    public String getMahkemeKarari() {
        return mahkemeKarari;
    }

    public void setMahkemeKarari(String mahkemeKarari) {
        this.mahkemeKarari = mahkemeKarari;
    }
    
    //Getter ve Setter Sonu.----------------------------
    
    public void goruntule(){
        
        Connection con = DbFunctions.getCon();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String bilgiler = " WHERE ";        
        dbKayitlari = new ArrayList<DavaGorClass>();
        
        if(!mahkemeTipi.equals("SEÇİLMEDİ")) bilgiler += ("MAHKEMETIPI='"+mahkemeTipi+"' AND ");
        if(!davaTipi.equals("SEÇİLMEDİ")) bilgiler += ("DAVATIPI='"+davaTipi+"' AND ");
        if(davaEsasNo != 0) bilgiler += ("DAVAESASNO="+davaEsasNo+" AND ");
        
        if(!durusmaGun.equals("") && !durusmaAy.equals("") && !durusmaYil.equals(""))
            bilgiler += ("DAVATARIH="+DbFunctions.stringToDate(durusmaGun+"/"+durusmaAy+"/"+durusmaYil)+" AND ");
        else if(!durusmaGun.equals("") && !durusmaAy.equals(""))
            bilgiler += ("DAY(DAVATARIH)="+durusmaGun+" AND MONTH(DAVATARIH)="+durusmaAy+" AND ");
        else if(!durusmaGun.equals("") && !durusmaYil.equals(""))
            bilgiler += ("DAY(DAVATARIH)="+durusmaGun+" AND YEAR(DAVATARIH)="+durusmaYil+" AND ");
        else if(!durusmaAy.equals("") && !durusmaYil.equals(""))
            bilgiler += ("MONTH(DAVATARIH)="+durusmaAy+" AND YEAR(DAVATARIH)="+durusmaYil+" AND ");
        else if(!durusmaGun.equals(""))
            bilgiler += ("DAY(DAVATARIH)="+durusmaGun+" AND ");
        else if(!durusmaAy.equals(""))
            bilgiler += ("MONTH(DAVATARIH)="+durusmaAy+" AND ");
        else if(!durusmaYil.equals(""))
            bilgiler += ("YEAR(DAVATARIH)="+durusmaYil+" AND ");
        
        if(!avukatAd.equals("") && !avukatSoyad.equals("") ) bilgiler += ("AVUKATADSOYAD='"+avukatAd+" "+avukatSoyad+"' AND ");
        else if(!avukatAd.equals("")) bilgiler += ("AVUKATADSOYAD LIKE '%"+avukatAd+"%' AND ");
        else if(!avukatSoyad.equals("")) bilgiler += ("AVUKATADSOYAD LIKE '%"+avukatSoyad+"%' AND ");
        
        if(!mahkemeKarari.equals("SEÇİLMEDİ")) bilgiler += ("MAHKEMEKARAR='"+mahkemeKarari+"'");
        
        if(bilgiler.substring(bilgiler.length()-4).equals("AND "))
            bilgiler =bilgiler.substring(0, bilgiler.length()-5);
            
        if(bilgiler.equals(" WHERE ")) bilgiler = "";
        
        try {                                    
            ps = con.prepareStatement("SELECT MAHKEMETIPI,DAVATIPI,DAVAESASNO,DAVATARIH,AVUKATADSOYAD,MAHKEMEKARAR FROM TBLMAHKEME_BILGILER"+bilgiler);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                DavaGorClass sutunlar = new DavaGorClass();
                sutunlar.mahkemeTipi = rs.getString("MAHKEMETIPI");
                sutunlar.davaTipi = rs.getString("DAVATIPI");
                sutunlar.davaEsasNo = rs.getInt("DAVAESASNO");
                sutunlar.durusmaTarih = DbFunctions.dateToString(rs.getDate("DAVATARIH").toString());
                sutunlar.avukatAdSoyad = rs.getString("AVUKATADSOYAD");
                sutunlar.mahkemeKarari = rs.getString("MAHKEMEKARAR");
                
                dbKayitlari.add(sutunlar);
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
             
    }
}
