
import static connect.Bootstrap.*;
import static connect.DatabaseConnectionManager.*;
import connect.DisplayCustPreference;
import connect.LoadCustPreference;
import connect.LoadSatisfactionValues;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

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
        Scanner sc = null;
        
        try {
          //Get user input for Member ID and no. of pax
          sc = new Scanner(System.in);
          // input member id
          System.out.print("Please input member id: ");
          int memberId = sc.nextInt();
          System.out.println();
          
          // select # pax
          System.out.print("Please input number of pax: ");
          int paxNum = sc.nextInt();

          //Display top X recommendations based on time of the day (snacks/main course), X = num of pax
          DisplayCustPreference displayPreference = new DisplayCustPreference();
          boolean display = displayPreference.displayPreference(conn, memberId, paxNum);
          
          //to load database
//        boolean success = bootstrap(conn);
//        if (success) {
//            LoadSatisfactionValues satisfactionV = new LoadSatisfactionValues();
//            boolean satLoad = satisfactionV.loadSatisfactionValues(conn);

//            LoadCustPreference cust = new LoadCustPreference();
//            boolean prefLoad = cust.loadPreference(conn);

//            if (!prefLoad || !satLoad) {
//                System.out.println("Error");
//            }
//        }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }
}
