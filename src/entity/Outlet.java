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
public class Outlet {
    private String outlet;
    private int outletDistrict;
    private String region;

    public Outlet (String outlet, int outletDistrict, String region) {
        this.outlet = outlet;
        this.outletDistrict = outletDistrict;
        this.region = region;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Outlet{" + "outlet=" + outlet + ", outletDistrict=" + outletDistrict + ", region=" + region + '}';
    }
            
    
}
