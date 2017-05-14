package com.hukuk.services;

import com.hukuk.core.DavaKayit;
import com.hukuk.core.DbFunctions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.DefaultScheduleEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yasin
 */
public class TakvimService {
    
    private static Connection connection = DbFunctions.getCon();
    
    public static List<DefaultScheduleEvent> getEventList(){
        
        List<DefaultScheduleEvent> list = new ArrayList<DefaultScheduleEvent>();
        
        PreparedStatement ps = null;
        
        if(connection == null)
        {
            return null;
        }
        
        String sqlKomut = "SELECT ID, BASLIK, BASLANGIC, BITIS FROM HUKUKWEBDB.TBLTAKVIM ";
        
        try
        {
            ps=connection.prepareStatement(sqlKomut);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
               int id = rs.getInt("ID");
               String baslik = rs.getString("BASLIK");
               Timestamp baslangic = rs.getTimestamp("BASLANGIC");
               Timestamp bitis = rs.getTimestamp("BITIS");
               
               DefaultScheduleEvent event = new DefaultScheduleEvent(baslik, DbFunctions.toDate(baslangic),DbFunctions.toDate(bitis), id  );      
               
               list.add(event);
            }

            System.out.println("MahkemeBilgiler Tablosuna Kayit Eklendi.");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("MahkemeBilgiler tablosuna kayit eklenirken hata olustu!");
            return null;
        }

        return list;
    }
    
    
    
    public static int addEvent(String title, Date baslangic, Date bitis ){
        PreparedStatement ps = null;
        int kayitNo = 0;
        if(connection == null)
        {
            return kayitNo;
        }
        
        String sqlKomut = "INSERT INTO HUKUKWEBDB.TBLTAKVIM (BASLIK, BASLANGIC, BITIS) VALUES (?, ?, ?)";
        
        try
        {
            ps=connection.prepareStatement(sqlKomut);
            
            ps.setString(1,title );
            ps.setTimestamp(2, new Timestamp(baslangic.getTime()));
            ps.setTimestamp(3, new Timestamp(bitis.getTime()));
             
            ps.executeUpdate();
            System.out.println("MahkemeBilgiler Tablosuna Kayit Eklendi.");
     
            kayitNo = getLastRow("TBLTAKVIM");

        }
        catch (SQLException ex)
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("MahkemeBilgiler tablosuna kayit eklenirken hata olustu!");
            return kayitNo;
        }
       return kayitNo; 
    }
    
     public static int updateEvent(int id, String title, Date baslangic, Date bitis ){
        PreparedStatement ps = null;
        
        int resultCount = 0;
        
        if(connection == null)
        {
            return resultCount;
        }
        
        String sqlKomut = "UPDATE HUKUKWEBDB.TBLTAKVIM  SET BASLIK = ?, BASLANGIC = ? , BITIS = ? WHERE ID = ? ";
        
        try
        {
            ps=connection.prepareStatement(sqlKomut);
            
            ps.setString(1,title );
            ps.setTimestamp(2, new Timestamp(baslangic.getTime()));
            ps.setTimestamp(3, new Timestamp(bitis.getTime()));
            ps.setInt(4, id);
            
            resultCount = ps.executeUpdate();
            System.out.println("MahkemeBilgiler Tablosuna Kayit Eklendi.");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("MahkemeBilgiler tablosuna kayit eklenirken hata olustu!");
            resultCount =  0;
        }
       return resultCount;
    }
    
    
      public static int getLastRow( String tableName ){
            
        PreparedStatement ps = null;
        int lastCount = 0;
        if(connection == null)
        {
            return lastCount;
        }
        
        String sqlKomut = "SELECT count(*) as ROWCOUNT FROM HUKUKWEBDB."+ tableName;
        
        try
        {
            ps=connection.prepareStatement(sqlKomut);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
              lastCount = rs.getInt("ROWCOUNT");
            }

            System.out.println("MahkemeBilgiler Tablosuna Kayit Eklendi.");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("MahkemeBilgiler tablosuna kayit eklenirken hata olustu!");
            return lastCount;
        }

        return lastCount;
    }

    public static int removeEvent(int id) {
       
        PreparedStatement ps = null;
        
        int resultCount = 0;
        
        if(connection == null)
        {
            return resultCount;
        }
        
        String sqlKomut = "DELETE FROM HUKUKWEBDB.TBLTAKVIM  WHERE ID = ? ";
        
        try
        {
            ps=connection.prepareStatement(sqlKomut);
            ps.setInt(1, id);
            
            resultCount = ps.executeUpdate();
            System.out.println("MahkemeBilgiler Tablosuna Kayit Eklendi.");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DavaKayit.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("MahkemeBilgiler tablosuna kayit eklenirken hata olustu!");
            resultCount =  0;
        }
       return resultCount;   
    }
     
     
     
    
}
