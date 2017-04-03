package connect;

import entity.FoodCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jing Xiang on 1/4/2017.
 */
public class CreateTables {

    private static final String DATACREATE = "CREATE TABLE IF NOT EXISTS `data` (\n" +
            "  `customerid` int(11) NOT NULL,\n" +
            "  `age` int(11) NOT NULL,\n" +
            "  `gender` varchar(20) NOT NULL,\n" +
            "  `transactid` varchar(100) NOT NULL,\n" +
            "  `transactdate` varchar(200) NOT NULL,\n" +
            "  `transacttime` varchar(200) NOT NULL,\n" +
            "  `outlet` varchar(100) NOT NULL,\n" +
            "  `outletdistrict` int(11) NOT NULL,\n" +
            "  `transactdetailsid` int(11) NOT NULL,\n" +
            "  `item` varchar(200) NOT NULL,\n" +
            "  `itemdesc` varchar(300) NOT NULL,\n" +
            "  `quantity` int(11) NOT NULL,\n" +
            "  `price` double NOT NULL,\n" +
            "  `spending` double NOT NULL\n" +
            ",\n" +
            "   PRIMARY KEY(customerid, transactid, transactdetailsid))";

    private static final String USERCREATE = "CREATE TABLE IF NOT EXISTS `users` (\n" +
            "  `customerid` int(11) NOT NULL,\n" +
            "  `age` int(11) NOT NULL,\n" +
            "  `gender` varchar(20) NOT NULL,\n" +
            "  `spending` double NOT NULL,\n" +
            "  `totaltransaction` int NOT NULL,\n" +
            "   PRIMARY KEY(`customerid`))";

    private static final String OUTLETCREATE = "CREATE TABLE IF NOT EXISTS `outlets` (\n" +
            "  `outlet` varchar(30) NOT NULL,\n" +
            "  `outletdistrict` int(11) NOT NULL,\n" +
            "  `region` varchar(30) NOT NULL,\n" +
            "   PRIMARY KEY(`outlet`, `outletdistrict`))";


    private static final String FOODCATEGORYCREATE = "CREATE TABLE IF NOT EXISTS `category` (\n" +
            "  `item_id` varchar(200) NOT NULL,\n" +
            "  `item_desc` varchar(300) NOT NULL,\n" +
            "  `price` double NOT NULL,\n" +
            "  `course` varchar(10) NOT NULL,\n" +
            "  `origin` varchar(50) NOT NULL,\n" +
            "  `tags` varchar(255) NOT NULL,\n" +
            "  `hot_cold` varchar(10) NOT NULL,\n" +
            "  PRIMARY KEY(`item_id`, `item_desc`)\n" +
            ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";


    private static final String SATISFACTIONCREATE = "CREATE TABLE IF NOT EXISTS `satisfaction_values` (\n" +
            "  `cust_id` int(11) NOT NULL,\n" +
            "  `item_id` varchar(200) NOT NULL,\n" +
            "  `item_desc` varchar(300) NOT NULL,\n" +
            "  `satisfaction_value` double NOT NULL,\n" +
            "   PRIMARY KEY(cust_id, item_id, item_desc))";


    /*
    private static final String FINALSATISFACTIONCREATE = "CREATE TABLE IF NOT EXISTS `final_satisfaction` (\n" +
            "  `cust_id` int(11) NOT NULL,\n" +
            "  `item_id` varchar(200) NOT NULL,\n" +
            "  `item_desc` varchar(300) NOT NULL,\n" +
            "  `satisfaction_value` int(11) NOT NULL,\n" +
            "   PRIMARY KEY(cust_id, item_id, item_desc))";
    */


    private static final String FOODSIMILARITYCREATE = "CREATE TABLE IF NOT EXISTS `similarity` (\n" +
            "  `item_id` varchar(200) NOT NULL,\n" +
            "  `item_desc` varchar(300) NOT NULL,\n" +
            "  `item_id2` varchar(200) NOT NULL,\n" +
            "  `item_desc2` varchar(300) NOT NULL,\n" +
            "  `similarityval` double NOT NULL,\n" +
            "  PRIMARY KEY(`item_id`, `item_desc`,`item_id2`, `item_desc2`)\n" +
            ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";




    public static boolean createTables() {
        Connection conn = DatabaseConnectionManager.connect();
        PreparedStatement pstmt = null;

        //establish connection, sql, execute sql
        try {
            pstmt = conn.prepareStatement(DATACREATE);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(USERCREATE);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(OUTLETCREATE);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(FOODCATEGORYCREATE);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(SATISFACTIONCREATE);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(FOODSIMILARITYCREATE);
            pstmt.executeUpdate();

        } catch (SQLException k) {
            k.printStackTrace();
        }  finally {
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
        }

        return true;
    }

}
