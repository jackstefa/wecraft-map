package com.polimi.wecraft.wecraftmap.model.response;

import com.polimi.wecraft.wecraftmap.model.Category;

import java.util.ArrayList;

public class ClientFilters {

    private int priceMin;
    private int priceMax;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    private ArrayList<Category> categories;

    public int getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
    }


}
