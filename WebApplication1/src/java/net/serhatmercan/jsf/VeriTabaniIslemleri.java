package net.serhatmercan.jsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeriTabaniIslemleri {
    
public String sqlKomut;    

public void ekle()
{
    PreparedStatement ps = null;
    Connection baglanti = DbFunctions.getCon();
      
    if(baglanti == null){       //return "anasayfa.xhtml";
                        }
    try 
    {
        ps=baglanti.prepareStatement(sqlKomut);
        ps.execute();        
    }
    catch (SQLException ex) 
    {
        Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
        //return "davakayit.xhtml";//hata mesaji verdirmeliyiz.
    }
    finally
    {
     DbFunctions.baglantiKapa(baglanti);
    //return "";
    }    
    }
}   

