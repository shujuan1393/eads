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

    private static final String SELECTUNIQUE = "SELECT DISTINCT customerid, item, itemdesc, count(*) FROM data GROUP BY customerid, item, itemdesc";
    private static final String INSERTSATISFACTION = "INSERT INTO satisfaction_values VALUES(?,?,?,?)";
    
    public static boolean loadSatisfactionValues() throws SQLException {
        Connection conn = DatabaseConnectionManager.connect();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECTUNIQUE);
            rs = pstmt.executeQuery();

            ArrayList<SatisfactionValues>  satisfactionValuesArrayList = new ArrayList<>();
            while (rs.next()) {
                int customerID= rs.getInt(1);
                String itemID = rs.getString(2);
                String itemdesc= rs.getString(3);
                int satisfactionValue = rs.getInt(4);
                satisfactionValuesArrayList.add(new SatisfactionValues(customerID, itemID, itemdesc, satisfactionValue));
            }

            conn.setAutoCommit(false);
            pstmt2 = conn.prepareStatement(INSERTSATISFACTION);

            for (SatisfactionValues satisfactionValues: satisfactionValuesArrayList) {
                pstmt2.setInt(1, satisfactionValues.getCust_id());
                pstmt2.setString(2, satisfactionValues.getItem_id());
                pstmt2.setString(3, satisfactionValues.getItem_desc());
                pstmt2.setDouble(4, satisfactionValues.getSatisfaction_value());
                pstmt2.addBatch();
            }
            pstmt2.executeBatch();
            conn.commit();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (pstmt2 !=null) {
                pstmt2.close();
            }
            if (rs != null) {
                rs.close();
            }
        }


        return true;
    }
}

