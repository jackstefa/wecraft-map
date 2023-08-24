package com.polimi.wecraft.wecraftmap.service;

import java.util.ArrayList;
import java.util.List;

public class FilterParams {

    private int priceMin;

    private int priceMax;

    private ArrayList<String> categories;

    public FilterParams(int priceMin, int priceMax, ArrayList<String> categories) {
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.categories = categories;
    }


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

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }
}
