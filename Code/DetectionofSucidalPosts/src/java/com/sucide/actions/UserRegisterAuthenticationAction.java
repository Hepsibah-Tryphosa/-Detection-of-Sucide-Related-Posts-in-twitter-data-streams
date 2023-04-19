/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sucide.actions;

import com.sucide.db.DBConnections;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author welcome
 */
@MultipartConfig
public class UserRegisterAuthenticationAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String uname = request.getParameter("userid");
        String pass = request.getParameter("pass");
        String email = request.getParameter("email");
        String mno = request.getParameter("mobile");
        String addr = request.getParameter("address");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        Part filePart = request.getPart("pic");
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnections.getConnection();
            ps = con.prepareStatement("insert into user(name,pass,email,mobile,addr,dob,gender,status,imagess) values(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, uname);
            ps.setString(2, pass);
            ps.setString(3, email);
            ps.setString(4, mno);
            ps.setString(5, addr);
            ps.setString(6, dob);
            ps.setString(7, gender);
            ps.setString(8, "waiting");
            ps.setBinaryStream(9, filePart.getInputStream());

            int x = ps.executeUpdate();
            if (x > 0) {
                request.setAttribute("msg", "successfull");
                out.print("Registered Successfully");
            }else{
             //out.print("Registered Successfully");
             response.sendRedirect("NameExists.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
