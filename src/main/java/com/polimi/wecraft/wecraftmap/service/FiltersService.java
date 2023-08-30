package com.polimi.wecraft.wecraftmap.service;

import com.polimi.wecraft.wecraftmap.dao.CategoryRepo;
import com.polimi.wecraft.wecraftmap.dao.ItemRepo;
import com.polimi.wecraft.wecraftmap.model.Category;
import com.polimi.wecraft.wecraftmap.model.response.ClientFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class FiltersService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ItemRepo itemRepo;

    //build a response object to provide filter entries to the UI
    public ResponseEntity<HashMap<String, ClientFilters>> getFilters(){

        try{

            ClientFilters clientFilters = new ClientFilters();

            //querying database
            ArrayList<Category> categories= (ArrayList<Category>) categoryRepo.findAll();
            clientFilters.setCategories(categories);

            clientFilters.setPriceMin(itemRepo.minPrice());
            clientFilters.setPriceMax(itemRepo.maxPrice());

            HashMap<String, ClientFilters> response = new HashMap<>();
            response.put("filters", clientFilters);

            return new ResponseEntity<>(response, HttpStatus.OK);


        }  catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
