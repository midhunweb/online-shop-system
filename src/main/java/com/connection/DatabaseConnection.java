package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class DatabaseConnection {

    //Creating Connection
    public static Connection connection;

    //Creating universal method to open connect will mysql database
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
    	String url = "jdbc:mysql://localhost:3306/shopsystem?characterEncoding=utf8";
        String username = "root";
        String password = "100%Discount";
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
            connection =
               DriverManager.getConnection(url, username, password);

            // Do something with the Connection

        return (connection);
    }

    //Creating universal method to close connect will mysql database
    public static void CloseConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    //Creating universal method to query for retriving information
    public static ResultSet getResultFromSqlQuery(String SqlQueryString) {
        //Creating Resultset object
        ResultSet rs = null;
        try {
            //Checking whether the connection is null or null
            if (connection == null) {
                connection = getConnection();
            }
            //Querying the query
            rs = connection.createStatement().executeQuery(SqlQueryString);
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return null;
        }
        
    }

    //Creating universal method to query for  updating information in mysql database
    public static int insertUpdateFromSqlQuery(String SqlQueryString) {
        int i = 2;
        try {
            //Checking whether the connection is null or null
            if (connection == null) {
               connection = getConnection();
            }
            //Querying the query
            i = connection.createStatement().executeUpdate(SqlQueryString);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return i;
    }
    
    //Creating universal method to query for  updating information in mysql database
    public static int insertFromSqlQuery(int id, String image, String name, String image_name, 
    		String product_category, String description, String price, String mrp_price, String stock, String status) {
        int i = 2;
        try {
            //Checking whether the connection is null or null
            if (connection == null) {
               connection = getConnection();
            }
            //Querying the query
            String SqlString = "insert into tblproduct"+ " values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(SqlString);
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setString(3, description);
            pst.setString(4, product_category);
            pst.setString(5, image);
            pst.setString(6, mrp_price);
            pst.setString(7, image_name);
            pst.setString(8, price);
            pst.setString(9, stock);
            pst.setString(10, status);
            
            
            i = pst.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return i;
    }
}
