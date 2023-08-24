package com.polimi.wecraft.wecraftmap.dao;

import com.polimi.wecraft.wecraftmap.model.Artisan;
import org.springframework.data.repository.CrudRepository;


public interface ArtisanRepo extends CrudRepository<Artisan, Long> {

    Artisan findById(long id);

}
