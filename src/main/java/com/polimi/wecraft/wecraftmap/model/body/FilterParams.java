package com.polimi.wecraft.wecraftmap.model.body;

import java.util.ArrayList;
import java.util.List;

public class FilterParams {

    private int priceMin;

    private int priceMax;

    private long[] categories;

    public FilterParams(int priceMin, int priceMax, long[] categories) {
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

    public long[] getCategories() {
        return categories;
    }

    public void setCategories(long[] categories) {
        this.categories = categories;
    }
}
