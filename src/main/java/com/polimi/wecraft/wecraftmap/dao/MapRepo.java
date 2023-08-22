package com.polimi.wecraft.wecraftmap.dao;

import com.polimi.wecraft.wecraftmap.model.Point;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MapRepo extends CrudRepository<Point, Integer> {

    List<Point> findAll();
}
