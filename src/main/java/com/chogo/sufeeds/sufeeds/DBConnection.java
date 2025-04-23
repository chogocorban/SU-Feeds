package com.chogo.sufeeds.sufeeds;

//Corban Chogo, ICS 1.2 Group B, 165558, 13/11/2024

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static String user = "root";
    static String password = "";
    static String url = "jdbc:mysql://localhost/db_corban_chogo_165558_v2";
    static String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection getCon() throws ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName(driver);
        con = DriverManager.getConnection(url,user,password);
        return con;
    }
    }

