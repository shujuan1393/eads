
import static connect.AverageSpending.updateUserAverageSpending;
import static connect.Bootstrap.*;
import static connect.DatabaseConnectionManager.*;

import connect.*;

import entity.FoodCategory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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

        if (CreateTables.createTables()) {
            //to load database
            //boolean success = bootstrap();
            //LoadOutlet.loadOutlet();
            //LoadUser.loadUser();
            //LoadCategory.loadCategory();
            //LoadSatisfactionValues.loadSatisfactionValues();
            //LoadSimilarity.loadSimilarity();

            Scanner sc = new Scanner(System.in);
            // input member id

            System.out.print("Please input member id: ");
            int memberId = sc.nextInt();
            LoadSimilarity.processSimilarity(memberId);
        }

//        if (success) {
//            boolean catLoad = loadCategory(conn);    
            //boolean updateCustCount = UpdateCustomerOutletCount.updateCustomerOutletCount();
//             boolean prefLoad = loadPreference(conn);
//            updateUserAverageSpending(conn);
//            if (!prefLoad || !satLoad) {
//                System.out.println("Error");
//            }
//        }
//
        /*
        Scanner sc = null;
        HashMap<String, String> mainOrders = null;
        ArrayList<FoodCategory> drinkOrders = null;
        ArrayList<FoodCategory> dessertOrders = null;
        
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

            int orderNo = 0;

            while (orderNo != 4) {
                printMenu();
                System.out.print("Enter your choice: ");
                orderNo = sc.nextInt();
                if (orderNo == 1) {
                    mainOrders = orderMains(conn, memberId, paxNum, sc);
                } else if (orderNo == 2) {
                    drinkOrders = orderDrinksDesserts(paxNum, sc, "drinks");
                } else if (orderNo == 3) {
                    dessertOrders = orderDrinksDesserts(paxNum, sc, "desserts");
                } else if (orderNo == 4) {
                    endOrder(mainOrders, drinkOrders, dessertOrders);
                }
            }

        } finally {
            if (sc != null) {
                sc.close();
            }
        }*/
    }
    
    public static void printMenu() {
        System.out.println("\nWhat would you like to order now?");
        System.out.println("1. Mains");
        System.out.println("2. Drinks");
        System.out.println("3. Desserts");
        System.out.println("4. End Order");
    }
    
    public static HashMap<String,String> orderMains(Connection conn, int memberId, int paxNum, Scanner sc) throws SQLException {
        HashMap<String, String> orders = new HashMap<>();
        //Display top X recommendations based on time of the day (snacks/main course), X = num of pax
          DisplayCustPreference displayPreference = new DisplayCustPreference();
          HashMap<String, String> recommend = displayPreference.displayPreference(conn, memberId, paxNum);
//         System.out.println(recommend.size());
          if (recommend != null && recommend.size() > 0) {
                ArrayList<String> itemIds = new ArrayList<>();
                ArrayList<String> itemDescs = new ArrayList<>();
                
                Set set = recommend.entrySet();
                Iterator iterator = set.iterator();
                int count = 0;
                System.out.println("\nTop "+paxNum+ " Recommendations");
                while(iterator.hasNext()) {
                    Map.Entry me2 = (Map.Entry)iterator.next();
                    System.out.println(""+ (count+1) + ". " + me2.getValue());
                    itemIds.add(""+me2.getKey());
                    itemDescs.add(""+me2.getValue());
                    count++;
                }
                
                int choice = 0;
                while (orders.size() < paxNum) {
                    System.out.print("\nEnter your choice: ");
                    choice = sc.nextInt();
                    
                    if (choice <= itemIds.size()) {
                        orders.put(itemIds.get(choice-1), itemDescs.get(choice-1));
                    }
                    
                    if (orders.size() == paxNum) {
                        break;
                    }
                }
          }
          
          return orders;
    }
    
    public static ArrayList<FoodCategory> orderDrinksDesserts(int paxNum, Scanner sc, String type) {
        ArrayList<FoodCategory> orders = new ArrayList<>();
        ArrayList<FoodCategory> drinkDesRecom = RecommendDrinkDessertFromTemp.recommendDrinkDessertFromTemp(type);
        
        if (drinkDesRecom != null) {
            System.out.println("Recommended " + type);
            for (int i = 0; i < drinkDesRecom.size(); i++) {
                FoodCategory cat = drinkDesRecom.get(i);
                System.out.println((i+1)+". "+cat.getItemDesc());
            }
            
            if (drinkDesRecom.size() > 0) {
                int choice = 0;
                while (orders.size() < paxNum) {
                    System.out.print("\nEnter your choice: ");
                    choice = sc.nextInt();

                    if (choice <= drinkDesRecom.size()) {
                        orders.add(drinkDesRecom.get(choice-1));
                    }

                    if (orders.size() == paxNum) {
                        break;
                    }
                }
            } else {
                System.out.println("Sorry, no "+ type + " recommendations available at the moment!");
            }
            
        }
        
        return orders;
    }
    
    public static void endOrder(HashMap<String, String> mains, ArrayList<FoodCategory> drinks, ArrayList<FoodCategory> desserts) {
        System.out.println("You have ordered: ");
        System.out.println();
        
        if (mains != null) {
            Set set2 = mains.entrySet();
            Iterator iter = set2.iterator();
            System.out.println("Main Courses");
            while (iter.hasNext()) {
                Map.Entry me = (Map.Entry)iter.next();
                System.out.println("" + me.getValue());
            }
            System.out.println();
        }
        if (drinks.size() > 0) {
            System.out.println("Drinks");
            for (FoodCategory f: drinks) {
                System.out.println(f.getItemDesc());
            }
        }
        System.out.println();
        
        if (desserts != null && desserts.size() > 0) {
            System.out.println("Desserts");
            for (FoodCategory f: desserts) {
                System.out.println(f.getItemDesc());
            }
            System.out.println();
        }
        
        System.out.println("Thank you for ordering with us!");
    }
}
