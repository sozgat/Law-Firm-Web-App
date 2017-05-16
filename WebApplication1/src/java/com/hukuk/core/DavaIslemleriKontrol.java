package com.hukuk.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    public void sil(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();                                
        String no =  params.get("tcKimlikNo"); 
        int tcKimlikNo = Integer.parseInt(no);
        
        VeriTabaniIslemleri vti = new VeriTabaniIslemleri();
        vti.sqlKomut = "DELETE FROM TBLDAVA_BILGILER WHERE TCKIMLIKNO="+tcKimlikNo;
        vti.uygula();
    }


    public List<DavaIslemleri> getDavaGrup() {
        return davaGrup;
    }

    public void setDavaGrup(List<DavaIslemleri> davaGroup) {
        this.davaGrup = davaGroup;
    }
    
}
