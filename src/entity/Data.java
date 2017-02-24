package entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User 2
 */
public class Data {
    private int customerId;
    private int age;
    private String gender;
    private int transactId;
    private String transactDate;
    private String transactTime;
    private String outlet;
    private int outletDistrict;
    private int transactDetailsId;
    private String item;
    private String itemDesc;
    private int quantity;
    private double price;
    private double spending;

    public Data(int customerId, int age, String gender, int transactId, String transactDate, String transactTime, String outlet, int outletDistrict, int transactDetailsId, String item, String itemDesc, int quantity, double price, double spending) {
        this.customerId = customerId;
        this.age = age;
        this.gender = gender;
        this.transactId = transactId;
        this.transactDate = transactDate;
        this.transactTime = transactTime;
        this.outlet = outlet;
        this.outletDistrict = outletDistrict;
        this.transactDetailsId = transactDetailsId;
        this.item = item;
        this.itemDesc = itemDesc;
        this.quantity = quantity;
        this.price = price;
        this.spending = spending;
    }
            
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getTransactId() {
        return transactId;
    }

    public void setTransactId(int transactId) {
        this.transactId = transactId;
    }

    public String getTransactDate() {
        return transactDate;
    }

    public void setTransactDate(String transactDate) {
        this.transactDate = transactDate;
    }

    public String getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(String transactTime) {
        this.transactTime = transactTime;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public int getOutletDistrict() {
        return outletDistrict;
    }

    public void setOutletDistrict(int outletDistrict) {
        this.outletDistrict = outletDistrict;
    }

    public int getTransactDetailsId() {
        return transactDetailsId;
    }

    public void setTransactDetailsId(int transactDetailsId) {
        this.transactDetailsId = transactDetailsId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSpending() {
        return spending;
    }

    public void setSpending(double spending) {
        this.spending = spending;
    }
    
           
    
      
}
