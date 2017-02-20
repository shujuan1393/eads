/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

//STEP 1. Import required packages
import java.sql.*;

public class db {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:8889/eads";

   //  Database credentials
   static final String USER = "eads";
   static final String PASS = "";
   
   public static void main(String[] args) {
   Connection conn = null;
   try{
      //STEP 2: Register JDBC driver
//      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
      Class.forName(JDBC_DRIVER).newInstance();

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

//      //STEP 4: Execute a query
//      System.out.println("Creating database...");
//      stmt = conn.createStatement();
//      
//      String sql = "CREATE TABLE preferences("
//              + "id int(11), customerID int(11), itemID int(11), transactID int(11), "
//              + "satisfaction int(11), PRIMARY KEY (id));";
//      stmt.executeUpdate(sql);
//      System.out.println("Table created successfully...");
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
        System.out.println("Connection established");
      
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
}//end main
}//end JDBCExample
