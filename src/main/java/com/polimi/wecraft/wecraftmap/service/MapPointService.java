package com.polimi.wecraft.wecraftmap.service;

import com.polimi.wecraft.wecraftmap.dao.ArtisanRepo;
import com.polimi.wecraft.wecraftmap.dao.CategoryRepo;
import com.polimi.wecraft.wecraftmap.dao.ItemRepo;
import com.polimi.wecraft.wecraftmap.model.*;

import com.polimi.wecraft.wecraftmap.model.body.FilterParams;
import com.polimi.wecraft.wecraftmap.model.response.ClientItem;
import com.polimi.wecraft.wecraftmap.model.response.MapPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class MapPointService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    ArtisanRepo artisanRepo;


    //build a response object to provide all the data for showing map markers
    public ResponseEntity<HashMap<String, List<MapPoint>>> getAllMapPoints(){

        try{
            List<MapPoint> mapPoints = new ArrayList<>();

            List<Artisan> artisans = (List<Artisan>) artisanRepo.findAll();

            if(artisans.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            //for each artisan create a Map marker
            for(Artisan artisan : artisans){

                MapPoint mapPoint = new MapPoint();

                //adding artisan details
                mapPoint.setLatitude(artisan.getLatitude());
                mapPoint.setLongitude(artisan.getLongitude());
                mapPoint.setArtisanName(artisan.getName());
                mapPoint.setEmail(artisan.getEmail());
                mapPoint.setPhoneNumber(artisan.getPhonenumber());

                //querying database to retrieve items on sale by that artisan
                List<Item> items = itemRepo.findByArtisanid(artisan.getId());

                if(items.isEmpty()){
                    mapPoint.setItemsList(null);
                }
                else {
                    ArrayList<ClientItem> clientItems = new ArrayList<>();

                    //building item list
                    for(Item item : items){

                        ClientItem clientItem = new ClientItem();
                        clientItem.setCategory(categoryRepo.findNameById(item.getCategoryid()));
                        clientItem.setItemDescription(item.getDescription());
                        clientItem.setQuantity(item.getQuantity());
                        clientItem.setPrice(item.getPrice());
                        clientItem.setItemName(item.getName());
                        clientItem.setImageid(item.getImageid());

                        clientItems.add(clientItem);
                    }

                    mapPoint.setItemsList(clientItems);
                }
                mapPoints.add(mapPoint);
            }


            HashMap<String, List<MapPoint>> response = new HashMap<>();
            response.put("artisans", mapPoints);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //build a response object to provide all the data for showing map markers (filtered)
    public ResponseEntity<HashMap<String, List<MapPoint>>> getFilteredPoints(FilterParams filterParams){

        //getting filters
        int priceMin = filterParams.getPriceMin();
        int priceMax = filterParams.getPriceMax();

        //parameters checking
        if((priceMin > priceMax) || priceMin < itemRepo.minPrice() || priceMax > itemRepo.maxPrice()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }

        for(long id : filterParams.getCategories()){

            if(categoryRepo.findById(id).isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }



        try{
            List<MapPoint> mapPoints = new ArrayList<>();

            List<Artisan> artisans = (List<Artisan>) artisanRepo.findAll();

            if(artisans.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            //for each artisan create a Map marker
            for(Artisan artisan : artisans){

                MapPoint mapPoint = new MapPoint();
                ArrayList<Item> itemsList = new ArrayList<>();

                //querying database to retrieve items on sale by that artisan (filtered)
                for(long categoryId : filterParams.getCategories()){

                    List<Item> items = itemRepo.findByArtisanidAndCategoryidAndPriceBetween(artisan.getId(), categoryId, filterParams.getPriceMin(), filterParams.getPriceMax());
                    itemsList.addAll(items);
                }

                if(itemsList.isEmpty()){
                    mapPoint.setItemsList(null);
                }
                else {
                    ArrayList<ClientItem> clientItems = new ArrayList<>();

                    //building item list
                    for(Item item : itemsList){

                        ClientItem clientItem = new ClientItem();
                        clientItem.setCategory(categoryRepo.findNameById(item.getCategoryid()));
                        clientItem.setItemDescription(item.getDescription());
                        clientItem.setQuantity(item.getQuantity());
                        clientItem.setPrice(item.getPrice());
                        clientItem.setItemName(item.getName());
                        clientItem.setImageid(item.getImageid());

                        clientItems.add(clientItem);
                    }

                    mapPoint.setItemsList(clientItems);

                    mapPoint.setLatitude(artisan.getLatitude());
                    mapPoint.setLongitude(artisan.getLongitude());
                    mapPoint.setArtisanName(artisan.getName());
                    mapPoint.setEmail(artisan.getEmail());
                    mapPoint.setPhoneNumber(artisan.getPhonenumber());

                    mapPoints.add(mapPoint);
                }

            }

            HashMap<String, List<MapPoint>> response = new HashMap<>();
            response.put("artisans", mapPoints);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
