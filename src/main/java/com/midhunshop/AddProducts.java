package com.midhunshop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.connection.DatabaseConnection;

@WebServlet("/AddProducts") @MultipartConfig
public class AddProducts extends HttpServlet {

    //Path where all the images are stored
    private final String UPLOAD_DIRECTORY = "D:\\eclipse ws\\OnlineShop\\src\\main\\webapp\\uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Creating session
        HttpSession session = request.getSession();
        
            try {
                //Taking all image requests
                int id;
                String imageName = null;
                String productName = null;
                String productQuantity = null;
                String productPrice = null;
                String descrip = null;
                String mrpPrice = null;
                String status = null;
                String category = null;

                //SALTCHARS to generate unique code for product
                String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                StringBuilder salt = new StringBuilder();
                Random rnd = new Random();
                while (salt.length() < 3) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }
                String code = salt.toString();

                Collection<Part> parts = request.getParts();
                if (parts.size() != 3) {
                   //can write error page saying all details are not entered
                 }

                 Part filePart = request.getPart("uploadLogo");
                 InputStream imageInputStream = filePart.getInputStream();
                 //read imageInputStream
                 filePart.write("UPLOAD_DIRECTORY + File.separator + imageName");
                 //can also write the photo to local storage

                 //Read Name, String Type 
                 Part namePart = request.getPart("pname");
                 if(namePart.getSize() > 20){
                     //write name cannot exceed 20 chars
                 }
                 //use nameInputStream if required        
                 InputStream nameInputStream = namePart.getInputStream();
                 //name , String type can also obtained using Request parameter 
                  productName = request.getParameter("pname");
                  productPrice = request.getParameter("price");
                  descrip = request.getParameter("description");
                  mrpPrice = request.getParameter("mprice");
                  status = request.getParameter("fstatus");
                  category = request.getParameter("pcategory");
                  productQuantity = request.getParameter("stock");
                  id = Integer.parseInt(request.getParameter("pid"));
                



              
                try {
                    
                    String imagePath = UPLOAD_DIRECTORY + imageName;
                    //Querying to insert product in the table
                    int i = DatabaseConnection.insertFromSqlQuery(
                    		id,imagePath,productName,imageName,category,descrip,productPrice,mrpPrice,productQuantity,status);
                    //If product inserted sucessfully in the database
                    if (i > 0) {
                        String success = "Product added successfully.";
                        //Adding method in session.
                        session.setAttribute("message", success);
                        //Response send to the admin-add-product.jsp
                        response.sendRedirect("add-product.jsp");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                //If any occur occured
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }
       
        
    }
}
