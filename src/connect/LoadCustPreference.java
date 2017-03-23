package connect;

import entity.CustPreferenceWithTags;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


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
         "  `cust_id` int(11) NOT NULL,\n" +
         "  `item_id` varchar(10) NOT NULL,\n" +
         "  `item_desc` varchar(300) NOT NULL,\n" +
         "  `course` varchar(10) NOT NULL,\n" +
         "  `origin` varchar(50) NOT NULL,\n" +
         "  `tags` varchar(255) NOT NULL,\n" +
         "  `hot_cold` varchar(10) NOT NULL,\n" +
         "  `satisfaction_value` varchar(50) NOT NULL,\n"  +
                "   PRIMARY KEY(`cust_id`, `item_id`))";
    
    private static final String SQLINSERT = "Insert into custPreference VALUES(?,?,?,?,?,?,?,?)";
    
    public static boolean loadPreference(Connection conn) {
        ArrayList<CustPreferenceWithTags> custTagList = new ArrayList<>();
        
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
                            "ON satisfaction_values.item_id = category.item_id GROUP BY cust_id, item_id";
                            

          // create the java statement
          Statement st = conn.createStatement();

          // execute the query, and get a java resultset
          ResultSet rs = st.executeQuery(query);
          
          CustPreferenceWithTags custPref = null;
          // iterate through the java resultset
          while (rs.next()) {
            custPref = new CustPreferenceWithTags(0, "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL"); 
            custPref.setCust_id(rs.getInt("cust_id"));
            custPref.setItem_id(rs.getString("item_id"));
            custPref.setItem_desc(rs.getString("item_desc"));
            custPref.setCourse(rs.getString("course"));
            custPref.setOrigin(rs.getString("origin"));
            custPref.setTags(rs.getString("tags"));
            custPref.setHot_cold(rs.getString("hot_cold"));
            custPref.setSatisfaction_value(rs.getString("satisfaction_value"));
            custTagList.add(custPref);
          }
          st.close();
        }
        catch (Exception e)
        {
          System.err.println("Got an exception! ");
          System.err.println(e.getMessage());
          e.printStackTrace();
        }
        
        try {
            ResultSet rs = null;
                //establish connection, sql, execute sql
            try {
                //upload by batches
                conn.setAutoCommit(false);
                //total 556581
                pstmt2 = conn.prepareStatement(SQLINSERT);
                //loop through user list
                if (custTagList != null) {
                    for (CustPreferenceWithTags x : custTagList) {

                        pstmt2.setInt(1, x.getCust_id());
                        pstmt2.setString(2, x.getItem_id());
                        pstmt2.setString(3, x.getItem_desc());
                        pstmt2.setString(4, x.getCourse());
                        pstmt2.setString(5, x.getOrigin());
                        pstmt2.setString(6, x.getTags());
                        pstmt2.setString(7, x.getHot_cold());
                        pstmt2.setString(8, x.getSatisfaction_value());

                        pstmt2.addBatch();
                    }
                }
                //System.out.println(pstmt);
                pstmt2.executeBatch();
                conn.commit();
            } catch (SQLException k) {
                k.printStackTrace();
            }  finally {
//                if (conn != null) {
//                    connect.DatabaseConnectionManager.closeConnection(conn);
//                }
//                if (pstmt2 != null) {
//                    pstmt2.close();
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
