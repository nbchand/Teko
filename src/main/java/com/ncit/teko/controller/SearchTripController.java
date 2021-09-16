package com.ncit.teko.controller;

import java.util.Map;

import com.ncit.teko.service.TripSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SearchTripController {
    @Autowired
    TripSearchService tripSearchService;
    
    @PostMapping("/search-trips")
    public ResponseEntity<?> findTrips(@RequestBody Map<String,String> json){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
