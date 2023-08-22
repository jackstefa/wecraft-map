package com.polimi.wecraft.wecraftmap.controller;

import com.polimi.wecraft.wecraftmap.dao.MapRepo;
import com.polimi.wecraft.wecraftmap.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MapController {

    @Autowired
    MapRepo repo;


    @RequestMapping("/getPoints")
    @ResponseBody
    public List<Point> getPoints(){

        return repo.findAll();

    }
}
