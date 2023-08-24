package com.polimi.wecraft.wecraftmap.service;

import com.polimi.wecraft.wecraftmap.dao.ArtisanRepo;
import com.polimi.wecraft.wecraftmap.dao.ItemRepo;
import com.polimi.wecraft.wecraftmap.dao.PointRepo;
import com.polimi.wecraft.wecraftmap.model.Artisan;
import com.polimi.wecraft.wecraftmap.model.Item;
import com.polimi.wecraft.wecraftmap.model.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MapPointBuilderService {

    @Autowired
    PointRepo pointRepo;

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    ArtisanRepo artisanRepo;


    public ResponseEntity<List<MapPoint>> getAllMapPoints(){

        try{
            List<MapPoint> mapPoints = new ArrayList<MapPoint>();

            //TEST
            int cnt=0;

            List<Point> points = (List<Point>) pointRepo.findAll();

            if(points.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            for(Point point : points){
                //TEST
                cnt++;

                MapPoint mapPoint = new MapPoint();

                mapPoint.setLongitude(point.getLongitude());
                mapPoint.setLatitude(point.getLatitude());

                Item item = itemRepo.findById(point.getItemid());

                mapPoint.setItemName(item.getName());
                mapPoint.setCategory(item.getCategory());
                mapPoint.setPrice(item.getPrice());
                mapPoint.setImageid(item.getImageid());
                mapPoint.setInformations(item.getInformations());
                mapPoint.setQuantity(item.getQuantity());

                //TEST
                System.out.println(item.getId());
                System.out.println(item.getArtisanid());

                Artisan artisan = artisanRepo.findById(item.getArtisanid());

                mapPoint.setArtisanName(artisan.getName());
                mapPoint.setEmail(artisan.getEmail());
                mapPoint.setPhoneNumber(artisan.getPhonenumber());

                mapPoints.add(mapPoint);

                //TEST
                System.out.println(cnt);
            }
            return new ResponseEntity<>(mapPoints, HttpStatus.OK);
        } catch (Exception e) {
            //TEST
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
