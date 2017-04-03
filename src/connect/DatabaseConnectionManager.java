/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

//STEP 1. Import required packages
import java.sql.*;

public class DatabaseConnectionManager {
   // JDBC driver name and database URL
   private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   private static final String DB_URL = "jdbc:mysql://localhost:3306/eads";

   //  Database credentials
   private static final String USER = "root";
   private static final String PASS = "";
   
   public static Connection connect() {
        Connection conn = null;
        
        try{
           //STEP 2: Register JDBC driver
     //      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
           Class.forName(JDBC_DRIVER).newInstance();

           //STEP 3: Open a connection
//           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }

        return conn;
    }//end 
}//end JDBCExample
