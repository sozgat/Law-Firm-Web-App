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
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped

public class DavaIslemleriKontrol {
    
    private List<DavaIslemleri> davaGrup = null;
    private int davaEsasNo;
    
     public List<DavaIslemleri> getDavaGrup() {
        return davaGrup;
    }

    public void setDavaGrup(List<DavaIslemleri> davaGroup) {
        this.davaGrup = davaGroup;
    }
    
    @PostConstruct
    public void init(){
        davaGrup = new ArrayList<DavaIslemleri>();
    }

    public void add(){
        davaGrup.add(new DavaIslemleri());
    }
    
    public void remove(DavaIslemleri e){
        davaGrup.remove(e);
    }
    
    public String save() throws SQLException{
        
        int mahkemeBilgilerId;
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();                                
        String no =  params.get("davaEsasNo"); 
        davaEsasNo = Integer.parseInt(no);
        
        PreparedStatement ps = null;
        Connection baglanti = DbFunctions.getCon();
        String sqlKomut;
        
        ResultSet rs = null;
        String sqlSorgu = "SELECT ID FROM TBLMAHKEME_BILGILER WHERE DAVAESASNO="+davaEsasNo+" FETCH FIRST 1 ROWS ONLY";
        
        try 
        {
            ps = baglanti.prepareStatement(sqlSorgu);
            ps.execute();
            rs = ps.getResultSet();
            
            if(rs.next())
                mahkemeBilgilerId = rs.getInt("ID");
            else
                return "hataolustu.xhtml";
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            return "hataolustu.xhtml";
        }
                     
        //Simdi DavaBilgilerini ekleyebiliriz.
        for (DavaIslemleri davaGrup1 : davaGrup) {
                        
            sqlKomut="INSERT INTO TBLDAVA_BILGILER(davaTuru, ad, soyad, tcKimlikNo, dogumTarih, savunma, idMahkemeBilgiler)"
                    + "VALUES('"+davaGrup1.getDavaTuru()+"', '"+davaGrup1.getAd()+"', '"
                    +davaGrup1.getSoyad()+"','"+davaGrup1.getTcKimlikNo()+"',"+DbFunctions.stringToDate(davaGrup1.getDogumTarihi())
                    +",'"+davaGrup1.getSavunmasi()+"',"+mahkemeBilgilerId+")";
            ps=baglanti.prepareStatement(sqlKomut);                       
                        
            ps.execute();
        }
        DbFunctions.baglantiKapa(baglanti);
        return "anasayfa.xhtml";
    
    }
    
    public void goruntule(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();                                
        String no =  params.get("davaEsasNo"); 
        davaEsasNo = Integer.parseInt(no);
        
        PreparedStatement ps = null;
        Connection con = DbFunctions.getCon();        
        
        ResultSet rs = null;
        
        try {                                    
            ps = con.prepareStatement("SELECT * FROM TBLDAVA_BILGILER WHERE TBLDAVA_BILGILER.IDMAHKEMEBILGILER="
                                    + "(SELECT ID FROM TBLMAHKEME_BILGILER WHERE DAVAESASNO="+davaEsasNo+")");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                DavaIslemleri di = new DavaIslemleri();
                di.davaTuru =   rs.getString("DAVATURU");
                di.ad =         rs.getString("AD");
                di.soyad =      rs.getString("SOYAD");
                di.tcKimlikNo = rs.getString("TCKIMLIKNO");
                
                Date dogumTarihDate = rs.getDate("DOGUMTARIH");
                String dogumTarihString = dogumTarihDate.toString();
                String [] parts = dogumTarihString.split("-");
                di.dogumTarihi = parts[2]+"/"+parts[1]+"/"+parts[0];
                
                di.savunmasi = rs.getString("SAVUNMA");
                          
                davaGrup.add(di);                
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
        vti.sqlKomut =  "DELETE FROM TBLDAVA_BILGILER WHERE TCKIMLIKNO='"+no+"'"+
                        " AND TBLDAVA_BILGILER.IDMAHKEMEBILGILER=" +
                        "(SELECT ID FROM TBLMAHKEME_BILGILER WHERE DAVAESASNO="+davaEsasNo+")";
        vti.uygula();
    }

    public void temizle(){
        DavaIslemleri di = new DavaIslemleri();
        di.ad = "";
        di.soyad = "";
        di.davaTuru = "";
        di.dogumTarihi ="";
        di.tcKimlikNo ="";
        di.savunmasi="";
        davaGrup.clear();
    }

    public void guncelle(){        
        
        for (DavaIslemleri davaGrup1 : davaGrup) 
            {
            VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
            vti.sqlKomut = "UPDATE TBLDAVA_BILGILER SET DAVATURU='"+davaGrup1.davaTuru+"',"+
                                                        "AD='"+davaGrup1.ad+"',"+
                                                        "SOYAD='"+davaGrup1.soyad+"',"+
                                                        "TCKIMLIKNO='"+davaGrup1.tcKimlikNo+"',"+
                                                        "DOGUMTARIH="+DbFunctions.stringToDate(davaGrup1.dogumTarihi)+","+
                                                        "SAVUNMA='"+davaGrup1.savunmasi+"' "+
                                                        "WHERE TCKIMLIKNO='"+davaGrup1.tcKimlikNo+"' "+
                                                        "AND TBLDAVA_BILGILER.IDMAHKEMEBILGILER=" +
                                                        "(SELECT ID FROM TBLMAHKEME_BILGILER WHERE DAVAESASNO="+davaEsasNo+")";
            vti.uygula();                
            }    

    }



}

