package com.proyectofinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.dto.PlaceWithRatingDTO;
import com.proyectofinal.persistence.entities.Place;
import com.proyectofinal.persistence.entities.PlaceRating;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.repositories.PlaceRatingRepository;
import com.proyectofinal.persistence.repositories.PlaceRepository;
import com.proyectofinal.persistence.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PlaceController {

    private final PlaceRepository placeRepository;
    private final PlaceRatingRepository placeRatingRepository;
    private final UserRepository userRepository;

    @Autowired
    public PlaceController(PlaceRepository placeRepository, PlaceRatingRepository placeRatingRepository, UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.placeRatingRepository = placeRatingRepository;
        this.userRepository = userRepository;
    }

    // Endpoint to get places by autonomy and category, sorted by average rating
    @GetMapping("/autonomy/{autonomyId}/category/{category}")
    public List<PlaceWithRatingDTO> getPlacesByAutonomyAndCategory(
            @PathVariable Integer autonomyId, @PathVariable String category) {

        // Fetch places by autonomy and category
        List<Place> places = placeRepository.findByAutonomy_IdAndCategory(autonomyId, category);

        // Sort places by average rating (from high to low)
        return places.stream()
                .sorted((p1, p2) -> Double.compare(
                        calculateAverageRating(p2), calculateAverageRating(p1)))  // Sort by rating
                .map(place -> new PlaceWithRatingDTO(
                        place.getName(),
                        place.getCategory(),
                        calculateAverageRating(place),
                        place.getDescription(),  // Include description
                        getRatingsWithUserDetails(place),
                        place.getImagePath()
                ))  // Include ratings with user names
                .collect(Collectors.toList());
    }

    // Helper method to calculate the average rating for a place
    private double calculateAverageRating(Place place) {
        List<PlaceRating> ratings = placeRatingRepository.findByPlace(place);
        if (ratings.isEmpty()) {
            return 0; // No ratings, default to 0
        }
        return ratings.stream()
                .mapToInt(PlaceRating::getRating)
                .average()
                .orElse(0);
    }

    // Helper method to get ratings with user details for a place
    private List<PlaceWithRatingDTO.RatingDetail> getRatingsWithUserDetails(Place place) {
        List<PlaceRating> ratings = placeRatingRepository.findByPlace(place);
        return ratings.stream()
                .map(rating -> {
                    String userName = userRepository.findById(rating.getUser().getId())  // Assuming the `User` entity has a method to get the username
                            .map(user -> user.getUsername())  // or use getFullName() if that's what you need
                            .orElse("Unknown User");
                    return new PlaceWithRatingDTO.RatingDetail(userName, rating.getRating());
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/places/{placeId}")
    public Place getPlace(@PathVariable int placeId) {
        return placeRepository.findById(placeId).orElseThrow(() -> new RuntimeException("Place not found"));
    }

}
