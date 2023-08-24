package com.polimi.wecraft.wecraftmap.dao;

import com.polimi.wecraft.wecraftmap.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepo extends CrudRepository<Item, Long> {


    Item findById(long id);
    Item findByCategory(String category);

    List<Item> findByCategoryAndPriceBetween(String category, int priceMin, int priceMax);

    Item findByPriceBetween(int priceMin, int priceMax);

}
