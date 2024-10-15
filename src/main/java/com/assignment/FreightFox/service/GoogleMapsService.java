package com.assignment.FreightFox.service;
import com.assignment.FreightFox.model.Route;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsService {

	  private final GeoApiContext context;

	    public GoogleMapsService(@Value("${google.maps.api.key}") String apiKey) {
	        this.context = new GeoApiContext.Builder()
	                .apiKey(apiKey)
	                .connectTimeout(20, TimeUnit.SECONDS)  // Increase timeout
	                .readTimeout(20, TimeUnit.SECONDS)     // Increase timeout
	                .writeTimeout(20, TimeUnit.SECONDS)    // Increase timeout
	                .retryTimeout(5, TimeUnit.SECONDS)     // Add retry with timeout
	                .maxRetries(3)                         // Set max retries
	                .build();
	    }

	    public Route fetchRoute(String fromPincode, String toPincode) {
	        try {
	            DistanceMatrix result = DistanceMatrixApi.newRequest(context)
	                    .origins(fromPincode)
	                    .destinations(toPincode)
	                    .mode(TravelMode.DRIVING)
	                    .await();

	            if (result.rows.length > 0 && result.rows[0].elements.length > 0) {
	                long distanceInMeters = result.rows[0].elements[0].distance.inMeters;
	                long durationInSeconds = result.rows[0].elements[0].duration.inSeconds;

	                return new Route(fromPincode, toPincode, 
	                                 (double) distanceInMeters / 1000, // Convert to km
	                                 (int) durationInSeconds, 
	                                 "Route from " + fromPincode + " to " + toPincode);
	            }
	        } catch (Exception e) {
	            // Log the error and handle it appropriately
	            System.err.println("Error fetching route: " + e.getMessage());
	            e.printStackTrace();
	        }

	        // Return a default route if there's an error
	        return new Route(fromPincode, toPincode, 0.0, 0, "Route not found");
	    }

	    public boolean testGoogleMapsConnection() {
	        try {
	            DistanceMatrix result = DistanceMatrixApi.newRequest(context)
	                    .origins("00000")
	                    .destinations("00000")
	                    .mode(TravelMode.DRIVING)
	                    .await();
	            return true;
	        } catch (Exception e) {
	            System.err.println("Error testing Google Maps connection: " + e.getMessage());
	            e.printStackTrace();
	            return false;
	        }
	    }
	}