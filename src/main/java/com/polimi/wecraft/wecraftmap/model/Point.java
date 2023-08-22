package com.polimi.wecraft.wecraftmap.model;

import jakarta.persistence.*;

@Entity
@Table(name = "point")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "imageid")
    private int imageid;

    @Column(name = "informations")
    private String informations;

    @Column(name = "price")
    private int price;

    @Column(name = "sellerid")
    private int sellerid;

    @Column(name = "availableitems")
    private int availableitems;

    public Point(long id, double latitude, double longitude, int imageid, String informations, int price, int sellerid, int availableitems) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageid = imageid;
        this.informations = informations;
        this.price = price;
        this.sellerid = sellerid;
        this.availableitems = availableitems;
    }

    public Point() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getInformations() {
        return informations;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSellerid() {
        return sellerid;
    }

    public void setSellerid(int sellerid) {
        this.sellerid = sellerid;
    }

    public int getAvailableitems() {
        return availableitems;
    }

    public void setAvailableitems(int availableitems) {
        this.availableitems = availableitems;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", imageid=" + imageid +
                ", informations='" + informations + '\'' +
                ", price=" + price +
                ", sellerid=" + sellerid +
                ", availableitems=" + availableitems +
                '}';
    }
}
