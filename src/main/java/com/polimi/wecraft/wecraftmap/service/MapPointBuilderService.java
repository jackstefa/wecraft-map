package com.polimi.wecraft.wecraftmap.service;

import com.polimi.wecraft.wecraftmap.dao.ArtisanRepo;
import com.polimi.wecraft.wecraftmap.dao.ItemRepo;
import com.polimi.wecraft.wecraftmap.dao.PointRepo;
import com.polimi.wecraft.wecraftmap.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class MapPointBuilderService {

    @Autowired
    PointRepo pointRepo;

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    ArtisanRepo artisanRepo;


    public ResponseEntity<HashMap<String, List<MapPoint>>> getAllMapPoints(){

        try{
            List<MapPoint> mapPoints = new ArrayList<MapPoint>();


            List<Point> points = (List<Point>) pointRepo.findAll();

            if(points.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            for(Point point : points){

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


                Artisan artisan = artisanRepo.findById(item.getArtisanid());

                mapPoint.setArtisanName(artisan.getName());
                mapPoint.setEmail(artisan.getEmail());
                mapPoint.setPhoneNumber(artisan.getPhonenumber());

                mapPoints.add(mapPoint);


            }

            HashMap<String, List<MapPoint>> response = new HashMap<>();
            response.put("mapPoints", mapPoints);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<HashMap<String, List<MapPoint>>> getFilteredPoints(FilterParams filterParams){

        ArrayList<String> categories = filterParams.getCategories();
        int priceMin = filterParams.getPriceMin();
        int priceMax = filterParams.getPriceMax();

        //Parameters checking
        for(String category : categories){

            if(!Categories.contains(category)){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        if((priceMin > priceMax) || (priceMin < Constants.PRICE_MIN) || (priceMax > Constants.PRICE_MAX)){
            System.out.println("pricemin" + priceMin);
            System.out.println("pricemax" + priceMax);
            System.out.println("Sono entrato nel param check del prezzo");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        //Building mapPoints
        try{
            List<MapPoint> mapPoints = new ArrayList<MapPoint>();

            for(String category : categories){

                List<Item> items = itemRepo.findByCategoryAndPriceBetween(category, priceMin, priceMax);

                for(Item item : items) {

                    MapPoint mapPoint = new MapPoint();

                    mapPoint.setCategory(item.getCategory());
                    mapPoint.setImageid(item.getImageid());
                    mapPoint.setInformations(item.getInformations());
                    mapPoint.setItemName(item.getName());
                    mapPoint.setPrice(item.getPrice());
                    mapPoint.setQuantity(item.getQuantity());

                    Artisan artisan = artisanRepo.findById(item.getArtisanid());

                    mapPoint.setArtisanName(artisan.getName());
                    mapPoint.setEmail(artisan.getEmail());
                    mapPoint.setPhoneNumber(artisan.getPhonenumber());

                    Point point = pointRepo.findByItemid(item.getId());

                    mapPoint.setLongitude(point.getLongitude());
                    mapPoint.setLatitude(point.getLatitude());

                    mapPoints.add(mapPoint);
                }
            }

            if(mapPoints.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            HashMap<String, List<MapPoint>> response = new HashMap<>();
            response.put("mapPoints", mapPoints);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
