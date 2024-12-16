package com.proyectofinal.controllers;

import com.proyectofinal.persistence.entities.Place;
import com.proyectofinal.persistence.entities.PlaceRating;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.repositories.PlaceRepository;
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
    private UserRepository userRepository;
    @Autowired
    private PlaceRepository placeRepository;


    // Create or update the rating for a place
    @PostMapping("/rate/{placeId}/{rating}")
    public ResponseEntity<PlaceRating> createOrUpdateRating(@PathVariable int placeId, @PathVariable int rating) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // The username is the identifier of the authenticated user

        // If the user is not logged in (username is anonymous), we return an error or indicate that login is required
        if (username.equals("anonymousUser")) {
            return ResponseEntity.status(401).body(null); // Unauthorized access (user needs to log in)
        }

        try {
            // Fetch the user from the repository
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return ResponseEntity.badRequest().body(null); // If the user doesn't exist, return bad request
            }

            // Call the service to create or update the rating
            PlaceRating placeRating = placeRatingService.createOrUpdateRating(placeId, user.getId(), rating);

            return ResponseEntity.ok(placeRating);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Handle any errors that occur during the rating process
        }
    }




    @GetMapping("/user/{placeId}")
    public ResponseEntity<PlaceRating> getRatingForPlace(@PathVariable int placeId) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // The username is the identifier of the authenticated user

        // If the user is not logged in, return a rating of 0
        if (username.equals("anonymousUser")) {
            // Create a new PlaceRating object and set the rating to 0
            PlaceRating placeRating = new PlaceRating();
            placeRating.setRating(0);
            return ResponseEntity.ok(placeRating); // Return the default rating of 0
        }

        try {
            // Fetch the user from the repository
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return ResponseEntity.badRequest().body(null); // If the user doesn't exist, return bad request
            }

            // Fetch the rating for the place by the user
            PlaceRating placeRating = placeRatingService.getRatingForPlace(placeId, user.getId());

            // If no rating exists, return a default rating of 0
            if (placeRating == null) {
                placeRating = new PlaceRating();
                placeRating.setRating(0);  // Default rating of 0
            }

            return ResponseEntity.ok(placeRating);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Handle any errors during fetching the rating
        }
    }



}