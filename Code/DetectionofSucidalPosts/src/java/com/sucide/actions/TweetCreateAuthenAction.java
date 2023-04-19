/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sucide.actions;

import com.sucide.db.DBConnections;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author welcome
 */
@MultipartConfig
public class TweetCreateAuthenAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String tname = request.getParameter("tweet");
        String tdesc = request.getParameter("description");
        Part filePart = request.getPart("pic");
        Connection con = null;
        PreparedStatement ps = null;
        HttpSession session = request.getSession();
        String uname = (String) session.getAttribute("uname");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = tname.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = " ";
            for (int i = 0; i < digest.length; i++) {
                hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
            }
            String hash = hexStr;
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

            Date now = new Date();

            String strDate = sdfDate.format(now);
            String strTime = sdfTime.format(now);
            String dt = strDate + "   " + strTime;
            con = DBConnections.getConnection();
            ps = con.prepareStatement("insert into ttopic(tname,description,hash,imagess,dt,user,rank) values(?,?,?,?,?,?,?)");
            ps.setString(1, tname);
            ps.setString(2, tdesc);
            ps.setString(3, hash);
            ps.setBinaryStream(4, filePart.getInputStream());
            ps.setString(5, dt);
            ps.setString(6, uname);
            ps.setInt(7, 0);     

            int x = ps.executeUpdate();
            if (x > 0) {
                //request.setAttribute("msg","successfull");
                out.print("Tweet Created Successfully");
            }else{
            out.print("Tweet Topic Already Exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
