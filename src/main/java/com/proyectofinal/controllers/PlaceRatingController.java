package com.proyectofinal.controllers;

import com.proyectofinal.persistence.entities.PlaceRating;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.repositories.UserRepository;
import com.proyectofinal.service.PlaceRatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin
public class PlaceRatingController {

    @Autowired
    private PlaceRatingService placeRatingService;

    @Autowired
    private UserRepository userRepository;  // Use @Autowired to inject the UserRepository

    @PostMapping("/rate/{placeId}/{rating}")
    public ResponseEntity<PlaceRating> addRating(@PathVariable int placeId, @PathVariable int rating) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Username is the authenticated user's name (assumed to be user identifier)

        try {
            // Get the user from the repository
            User user = userRepository.findByUsername(username);  // Directly fetch the user
            if (user == null) {
                return ResponseEntity.badRequest().body(null); // User not found
            }

            // Use the user object here
            PlaceRating placeRating = placeRatingService.addRating(placeId, user.getId(), rating);
            return ResponseEntity.ok(placeRating);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Handle errors like place or user not found
        }
    }

    @PutMapping("/rateUpdate/{placeId}/{rating}")
    public ResponseEntity<PlaceRating> updateRating(@PathVariable int placeId, @PathVariable int rating) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Username is the authenticated user's name (assumed to be user identifier)

        try {
            // Get the user from the repository
            User user = userRepository.findByUsername(username);  // Directly fetch the user
            if (user == null) {
                return ResponseEntity.badRequest().body(null); // User not found
            }

            // Use the user object here
            PlaceRating placeRating = placeRatingService.updateRating(placeId, user.getId(), rating);
            return ResponseEntity.ok(placeRating);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Handle errors like place or user not found
        }
    }


}
