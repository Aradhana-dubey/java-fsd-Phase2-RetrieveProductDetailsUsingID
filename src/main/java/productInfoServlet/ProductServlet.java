package productInfoServlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */

public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve product ID from the form
        int productId = Integer.parseInt(request.getParameter("productId"));
         
       try {
    	   
	       Class.forName("com.mysql.cj.jdbc.Driver");
       } catch(ClassNotFoundException e) {
    	   e.printStackTrace();
    	   
       };
       
       Connection con= null;
	       
	       try {
    	    con= DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommercedb", "root", "Arad@0712");
    	   String sql="SELECT * FROM oilProduct WHERE ID = ?";
    	   PreparedStatement ps= con.prepareStatement(sql);
    		   
    	   ps.setInt(1, productId);
    	   try(ResultSet resultSet= ps.executeQuery()){
    		   if(resultSet.next()) {
    			   out.println("<h2> Product Information </h2>");
    			   out.println("<p> ID: " +resultSet.getInt("ID")+ "</p>");
    			   out.println("<p> Name of Product: " +resultSet.getString("ProductName")+ "</p>");
    			   out.println("<p> Price of Product: " +resultSet.getBigDecimal("ProductPrice")+ "</p>");
    			   out.println("<p> Details of Product: " +resultSet.getString("ProductDetails")+ "</p>");
    		   }
    		   else {
    			   out.println("<h2>Error</h2>");
    			   out.println("<p> Product not found");
    		   }
    	   
    	   }
    	
    	   
    	   
    	   
    	   
       }catch (SQLException e) {
       e.printStackTrace();
       out.println(e);
            out.println("<h2>Error</h2>");
            out.println("<p>Database connection error</p>");
        
       }
	       out.close() ;
	       }
}
