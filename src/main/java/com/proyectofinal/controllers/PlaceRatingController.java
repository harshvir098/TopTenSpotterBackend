package com.proyectofinal.controllers;


import com.proyectofinal.persistence.entities.PlaceRating;
import com.proyectofinal.service.PlaceRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/ratings")
@CrossOrigin
public class PlaceRatingController {

    @Autowired
    private PlaceRatingService placeRatingService;

    // Endpoint to add or update a rating for a specific place by a user
    @PostMapping("/rate")
    public ResponseEntity<PlaceRating> ratePlace(@RequestParam int placeId, @RequestParam int userId, @RequestParam int rating) {
        try {
            PlaceRating placeRating = placeRatingService.addOrUpdateRating(placeId, userId, rating);
            return ResponseEntity.ok(placeRating);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Handle errors like place or user not found
        }
    }

    // Endpoint to delete a rating by a user for a specific place
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRating(@RequestParam int placeId, @RequestParam int userId) {
        try {
            placeRatingService.deleteRating(placeId, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint to get all ratings for a specific place
    @GetMapping("/place/{placeId}")
    public ResponseEntity<List<PlaceRating>> getRatingsForPlace(@PathVariable int placeId) {
        List<PlaceRating> ratings = placeRatingService.getRatingsForPlace(placeId);
        return ResponseEntity.ok(ratings);
    }
}
