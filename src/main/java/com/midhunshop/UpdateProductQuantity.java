package com.midhunshop;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import com.connection.DatabaseConnection;

@WebServlet("/UpdateProductQuantity")
public class UpdateProductQuantity extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Getting all the data from the user/cutomer
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        HttpSession session = request.getSession();
        String discount_price = null;
        Double productPrice = 0.0;
        try {
            //Querying to database
            ResultSet rs = DatabaseConnection.getResultFromSqlQuery("select discount_price from tblcart where customer_id='" + session.getAttribute("id") + "' and product_id='" + productId + "'");
            while (rs.next()) {
                //Gettinh data
                discount_price = rs.getString("discount_price");
                //Converting into double from string
                productPrice = Double.parseDouble(discount_price);
            }
            productPrice = productPrice * quantity;
            //Update Query for updating product quantity
            int updateQuantity = DatabaseConnection.insertUpdateFromSqlQuery("update tblcart set quantity='" + quantity + "',total_price='" + productPrice + "' where customer_id='" + session.getAttribute("id") + "' and product_id='" + productId + "' ");
            //If cart is sucessfully updated
            if (updateQuantity > 0) {
                //Sending response back to the user/customer.
                response.sendRedirect("products.jsp");
                //If cart is not updated
            } else {
                 //Sending response back to the user/customer.
                response.sendRedirect("products.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}