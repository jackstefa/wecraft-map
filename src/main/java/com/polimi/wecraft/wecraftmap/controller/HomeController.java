package com.polimi.wecraft.wecraftmap.controller;

import com.polimi.wecraft.wecraftmap.dao.ItemRepo;
import com.polimi.wecraft.wecraftmap.dao.PointRepo;
import com.polimi.wecraft.wecraftmap.service.MapPoint;
import com.polimi.wecraft.wecraftmap.service.MapPointBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    PointRepo pointRepo;

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    MapPointBuilderService service;


    @GetMapping("/getAllPoints")
    public ResponseEntity<List<MapPoint>> getAllPoints(){

            return service.getAllMapPoints();

    }
}
