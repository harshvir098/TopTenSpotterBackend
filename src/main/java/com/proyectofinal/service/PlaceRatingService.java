package com.proyectofinal.service;


import com.proyectofinal.persistence.entities.Place;
import com.proyectofinal.persistence.entities.PlaceRating;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.repositories.PlaceRatingRepository;
import com.proyectofinal.persistence.repositories.PlaceRepository;
import com.proyectofinal.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceRatingService {

    @Autowired
    private PlaceRatingRepository placeRatingRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    // Add or update a rating for a place by a user
    public PlaceRating addOrUpdateRating(int placeId, int userId, int rating) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new RuntimeException("Place not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user has already rated this place
        List<PlaceRating> existingRatings = placeRatingRepository.findByPlaceId(placeId);
        for (PlaceRating existingRating : existingRatings) {
            if (existingRating.getUser().getId() == userId) {
                existingRating.setRating(rating);  // Update the rating if the user has already rated the place
                return placeRatingRepository.save(existingRating);
            }
        }

        // If no existing rating, create a new one
        PlaceRating newRating = new PlaceRating(place, user, rating);
        return placeRatingRepository.save(newRating);
    }

    // Delete a user's rating for a specific place
    public void deleteRating(int placeId, int userId) {
        placeRatingRepository.deleteByPlaceIdAndUserId(placeId, userId);
    }

    // Get all ratings for a specific place
    public List<PlaceRating> getRatingsForPlace(int placeId) {
        return placeRatingRepository.findByPlaceId(placeId);
    }
}

