/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;


/**
 *
 * @author smu
 */
public class LoadSpendingByOutlet {
    
    public static void loadSpendingByOutlet() {
        String sql = "SELECT outlet, outletdistrict, u.customerid, AVG(d.spending) as average, "
                + "d.transactid FROM data d INNER JOIN users u on d.customerid = u.customerid "
                + "GROUP BY d.transactid";
    }
    
}
