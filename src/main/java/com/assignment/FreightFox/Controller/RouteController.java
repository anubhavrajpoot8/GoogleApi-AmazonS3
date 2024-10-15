package com.assignment.FreightFox.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment.FreightFox.model.Route;
import com.assignment.FreightFox.service.RouteService;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public ResponseEntity<Route> getRoute(@RequestParam String fromPincode, @RequestParam String toPincode) {
        Route route = routeService.getRoute(fromPincode, toPincode);
        return ResponseEntity.ok(route);
    }
}