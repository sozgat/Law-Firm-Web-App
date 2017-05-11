package net.serhatmercan.jsf;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Toshiba
 */
@ManagedBean(name = "BeanDavalariGor")
@SessionScoped
public class DavalariGor implements Serializable 
{
    private String mahkemeTipi;
    private String davaTipi;
    private int davaEsasNo;
    private String durusmaGun;
    private String durusmaAy;
    private String durusmaYil;
    private String avukatAd;
    private String avukatSoyad;
    private String mahkemeKarari;

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
    
    public ArrayList liste()
    {
        ArrayList mahkemeBilgileri=null;
        ArrayList davaBilgileri;
    }
}
