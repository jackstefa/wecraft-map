package com.polimi.wecraft.wecraftmap.dao;

import com.polimi.wecraft.wecraftmap.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepo extends CrudRepository<Item, Long> {


    List<Item> findByArtisanid(long artisanid);

    List<Item> findByArtisanidAndCategoryidAndPriceBetween(long artisanid, long categoryid, int priceMin, int priceMax);

    @Query("SELECT MAX(price) FROM Item")
    int maxPrice();

    @Query("SELECT MIN(price) FROM Item")
    int minPrice();

}
