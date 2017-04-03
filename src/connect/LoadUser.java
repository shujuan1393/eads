package connect;

import entity.Customer;
import entity.Outlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jing Xiang on 1/4/2017.
 */
public class LoadUser {

    private static final String SELECTUNIQUEUSERS = "SELECT DISTINCT customerid, age, gender, sum(spending), count(distinct transactid)" +
            "FROM data GROUP BY customerid, age, gender";
    private static final String INSERTUSERSQL = "INSERT INTO users VALUES(?,?,?,?,?)";

    public static boolean loadUser() {
        Connection conn = DatabaseConnectionManager.connect();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECTUNIQUEUSERS);
            rs = pstmt.executeQuery();

            ArrayList<Customer> customerList = new ArrayList<>();
            while (rs.next()) {
                int custoemrID = rs.getInt(1);
                int  age = rs.getInt(2);
                String gender = rs.getString(3);
                double spending = rs.getDouble(4);
                int totalTransaction = rs.getInt(5);
                customerList.add(new Customer(custoemrID, age, gender, spending, totalTransaction));
                //System.out.println(custoemrID + ", " + age + ", " + gender + ", " + spending + ", " + totalTransaction);
            }

            conn.setAutoCommit(false);
            pstmt2 = conn.prepareStatement(INSERTUSERSQL);

            for (Customer customer: customerList) {
                pstmt2.setInt(1, customer.getCustomerId());
                pstmt2.setInt(2, customer.getAge());
                pstmt2.setString(3, customer.getGender());
                pstmt2.setDouble(4, customer.getSpending());
                pstmt2.setInt(5, customer.getTotalTransaction());
                pstmt2.addBatch();
            }
            pstmt2.executeBatch();
            conn.commit();

        } catch (SQLException k) {
            k.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}
