/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author langxin
 */
public class SatisfactionValues {
    private String cust_id;
    private String item_id;
    private String satisfaction_value;

    public SatisfactionValues(String cust_id, String item_id, String item_desc, String satisfaction_value) {
        this.cust_id = cust_id;
        this.item_id = item_id;
        this.satisfaction_value = satisfaction_value;
    }

    public String getCust_id() {
        return cust_id;
    }
    
    public String getItem_id() {
        return item_id;
    }
    
    public String getSatisfaction_value() {
        return satisfaction_value;
    }
    
}
