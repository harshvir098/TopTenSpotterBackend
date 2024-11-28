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
import java.util.Optional;

@Service
public class PlaceRatingService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final PlaceRatingRepository placeRatingRepository;

    @Autowired
    public PlaceRatingService(PlaceRepository placeRepository, UserRepository userRepository, PlaceRatingRepository placeRatingRepository) {
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.placeRatingRepository = placeRatingRepository;
    }

    // Method to add a new rating
    public PlaceRating addRating(int placeId, int userId, int rating) {
        // Find the place and user
        Optional<Place> place = placeRepository.findById(placeId);
        Optional<User> user = userRepository.findById(userId);

        if (place.isEmpty() || user.isEmpty()) {
            throw new RuntimeException("Place or User not found");
        }

        // Check if the user has already rated the place
        Optional<PlaceRating> existingRating = placeRatingRepository.findByPlaceAndUser(place.get(), user.get());
        if (existingRating.isPresent()) {
            throw new RuntimeException("User has already rated this place");
        }

        // Create a new rating
        PlaceRating placeRating = new PlaceRating();
        placeRating.setPlace(place.get());
        placeRating.setUser(user.get());
        placeRating.setRating(rating);
        return placeRatingRepository.save(placeRating);
    }

    // Method to update an existing rating
    public PlaceRating updateRating(int placeId, int userId, int rating) {
        // Find the place and user
        Optional<Place> place = placeRepository.findById(placeId);
        Optional<User> user = userRepository.findById(userId);

        if (place.isEmpty() || user.isEmpty()) {
            throw new RuntimeException("Place or User not found");
        }

        // Find the existing rating
        Optional<PlaceRating> existingRating = placeRatingRepository.findByPlaceAndUser(place.get(), user.get());
        if (existingRating.isEmpty()) {
            throw new RuntimeException("User has not rated this place");
        }

        // Check if the user is the one who originally rated the place
        PlaceRating placeRating = existingRating.get();
        if (placeRating.getUser().getId() != userId) {
            throw new RuntimeException("User is not authorized to update this rating");
        }

        // Update the rating
        placeRating.setRating(rating);
        return placeRatingRepository.save(placeRating);
    }
}
