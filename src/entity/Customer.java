/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author smu
 */
public class Customer {
    private int customerId;
    private int age;
    private String gender;
    private double spending;
    
    public Customer(int customerId, int age, String gender, double spending) {
        this.customerId = customerId;
        this.age = age;
        this.gender = gender;
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

    public double getSpending() {
        return spending;
    }

    public void setSpending(double spending) {
        this.spending = spending;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", age=" + age + ", gender=" + gender + ", spending=" + spending + '}';
    }
    
    
}
