package com.midhunshop;
import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.connection.DatabaseConnection;

@WebServlet("/ManagerLogin")
public class ManagerLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	//Getting all the parameters from the frontend (admin)
    	
    	PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String pass = request.getParameter("upass");
        
        try {
            
             Class.forName("com.mysql.jdbc.Driver");
            
            //Retriving our session
            HttpSession hs = request.getSession();

            //Calling Connection method
            Connection con = DatabaseConnection.getConnection();
            
            
            //Creating Statement
            PreparedStatement pst = con.prepareStatement("select name from tbladmin where email=? AND password=? ");
            pst.setString(1, email);
            pst.setString(2, pass);
            //Querying inside the database
            ResultSet resultset = pst.executeQuery();
            //If all the details are correct
            if (resultset.next()) {
            	
               hs.setAttribute("uname", resultset.getString("name"));
                //Redirecting admin to dashboard page
                response.sendRedirect(request.getContextPath()+"/dashboard.jsp");

            } else {
                //If details are wrong
                String message = "You have enter wrong credentials";
                hs.setAttribute("credential", message);
                //Redirecting admin to admin login page
                response.sendRedirect(request.getContextPath()+"/Managerlogin.jsp");
                
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            out.println(e);
        }
    }
}
