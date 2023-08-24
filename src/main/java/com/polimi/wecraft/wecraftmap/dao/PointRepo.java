package com.polimi.wecraft.wecraftmap.dao;

import com.polimi.wecraft.wecraftmap.model.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointRepo extends CrudRepository<Point, Long> {

    Point findByItemid(long itemid);
}
