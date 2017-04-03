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
    private int cust_id;
    private String item_id;
    private String item_desc;
    private double satisfaction_value;

    public SatisfactionValues(int cust_id, String item_id, String item_desc, double satisfaction_value) {
        this.cust_id = cust_id;
        this.item_id = item_id;
        this.item_desc = item_desc;
        this.satisfaction_value = satisfaction_value;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public double getSatisfaction_value() {
        return satisfaction_value;
    }

    public void setSatisfaction_value(double satisfaction_value) {
        this.satisfaction_value = satisfaction_value;
    }
}
