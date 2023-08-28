package com.polimi.wecraft.wecraftmap.dao;

import com.polimi.wecraft.wecraftmap.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepo extends CrudRepository<Category, Long> {

    @Query("SELECT c.name FROM Category c WHERE c.id = :id")
    String findNameById(@Param("id") long id);

}
