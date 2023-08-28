package com.polimi.wecraft.wecraftmap.model.response;


import java.util.ArrayList;

public class MapPoint {

    private double latitude;
    private double longitude;
    private String artisanName;
    private String email;
    private String phoneNumber;
    private ArrayList<ClientItem> itemsList;


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

    public String getArtisanName() {
        return artisanName;
    }

    public void setArtisanName(String artisanName) {
        this.artisanName = artisanName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<ClientItem> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ArrayList<ClientItem> itemsList) {
        this.itemsList = itemsList;
    }
}
