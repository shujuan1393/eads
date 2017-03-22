package connect;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AverageSpending{
    public static boolean updateUserAverageSpending(Connection conn) {
        try {
            // store how much each customer has spent
            HashMap<String, Double> customer_totalSpending = new HashMap<>();

            // store how many times each customer has spent
            HashMap<String, Integer> customer_numSpending = new HashMap<>();

            // batch mode:on
            conn.setAutoCommit(false);

            // sql update statement
            final String SqlUpdateStmt = "update users set spending=? " +
                                        "where customerid=?";

            //prepare statement
            PreparedStatement pstmt2 = conn.prepareStatement(SqlUpdateStmt);

            //select the data
            String query = "SELECT customerid, age, gender, spending" +
                          " FROM data ";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset and store spending and number of transactions for each user
            while (rs.next()) {
                String custId = rs.getString("customerid");
                double spending = rs.getDouble("spending");

//                System.out.println("Customerid: " + custId + ", spending: " + spending);

                // update hashmap with customer's total spending
                
                if(customer_totalSpending.get(custId) == null){
                    customer_totalSpending.put(custId, spending);
                    customer_numSpending.put(custId, 1);
                } else{
                    double totalSpending = customer_totalSpending.get(custId);
                    totalSpending += spending;
                    customer_totalSpending.put(custId, spending);
                    customer_numSpending.put(custId, customer_numSpending.get(custId)+1); //increase number of spendings by 1
                }
            }
            st.close();

            // iterate through the stored data and calculate average
            Set set = customer_totalSpending.keySet();
            Iterator iterator = set.iterator();
            pstmt2 = conn.prepareStatement(SqlUpdateStmt);

            while(iterator.hasNext()) {
                String custId = iterator.next().toString();

                //calculate average
                double avgSpending = (customer_totalSpending.get(custId) / customer_numSpending.get(custId));

                // set the prepared statement
                try {

                    pstmt2.setDouble(1, avgSpending);
                    pstmt2.setString(2, custId);

                    // add into the batch
                    pstmt2.addBatch();
                } catch (SQLException k) {
                    System.out.println("Cannot set the statement or cannot add to batch");
                    k.printStackTrace();
                }
            }

            try{
                // actually execute the batch updates
                pstmt2.executeBatch();
                conn.commit();
            } catch (SQLException k) {
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
            e.printStackTrace();
        }

        return true;
    }
}