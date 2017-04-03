package connect;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateCustomerOutletCount {

    private static final String SQLCREATE = "CREATE TABLE IF NOT EXISTS `outletcount` (\n"
            + "  `customerid` int(11) NOT NULL,\n"
            + "  `outlet` varchar(200) NOT NULL,\n"
            + "  `count` int(11) NOT NULL, \n" +
                "   PRIMARY KEY (customerid, outlet)"
            + ")";

    public static boolean updateCustomerOutletCount() {
        Connection conn = DatabaseConnectionManager.connect();

        try {
            // store customer ID, hashmap of outlet id and visit count
            HashMap<Integer, HashMap<String, Integer>> customerOutletCount = new HashMap<>();
            HashMap<String, Integer> outletCount = new HashMap<>();
            
            //create table
            PreparedStatement pstmt = null;
            pstmt = conn.prepareStatement(SQLCREATE);
            pstmt.executeUpdate();
            
            // batch mode:on
            conn.setAutoCommit(false);
            //String backup = "Select customerid , count(outlet) , outlet from ((SELECT distinct transacid,outlet , customerid  from data )as T)";
            // sql update statement
            final String SqlUpdateStmt = "insert into outletcount values( ? , ? ,?)";
            //prepare statement
            PreparedStatement pstmt2 = conn.prepareStatement(SqlUpdateStmt);

            //select the data
            String query = " SELECT distinct transactid,outlet , customerid  from data order by customerid";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset and store number of visits to each outlet
            int currentCustId = 0;
            String currentOutlet = "";
            int currentOutletCount = 0;
            while (rs.next()) {

                int custId = rs.getInt("customerid");
                String outlet = rs.getString("outlet");

                if (custId == currentCustId) { //same customer

                    if (outlet.equals(currentOutlet)) { //same outlet
                        currentOutletCount++;
                    } else { //different outlet
                        outletCount.put(currentOutlet, currentOutletCount);
                        currentOutletCount = 1;
                        currentOutlet = outlet;
                    }
                } else { //different customer
                    if (currentCustId != 0) {
                        outletCount.put(currentOutlet, currentOutletCount);
                        customerOutletCount.put(currentCustId, outletCount); //put everything from previous cus into hashmap
                        outletCount = new HashMap<>();
                    }
                    currentCustId = custId;
                    currentOutlet = outlet;
                    currentOutletCount = 1;
                    //outletCount.put(currentOutlet, currentOutletCount);
                }

            }
            st.close();

            // iterate through the stored data and calculate average
            Set set = customerOutletCount.keySet();
            Iterator iterator = set.iterator();
            pstmt2 = conn.prepareStatement(SqlUpdateStmt);
            while (iterator.hasNext()) {

                int custId = (int) iterator.next();
                HashMap<String, Integer> map = customerOutletCount.get(custId);
                Set set2 = map.keySet();
                Iterator iterator2 = set2.iterator();
                while (iterator2.hasNext()) {
                    String outlet = iterator2.next().toString();
                    int count = map.get(outlet);

                    // set the prepared statement
                    try {

                        pstmt2.setInt(1, custId);
                        pstmt2.setString(2, outlet);
                        pstmt2.setInt(3, count);

                        // add into the batch
                        pstmt2.addBatch();
                    } catch (SQLException k) {
                        System.out.println("Cannot set the statement or cannot add to batch");
                        k.printStackTrace();
                    }

                }

            }

            try {
                // actually execute the batch updates
                pstmt2.executeBatch();
                conn.commit();
            } catch (Exception k) {
                System.out.println("Cannot execute the batch or commit");
                k.printStackTrace();
            } finally {
                if (conn != null) {
                    // connect.DatabaseConnectionManager.closeConnection(conn);
                }
                if (pstmt2 != null) {
                    pstmt2.close();
                }
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return true;
    }
}
