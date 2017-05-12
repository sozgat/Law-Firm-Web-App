package net.serhatmercan.jsf;

import javax.inject.Named;
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
    private String mahkemeTipi = null;
    private String davaTipi = null;
    private int davaEsasNo=-1;
    private String durusmaGun;
    private String durusmaAy;
    private String durusmaYil;
    private String avukatAd;
    private String avukatSoyad;
    private String mahkemeKarari;
    List<String> dbKayitlari = null;

    public List<String> getDbKayitlari() {
        return dbKayitlari;
    }

    public void setDbKayitlari(List<String> dbKayitlari) {
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
        dbKayitlari = new ArrayList<String>();
        
        if(!mahkemeTipi.equals("SEÇİLMEDİ")) bilgiler += ("MAHKEMETIPI='"+mahkemeTipi+"' AND ");
        if(!davaTipi.equals("SEÇİLMEDİ")) bilgiler += ("DAVATIPI='"+davaTipi+"' AND ");
        if(davaEsasNo != -1) bilgiler += ("DAVAESASNO='"+davaEsasNo+"'");
        
        if(bilgiler.substring(bilgiler.length()-4).equals("AND ")) 
            bilgiler =bilgiler.substring(0, bilgiler.length()-5);
            
        if(bilgiler.equals(" WHERE ")) bilgiler = "";
        
        try {                                    
            ps = con.prepareStatement("select MAHKEMETIPI,DAVATIPI,DAVAESASNO,DAVATARIH,AVUKATADSOYAD,MAHKEMEKARAR FROM TBLMAHKEME_BILGILER"+bilgiler);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                dbKayitlari.add(rs.getString("MAHKEMETIPI")+"   "+rs.getString("DAVATIPI")+"   "+rs.getInt("DAVAESASNO")+"   "+
                                rs.getDate("DAVATARIH")+"   "+rs.getString("AVUKATADSOYAD")+"   "+rs.getString("MAHKEMEKARAR"));    
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
