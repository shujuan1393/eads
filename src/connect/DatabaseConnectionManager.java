package esas.utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnectionManager {

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    /**
     * Contains the username for database
     */
    private static String dbUser = "root";
    /**
     * Contains the password for database
     */
    private static String dbPass = "";
    /**
     * Contains the name of database
     */
    private static String dbName ="eads";
    /**
     * Contains the url of database
     */
    private static String dbUrl = "";
    /**
     * Contains the host of database
     */
    private static String dbHost = "localhost";
    /**
     * Contains the port of database
     */
    private static String dbPort = "3306";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        dbUrl = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        System.out.println("dbUrl: " + dbUrl);
       
        try {
            Class.forName(DRIVER_NAME).newInstance();
        } catch (Exception e) {
            System.out.println("*** DATABASE Connection Error from DatabaseConnectionManager.java ***");
            System.out.println(e.getMessage());
        }

    }

    /**
     * Get connection from DriverManager based on the dbUrl, dbUser and dbPass
     *
     * @return Database Connection Object for using
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }

    /**
     * Close the specified connection
     *
     * @param conn the Connection Object to be closed
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                // System.out.println("*** Connection closed ***");
            }
        } catch (SQLException e) {
            //System.out.println("Fail to close");
        }
    }
}
