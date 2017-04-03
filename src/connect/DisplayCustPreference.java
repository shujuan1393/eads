/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import entity.CustPreferenceWithTags;
import entity.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author langxin
 */
public class DisplayCustPreference {
    public HashMap<String, String> displayPreference(Connection conn, int inputId, int numPax) throws SQLException {
        ArrayList<CustPreferenceWithTags> list = null;
        HashMap<String, String> toReturn = new HashMap<>();
        
        try {
            // our SQ
            // L SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT c.item_id, c.item_desc, c.course, c.origin, c.tags, c.hot_cold, "
                    + "s.satisfaction_value, d.customerid, d.transactid FROM category c inner join "
                    + "satisfaction_values s inner join data d on s.item_id = c.item_id and "
                    + "d.customerid = s.cust_id where s.cust_id = '" + inputId + "' GROUP BY c.item_id ORDER BY "
                    + "s.satisfaction_value desc";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            
            CustPreferenceWithTags c = new CustPreferenceWithTags(0,"NULL","NULL", "NULL", "NULL", "NULL", "NULL", "NULL");
            list = new ArrayList<>();
            int id = 0;
            while (rs.next()) {
                c.setCust_id(id);
                c.setItem_id(rs.getString("item_id"));
                c.setItem_desc(rs.getString("item_desc"));
                c.setCourse(rs.getString("course"));
                c.setOrigin(rs.getString("origin"));
                c.setTags(rs.getString("tags"));
                c.setHot_cold(rs.getString("hot_cold"));
                c.setSatisfaction_value(rs.getString("satisfaction_value"));
                list.add(c);
                c= new CustPreferenceWithTags(0,"NULL","NULL", "NULL", "NULL", "NULL", "NULL", "NULL");
            }
            
            // get time of day
            // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("HH");
            java.util.Date date = new java.util.Date();
            int hour = Integer.parseInt(dateFormat.format(date));
            
//            System.out.println("list size:" + list.size());
            
            for (CustPreferenceWithTags x: list) {
//                System.out.println(x.toString());
                String course = x.getCourse();
                //exclude 'drinks' first
                if (course.equals("snacks") || course.equals("side") || course.equals("dessert")) {
                    if(hour >= 15 && hour <= 18 || hour >= 20 && hour <= 22){
                        //Recommend snacks/side/dessert
                        toReturn.put(x.getItem_id(), x.getItem_desc());
                    } 
//                    teaTimeList.put(x.getItem_desc(), Integer.parseInt(x.getSatisfaction_value()));
                } else if (course.equals("main")) {
                    toReturn.put(x.getItem_id(), x.getItem_desc());
                }
            }
//            System.out.println("toReturn: "+toReturn.size());
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return toReturn;
    }

}
