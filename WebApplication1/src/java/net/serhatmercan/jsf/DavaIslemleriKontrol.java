package net.serhatmercan.jsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

public class DavaIslemleriKontrol {
    
    private List<DavaIslemleri> davaGrup = null;
    
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
        
        
        PreparedStatement ps = null;
        Connection baglanti = DbFunctions.getCon();
        String sqlKomut;
        
        if(baglanti == null)
        {            
            return "hataolustu.xhtml";//Burayi ayri bir hata sayfasina dondurelim yada hata mesaji gosterip ayni sayfada tutalim
        }                
       
        for (DavaIslemleri davaGrup1 : davaGrup) {
                        
            sqlKomut="INSERT INTO TBLDAVA_BILGILER(davaTuru, ad, soyad, tcKimlikNo, dogumTarih, savunma, idMahkemeBilgiler)"
                    + "VALUES('"+davaGrup1.getDavaTuru()+"', '"+davaGrup1.getAd()+"', '"
                    +davaGrup1.getSoyad()+"','"+davaGrup1.getTcKimlikNo()+"',"+DbFunctions.stringToDate(davaGrup1.getDogumTarihi())
                    +",'"+davaGrup1.getSavunmasi()+"',"+1+")";
            ps=baglanti.prepareStatement(sqlKomut);                       
                        
            ps.execute();
        }
        DbFunctions.baglantiKapa(baglanti);
        return "anasayfa.xhtml";
    
    }
    


    public List<DavaIslemleri> getDavaGrup() {
        return davaGrup;
    }

    public void setDavaGrup(List<DavaIslemleri> davaGroup) {
        this.davaGrup = davaGroup;
    }
    
}
