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
public class FoodCategory {
    private String item_id;
    private String itemDesc;
    private double price;
    private String course;
    private String origin;
    private String tags;
    private String hotcold;

    public FoodCategory(String item_id, String itemDesc, double price, String course, String origin, String tags, String hotcold) {
        this.item_id = item_id;
        this.itemDesc = itemDesc;
        this.price = price;
        this.course = course;
        this.origin = origin;
        this.tags = tags;
        this.hotcold = hotcold;
    }

    public FoodCategory(String item_id, String itemDesc, double price) {
        this.item_id = item_id;
        this.itemDesc = itemDesc;
        this.price = price;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getHotcold() {
        return hotcold;
    }

    public void setHotcold(String hotcold) {
        this.hotcold = hotcold;
    }
          
    
    
      
}
