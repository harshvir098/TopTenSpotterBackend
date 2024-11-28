package com.proyectofinal.controllers;

import com.proyectofinal.persistence.entities.Place;
import com.proyectofinal.persistence.repositories.PlaceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlaceController {

    private final PlaceRepository placeRepository;

    public PlaceController(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @GetMapping("/api/autonomy/{autonomyId}/category/{category}")
    public List<Place> getPlacesByAutonomyAndCategory(@PathVariable Integer autonomyId, @PathVariable String category) {
        List<Place> places = placeRepository.findByAutonomy_IdAndCategory(autonomyId, category);
        System.out.println("Fetched places: " + places);  // Add logging for debugging
        return places;
    }

}
