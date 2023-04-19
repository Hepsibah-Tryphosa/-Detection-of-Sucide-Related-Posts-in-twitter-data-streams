/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sucide.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author welcome
 */
public class DBConnections {
    public static Connection con = null;
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");	
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Detection_Of_Suicide","root","root");
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
