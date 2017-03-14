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
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:8889/eads";

   //  Database credentials
   static final String USER = "eads";
   static final String PASS = "";
   
   public static Connection connect() {
        Connection conn = null;
        Statement stmt = null;
        
        try{
           //STEP 2: Register JDBC driver
     //      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
           Class.forName(JDBC_DRIVER).newInstance();

           //STEP 3: Open a connection
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           //STEP 4: Execute a query
           System.out.println("Creating database...");
           stmt = conn.createStatement();
           
           String sql = "CREATE TABLE IF NOT EXISTS `data` (\n" +
                "  `customerID` int(11) NOT NULL,\n" +
                "  `age` int(11) NOT NULL,\n" +
                "  `gender` varchar(20) NOT NULL,\n" +
                "  `transactID` varchar(100) NOT NULL,\n" +
                "  `transactDate` varchar(200) NOT NULL,\n" +
                "  `transactTime` varchar(200) NOT NULL,\n" +
                "  `outlet` varchar(100) NOT NULL,\n" +
                "  `outletDistrict` int(11) NOT NULL,\n" +
                "  `transactDetailsID` int(11) NOT NULL,\n" +
                "  `item` varchar(200) NOT NULL,\n" +
                "  `itemDesc` varchar(300) NOT NULL,\n" +
                "  `quantity` int(11) NOT NULL,\n" +
                "  `price` double NOT NULL,\n" +
                "  `spending` double NOT NULL\n" +
                ",\n" +
                "   PRIMARY KEY(customerID, transactID, transactDetailsID))";
           stmt.executeUpdate(sql);
           System.out.println("Table created successfully...");
           System.out.println("Connection established");
        }catch(SQLException se){
           //Handle errors for JDBC
           se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }
//        finally{
//           //finally block used to close resources
//
//           try{
//              if(conn!=null)
//                 conn.close();
//           }catch(SQLException se){
//              se.printStackTrace();
//           }//end finally try
//        }//end try
        System.out.println("Goodbye!");
        return conn;
    }//end 
}//end JDBCExample
