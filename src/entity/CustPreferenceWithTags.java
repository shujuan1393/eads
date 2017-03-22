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
public class CustPreferenceWithTags {
    private int cust_id;
    private String item_id;
    private String item_desc;
    private String course;
    private String origin;
    private String tags;
    private String hot_cold;
    private String satisfaction_value;
    
    public CustPreferenceWithTags(int cust_id, String item_id, String item_desc, String course, String origin, String tags, String hot_cold, String satisfaction_value) {
        this.cust_id = cust_id;
        this.item_id = item_id;
        this.item_desc = item_desc;
        this.course = course;
        this.origin = origin;
        this.tags = tags;
        this.hot_cold = hot_cold;
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
    
    public String getHot_cold() {
        return hot_cold;
    }
    
    public void setHot_cold(String hot_cold) {
        this.hot_cold = hot_cold;
    }
    
    public String getSatisfaction_value() {
        return satisfaction_value;
    }
    
    public void setSatisfaction_value(String satisfaction_value) {
        this.satisfaction_value = satisfaction_value;
    }

    @Override
    public String toString() {
        return "CustPreferenceWithTags{" + "cust_id=" + cust_id + ", item_id=" + item_id + ", item_desc=" + item_desc + ", course=" + course + ", origin=" + origin + ", tags=" + tags + ", hot_cold=" + hot_cold + ", satisfaction_value=" + satisfaction_value + '}';
    }
}
