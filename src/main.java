
import static connect.Bootstrap.*;
import static connect.DatabaseConnectionManager.*;
import connect.LoadCustPreference;
import connect.LoadSatisfactionValues;
import java.sql.Connection;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author smu
 */


public class main {
    public static void main(String[] args) throws SQLException {
        
        Connection conn = connect();
//        boolean success = bootstrap(conn);
        
//        if (success) {
            LoadSatisfactionValues satisfactionV = new LoadSatisfactionValues();
            boolean satLoad = satisfactionV.loadSatisfactionValues(conn);

//            LoadCustPreference cust = new LoadCustPreference();
//            boolean prefLoad = cust.loadPreference(conn);

//            if (!prefLoad || !satLoad) {
//                System.out.println("Error");
//            }
//        }
    }
}
