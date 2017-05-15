package com.hukuk.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeriTabaniIslemleri {
    
public String sqlKomut;
public String sayfa;

public String uygula()
{
    PreparedStatement ps = null;
    Connection baglanti = DbFunctions.getCon();
      
    if(baglanti == null)       return "hataolustu.xhtml";
                        
    try 
    {
        ps=baglanti.prepareStatement(sqlKomut);
        ps.execute();        
    }
    catch (SQLException ex) 
    {
        Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
        return "hataolustu.xhtml";
    }
    finally
    {
     DbFunctions.baglantiKapa(baglanti);
     return sayfa;
    }    
    }
}   

