package com.hukuk.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbFunctions {
    
    public static void baglantiKapa(Connection con)
    {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(KayitKontrol.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Baglanti kapatilirken hata olustu!");
        }
    }
    
    public static Connection getCon()
    {
        Connection con = null;
        String url = "jdbc:derby://localhost:1527/HUKUKWEBDB";
        String user = "hukukwebdb", pass = "1234";
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            Logger.getLogger(KayitKontrol.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Veritabanina Baglanilamadi!\nDetay : "+ex.getMessage());
            return null;
        }
        
        return con;
    }
    
    public static String stringToDate(String date)
    {
        String[] formats = date.split("/");
        return "{d'"+formats[2]+"-"+formats[1]+"-"+formats[0]+"'}";
    }
    
    public static String stringToDateKarar(String date, int sira)//sira=0 kararYil, sira=1 kararNo
    {
        String[] formats = date.split("/");
        if(sira == 0)
            return "{d'"+formats[0]+"-01-01'}";
        else 
            return formats[1];
    }
    
    public static String dateToString(String date)
    {
        String[] formats = date.split("-");
        return formats[2]+"/"+formats[1]+"/"+formats[0];
    }
    public static Date toDate(Timestamp timestamp) {
        long millisec = timestamp.getTime() + (timestamp.getNanos() / 1000000);
        return new Date(millisec);
    }
}
