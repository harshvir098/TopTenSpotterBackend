package com.proyectofinal.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.dto.PlaceWithRatingDTO;
import com.proyectofinal.persistence.entities.Place;
import com.proyectofinal.persistence.entities.PlaceRating;
import com.proyectofinal.persistence.repositories.PlaceRatingRepository;
import com.proyectofinal.persistence.repositories.PlaceRepository;
import com.proyectofinal.persistence.repositories.UserRepository;

@RestController
@RequestMapping("/api")
public class PlaceController {

    private final PlaceRepository placeRepository;
    private final PlaceRatingRepository placeRatingRepository;
    private final UserRepository userRepository; // Assuming there's a UserRepository to get user details

    @Autowired
    public PlaceController(PlaceRepository placeRepository, PlaceRatingRepository placeRatingRepository, UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.placeRatingRepository = placeRatingRepository;
        this.userRepository = userRepository;
    }

    // Helper method to get ratings and user details for all places in a single query
    @GetMapping("/autonomy/{autonomyId}/category/{category}")
    public List<PlaceWithRatingDTO> getPlacesByAutonomyAndCategory(
            @PathVariable Integer autonomyId, @PathVariable String category) {

        // Fetch places by autonomy and category
        List<Place> places = placeRepository.findByAutonomy_IdAndCategory(autonomyId, category);

        // Fetch ratings and user details for all places in a single query
        List<PlaceRating> allRatings = placeRatingRepository.findByPlaceIn(places);  // Get all ratings for these places in one query
        Map<Integer, List<PlaceRating>> placeRatingsMap = allRatings.stream()
                .collect(Collectors.groupingBy(rating -> rating.getPlace().getId()));  // Group ratings by place ID (Integer)

        // Sort places by average rating (from high to low)
        return places.stream()
                .sorted((p1, p2) -> Double.compare(
                        calculateAverageRating(p2, placeRatingsMap), calculateAverageRating(p1, placeRatingsMap)))  // Sort by rating
                .map(place -> new PlaceWithRatingDTO(
                        place.getName(),
                        place.getCategory(),
                        calculateAverageRating(place, placeRatingsMap),
                        place.getDescription(),
                        place.getLongitude(),
                        place.getLatitude(),
                        place.getImagePath(),
                        getRatingsWithUserDetails(place, placeRatingsMap)))  // Include ratings with user names
                .collect(Collectors.toList());
    }

    // Helper method to calculate the average rating for a place, using pre-fetched ratings
    private double calculateAverageRating(Place place, Map<Integer, List<PlaceRating>> placeRatingsMap) {
        List<PlaceRating> ratings = placeRatingsMap.get(place.getId());
        if (ratings == null || ratings.isEmpty()) {
            return 0; // No ratings, default to 0
        }
        return ratings.stream()
                .mapToInt(PlaceRating::getRating)
                .average()
                .orElse(0);
    }

    // Helper method to get ratings with user details for a place, using pre-fetched ratings
    private List<PlaceWithRatingDTO.RatingDetail> getRatingsWithUserDetails(Place place, Map<Integer, List<PlaceRating>> placeRatingsMap) {
        List<PlaceRating> ratings = placeRatingsMap.get(place.getId());
        if (ratings == null) {
            return Collections.emptyList();
        }
        return ratings.stream()
                .map(rating -> {
                    String userName = userRepository.findById(rating.getUser().getId())  // Assuming the `User` entity has a method to get the username
                            .map(user -> user.getUsername())
                            .orElse("Unknown User");
                    return new PlaceWithRatingDTO.RatingDetail(userName, rating.getRating());
                })
                .collect(Collectors.toList());
    }


    @GetMapping("/places/{placeName}")
    public Place getPlace(@PathVariable String placeName) {
        return placeRepository.findByName(placeName);
    }
}
