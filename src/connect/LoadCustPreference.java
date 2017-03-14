package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User 2
 */
public class LoadCustPreference {

    private static final String SQLCREATE = "CREATE TABLE IF NOT EXISTS `custPreference` (\n" +
         "  `cust_id` varchar(10) NOT NULL,\n" +
         "  `item_id` varchar(10) NOT NULL,\n" +
         "  `item_desc` varchar(300) NOT NULL,\n" +
         "  `course` varchar(10) NOT NULL,\n" +
         "  `origin` varchar(50) NOT NULL,\n" +
         "  `tags` varchar(255) NOT NULL,\n" +
         "  `hot_cold` varchar(10) NOT NULL,\n" +
         "  `satisfaction_value` varchar(50) NOT NULL,\n"  +
                "   PRIMARY KEY(`cust_id`, `item_id`))";
    
    private static final String SQLINSERT = "Insert into custPreference VALUES(?,?,?,?,?,?,?,?)";
    
    public boolean loadPreference(Connection conn) {
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null; //for inserting into customer preference table
        
        try {
          // create our mysql database connection
//          String myDriver = "org.gjt.mm.mysql.Driver";
//          String myUrl = "jdbc:mysql://localhost/eads";
//          Class.forName(myDriver);
//          Connection conn = DriverManager.getConnection(myUrl, "root", "");

          //satisfaction_values table left join category table
            pstmt = conn.prepareStatement(SQLCREATE);
            pstmt.executeUpdate();
            
          String query = "SELECT satisfaction_values.cust_id, satisfaction_values.item_id, category.item_desc, category.course, category.origin, category.tags, " +
                            "category.hot_cold, satisfaction_values.satisfaction_value " +
                            "FROM satisfaction_values LEFT JOIN category " +
                            "ON satisfaction_values.item_id = category.item_id";
                            

          // create the java statement
          Statement st = conn.createStatement();

          // execute the query, and get a java resultset
          ResultSet rs = st.executeQuery(query);

          // iterate through the java resultset
          while (rs.next())
          {
            String custId = rs.getString("cust_id");
            String itemId = rs.getString("item_id");
            String itemDesc = rs.getString("item_desc");
            String course = rs.getString("course");
            String origin = rs.getString("origin");
            String tags = rs.getString("tags");
            String hot_cold = rs.getString("hot_cold");
            String satisfactionValue = rs.getString("satisfaction_value");
            
            System.out.println("Customer ID: " + custId + ", Item ID: " + itemId + ", Item Description: " + itemDesc + ", Course: " + course + ", Origin: " + origin
                                + ", Tags: " + tags + ", Hot/Cold? : " + hot_cold + ", Satisfaction Value: " + satisfactionValue);
            
            //WHATEVER WE WANNA DO WITH THIS COMBINED DATA
          
            
            
          }
          st.close();
        }
        catch (Exception e)
        {
          System.err.println("Got an exception! ");
          System.err.println(e.getMessage());
        }

        return true;
    }
}
