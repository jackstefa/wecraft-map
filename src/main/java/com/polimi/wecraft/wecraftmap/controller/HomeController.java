package com.polimi.wecraft.wecraftmap.controller;

import com.polimi.wecraft.wecraftmap.model.body.FilterParams;
import com.polimi.wecraft.wecraftmap.model.response.ClientFilters;
import com.polimi.wecraft.wecraftmap.model.response.MapPoint;
import com.polimi.wecraft.wecraftmap.service.FiltersService;
import com.polimi.wecraft.wecraftmap.service.MapPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:63342"})
public class HomeController {

    @Autowired
    MapPointService mapService;

    @Autowired
    FiltersService filtersService;


    //get all artisans with relative information and items list
    @GetMapping("/getAllPoints")
    public ResponseEntity<HashMap<String, List<MapPoint>>> getAllPoints(){

            return mapService.getAllMapPoints();

    }

    //get all artisans with relative information and items list filtered
    @PostMapping(path = "/getFilteredPoints", consumes = "application/json")
    public ResponseEntity<HashMap<String, List<MapPoint>>> getPoints(@RequestBody FilterParams filterParams){

            return mapService.getFilteredPoints(filterParams);
    }

    //provides filter entries to the UI
    @GetMapping("/getFilters")
    public ResponseEntity<HashMap<String, ClientFilters>> getFilters(){

        return filtersService.getFilters();

    }
}
