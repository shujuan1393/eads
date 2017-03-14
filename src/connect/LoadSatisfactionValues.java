/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import entity.Data;
import com.monitorjbl.xlsx.StreamingReader;
import entity.FoodCategory;
import entity.SatisfactionValues;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.sql.*;
import java.util.HashMap;

/**
 *
 * @author langxin
 */
public class LoadSatisfactionValues {
    private static final String SQLCREATE = "CREATE TABLE IF NOT EXISTS `satisfaction_values` (\n" +
                "  `cust_id` int(11) NOT NULL,\n" +
                "  `item_id` varchar(10) NOT NULL,\n" +
                "  `satisfaction_value` varchar(50) NOT NULL,\n" +
                "   PRIMARY KEY(cust_id, item_id))";
    
    private static final String SQLINSERT = "Insert into satisfaction_values VALUES(?,?,?)";
    
    public boolean loadSatisfactionValues(Connection conn) throws SQLException {
        ArrayList<Data> dataList = null;
                
        try {
//             // create our mysql database connection
//            String myDriver = "org.gjt.mm.mysql.Driver";
//            String myUrl = "jdbc:mysql://localhost:8889/eads";
//            Class.forName(myDriver);
//            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            // our SQL SELECT query. 
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT * FROM data";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            
            Data d = null;
            dataList = new ArrayList<Data>();
      
            while (rs.next()) {
                d.setCustomerId(rs.getInt("customerid"));
                d.setAge(rs.getInt("age"));
                d.setGender(rs.getString("gender"));
                d.setTransactId(rs.getInt("transactid"));
                d.setTransactDate(rs.getString("transactdate"));
                d.setTransactTime(rs.getString("transacttime"));
                d.setOutlet(rs.getString("outlet"));
                d.setOutletDistrict(rs.getInt("outletdistrict"));
                d.setTransactDetailsId(rs.getInt("transactdetailsid"));
                d.setItem(rs.getString("item"));
                d.setItemDesc(rs.getString("itemdesc"));
                d.setQuantity(rs.getInt("quantity"));
                d.setPrice(rs.getDouble("price"));
                d.setSpending(rs.getDouble("spending"));
                System.out.println(d.toString());
                dataList.add(d);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
//        System.out.println(dataList.size());
        //ArrayList of Arraylist [(custID1, itemID1, satisfactionValue1), (custID1, itemID1, satisfactionValue1)]
        //For each custID, there may be different itemIDs
        //SatisfactionValue is calculated by taking qty of item ordered (so its not just +1)
        ArrayList<ArrayList<String>> outer = new ArrayList<ArrayList<String>>();
        ArrayList<String> inner = new ArrayList<String>();
        
        for (int i=0; i< dataList.size();i++) {
            Data d = dataList.get(i);
            String custId = Integer.toString(d.getCustomerId());
            String itemId = d.getItem();
            String qty = Integer.toString(d.getQuantity());
            
            //Check if custId and itemId already exists
            for (ArrayList<String> list : outer) {
                //If custId & itemId already exists, update satisfactionValue
                if (list.contains(custId) & list.contains(itemId)) {
                    String q = list.get(2);
                    q = q + qty;
                    list.add(2, q);
                } else {
                    //add values into inner arraylist (custID, itemID, satisfactionValue)
                    inner.add(custId);
                    inner.add(itemId);
                    inner.add(qty);
                    break;
                }
            }
        }
        
        //create satisfactionTable in db and add custId, itemId and satisfactionValues
        try {
            PreparedStatement pstmt = null;
            PreparedStatement pstmt2 = null;
            ResultSet rs = null;
            
                //establish connection, sql, execute sql
            try {
//                conn = connect.DatabaseConnectionManager.getConnection();
                pstmt = conn.prepareStatement(SQLCREATE);
                pstmt.executeUpdate();
              
                //upload by batches
                conn.setAutoCommit(false);
                //total 556581
                pstmt2 = conn.prepareStatement(SQLINSERT);
                //loop through user list
                for (ArrayList<String> x : outer) {
                    pstmt2.setString(1, x.get(0));
                    pstmt2.setString(2, x.get(1));
                    pstmt2.setString(3, x.get(2));
                    pstmt2.addBatch();
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

