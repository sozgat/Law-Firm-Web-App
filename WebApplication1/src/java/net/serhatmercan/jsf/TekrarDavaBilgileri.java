/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.serhatmercan.jsf;

import java.io.Serializable;

/**
 *
 * @author said
 */
public class TekrarDavaBilgileri implements Serializable{
    private String davaTcKimlikNo; // TC Kimlik No üzerinde işlem yapmayacağımız için String tanımladım. Değişim yapılabilir.
    private String davaad;
    private String davaSoyad;
    private String davaDogumTarihi; // Dogum tarihini date felan mı yapsak, veritabanında ben date yapıcam oraya kaydederken sorun yasarmıyız?(Mehmet)

    public String getDavaTcKimlikNo() {
        return davaTcKimlikNo;
    }

    public void setDavaTcKimlikNo(String davaTcKimlikNo) {
        this.davaTcKimlikNo = davaTcKimlikNo;
    }

    public String getDavaad() {
        return davaad;
    }

    public void setDavaad(String davaad) {
        this.davaad = davaad;
    }

    public String getDavaSoyad() {
        return davaSoyad;
    }

    public void setDavaSoyad(String davaSoyad) {
        this.davaSoyad = davaSoyad;
    }

    public String getDavaDogumTarihi() {
        return davaDogumTarihi;
    }

    public void setDavaDogumTarihi(String davaDogumTarihi) {
        this.davaDogumTarihi = davaDogumTarihi;
    }

}
