/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import entity.SatisfactionValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author langxin
 */
public class LoadFinalSatisfaction {

    private static final String SELECTUNIQUEUSER = "SELECT DISTINCT cust_id FROM satisfaction_values";

    private static final String SELECTUSERCATEGORY = "SELECT s.cust_id, c.item_id, c.item_desc, s.satisfaction_value \n"+
            "FROM (SELECT * FROM satisfaction_values WHERE cust_id = ?) \n"+
            " AS s RIGHT JOIN category c ON s.item_id = c.item_id AND s.item_desc = c.item_desc";

    private static final String INSERTSATISFACTION = "INSERT INTO final_satisfaction VALUES(?,?,?,?)";
    
    public static boolean loadFinalSatisfaction() throws SQLException {
        Connection conn = DatabaseConnectionManager.connect();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            pstmt = conn.prepareStatement(SELECTUNIQUEUSER);
            rs = pstmt.executeQuery();
            ArrayList<Integer> customerList = new ArrayList<>();
            while (rs.next()) {
                int customerID = rs.getInt(1);
                customerList.add(customerID);
            }
            pstmt.close();
            rs.close();

            for (int custID : customerList) {
                System.out.println("Now processing customer : " + custID);
                pstmt2 = conn.prepareStatement(SELECTUSERCATEGORY);
                pstmt2.setInt(1,custID);
                rs2 = pstmt2.executeQuery();

                ArrayList<SatisfactionValues> satisfactionValuesArrayList = new ArrayList<>();
                while (rs2.next()) {
                    int customerID= rs2.getInt(1);
                    String itemID = rs2.getString(2);
                    String itemdesc= rs2.getString(3);
                    Integer satisfactionValue = rs2.getInt(4);

                    //System.out.println(customerID + ", " + itemID + ", " +itemdesc + ", " + satisfactionValue);
                    satisfactionValuesArrayList.add(new SatisfactionValues(custID, itemID, itemdesc, satisfactionValue));
                }

                conn.setAutoCommit(false);
                pstmt3 = conn.prepareStatement(INSERTSATISFACTION);

                for (SatisfactionValues satisfactionValues: satisfactionValuesArrayList) {
                    pstmt3.setInt(1, satisfactionValues.getCust_id());
                    pstmt3.setString(2, satisfactionValues.getItem_id());
                    pstmt3.setString(3, satisfactionValues.getItem_desc());
                    pstmt3.setDouble(4, satisfactionValues.getSatisfaction_value());
                    pstmt3.addBatch();
                }
                pstmt3.executeBatch();
                conn.commit();
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt2 != null) {
                pstmt2.close();
            }
            if (pstmt3 != null) {
                pstmt3.close();
            }
            if (rs2 != null) {
                rs2.close();
            }
        }


        return true;
    }
}

