package com.polimi.wecraft.wecraftmap.controller;

import com.polimi.wecraft.wecraftmap.dao.ItemRepo;
import com.polimi.wecraft.wecraftmap.dao.PointRepo;
import com.polimi.wecraft.wecraftmap.service.FilterParams;
import com.polimi.wecraft.wecraftmap.service.MapPoint;
import com.polimi.wecraft.wecraftmap.service.MapPointBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:63342"})
public class HomeController {

    @Autowired
    PointRepo pointRepo;

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    MapPointBuilderService service;


    @GetMapping("/getAllPoints")
    public ResponseEntity<HashMap<String, List<MapPoint>>> getAllPoints(){


            return service.getAllMapPoints();

    }

    @PostMapping(path = "/getPoints", consumes = "application/json")
    public ResponseEntity<HashMap<String, List<MapPoint>>> getPoints(@RequestBody FilterParams filterParams){

            return service.getFilteredPoints(filterParams);
    }
}
