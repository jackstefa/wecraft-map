package com.polimi.wecraft.wecraftmap.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name = "artisanid")
    private int artisanid;

    @Column(name = "price")
    private int price;

    @Column(name = "category")
    private String category;

    @Column(name = "imageid")
    private int imageid;

    @Column(name = "informations")
    private String informations;

    @Column(name = "quantity")
    private int quantity;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getArtisanid() {
        return artisanid;
    }

    public void setArtisanid(int artisanid) {
        this.artisanid = artisanid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
