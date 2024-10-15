package com.assignment.FreightFox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.assignment.FreightFox.model.Route;

@Service
public class RouteService {

    private final GoogleMapsService googleMapsService;

    @Autowired
    public RouteService(GoogleMapsService googleMapsService) {
        this.googleMapsService = googleMapsService;
    }

    @Cacheable(value = "routes", key = "#fromPincode + '-' + #toPincode")
    public Route getRoute(String fromPincode, String toPincode) {

        Route newRoute = googleMapsService.fetchRoute(fromPincode, toPincode);
     
        return newRoute;
    }
}
