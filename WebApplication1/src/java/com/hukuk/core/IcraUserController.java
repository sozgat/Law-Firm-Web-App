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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author said
 */

@ManagedBean
@SessionScoped
public class IcraUserController {
    
    private List<DavaIslemleri> davaGroup = null;
    private int icraId;
    private String durum="";

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
        
    public int getIcraId() {
        return icraId;
    }

    public void setIcraId(int icraId) {
        this.icraId = icraId;
    }
    
    public List<DavaIslemleri> getDavaGroup() {
        return davaGroup;
    }

    public void setDavaGroup(List<DavaIslemleri> davaGroup) {
        this.davaGroup = davaGroup;
    }
    
    @PostConstruct
    public void init(){
        davaGroup = new ArrayList<DavaIslemleri>();
    }

    public void add(){
        davaGroup.add(new DavaIslemleri());
    }
    
    public void remove(DavaIslemleri e){
        davaGroup.remove(e);
    }
    
    public String save(){        
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String id = params.get("icraId");
        icraId = Integer.parseInt(id);
        
        VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
        
        
        
        for(DavaIslemleri davaGroup1 : davaGroup)
        {
            vti.sqlKomut = "INSERT INTO TBLICRA_BILGILER(TIP,AD,SOYAD,TCKIMLIKNO,DOGUMTARIH,SAVUNMA,IDICRALAR) "
                    + "VALUES('"+davaGroup1.getDavaTuru()+"','"+davaGroup1.getAd()+"','"+davaGroup1.getSoyad()+"','"+davaGroup1.getTcKimlikNo()+
                    "',"+DbFunctions.stringToDate(davaGroup1.getDogumTarihi())+",'"+davaGroup1.getSavunmasi()+"',"+icraId+")";
            vti.uygula();
        }
        
        return "anasayfa.xhtml";
    }

    public void goruntule(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();                                
        String no =  params.get("icraId"); 
        icraId = Integer.parseInt(no);
        davaGroup.clear();
        PreparedStatement ps = null;
        Connection con = DbFunctions.getCon();        
        
        ResultSet rs = null;
        
        try {                                    
            ps = con.prepareStatement("SELECT * FROM TBLICRA_BILGILER WHERE IDICRALAR="+icraId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                DavaIslemleri di = new DavaIslemleri();
                di.davaTuru =   rs.getString("TIP");
                di.ad =         rs.getString("AD");
                di.soyad =      rs.getString("SOYAD");
                di.tcKimlikNo = rs.getString("TCKIMLIKNO");
                
                Date dogumTarihDate = rs.getDate("DOGUMTARIH");
                String dogumTarihString = dogumTarihDate.toString();
                String [] parts = dogumTarihString.split("-");
                di.dogumTarihi = parts[2]+"/"+parts[1]+"/"+parts[0];
                
                di.savunmasi = rs.getString("SAVUNMA");
                          
                davaGroup.add(di);                
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
    
    public void sil(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();                                
        String no =  params.get("tcKimlikNo"); 
        
        VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
        vti.sqlKomut =  "DELETE FROM TBLICRA_BILGILER WHERE TCKIMLIKNO='"+no+"'"+
                        " AND TBLICRA_BILGILER.IDICRALAR="+icraId; 
                        
        vti.uygula();
        temizle();
        durum = "SİLME İŞLEMİ GERÇEKLEŞTİ";
    }
    
    public void guncelle(){        
        
        for (DavaIslemleri davaGrup1 : davaGroup) 
            {
            VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
            vti.sqlKomut = "UPDATE TBLICRA_BILGILER SET TIP='"+davaGrup1.davaTuru+"',"+
                                                        "AD='"+davaGrup1.ad+"',"+
                                                        "SOYAD='"+davaGrup1.soyad+"',"+
                                                        "TCKIMLIKNO='"+davaGrup1.tcKimlikNo+"',"+
                                                        "DOGUMTARIH="+DbFunctions.stringToDate(davaGrup1.dogumTarihi)+","+
                                                        "SAVUNMA='"+davaGrup1.savunmasi+"' "+
                                                        "WHERE TCKIMLIKNO='"+davaGrup1.tcKimlikNo+"' "+
                                                        "AND TBLICRA_BILGILER.IDICRALAR="+icraId;                                                        
            vti.uygula();                
            }
        temizle();
        durum = "GÜNCELLEME İŞLEMİ GERÇEKLEŞTİ";
    }
    public void temizle(){
        DavaIslemleri di = new DavaIslemleri();
        di.ad = "";
        di.soyad = "";
        di.davaTuru = "";
        di.dogumTarihi ="";
        di.tcKimlikNo ="";
        di.savunmasi="";
        davaGroup.clear();
    }
    public String anasayfa(){
        davaGroup.clear(); 
        return "anasayfa.xhtml";
    }
}
