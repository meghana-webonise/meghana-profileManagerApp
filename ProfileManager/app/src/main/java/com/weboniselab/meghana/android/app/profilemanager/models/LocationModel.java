package com.weboniselab.meghana.android.app.profilemanager.models;

/**
 * Created by webonise on 29/9/15.
 */
public class LocationModel {
    int id;
    String modeOfPhone;
    double latitude,longitude;
    int radius;
    String address;

    public LocationModel(){
        this.id=0;
        this.modeOfPhone="";
        this.latitude=0.0;
        this.longitude=0.0;
        this.address="";
        this.radius=0;
    }

    public LocationModel(int id, double latitude,double longitude,int radius, String address,String modeOfPhone) {
        this.id = id;
        this.latitude=latitude;
        this.longitude=longitude;
        this.radius=radius;
        this.address=address;
        this.modeOfPhone = modeOfPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModeOfPhone() {
        return modeOfPhone;
    }

    public void setModeOfPhone(String modeOfPhone) {
        this.modeOfPhone = modeOfPhone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}