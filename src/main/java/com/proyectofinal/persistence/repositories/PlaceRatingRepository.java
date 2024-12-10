package com.proyectofinal.persistence.repositories;

import com.proyectofinal.persistence.entities.PlaceRating;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.entities.Place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRatingRepository extends JpaRepository<PlaceRating, Integer> {

    // Fetch all ratings for a place by its ID (you can also use the Place entity itself)
    List<PlaceRating> findByPlaceId(int placeId);

    // Fetch all ratings given by a specific user by their ID
    List<PlaceRating> findByUserId(int userId);

    // Delete a rating based on place and user IDs
    void deleteByPlaceIdAndUserId(int placeId, int userId);

    // Find a rating by place and user (optional because it might not exist)
    Optional<PlaceRating> findByPlaceAndUser(Place place, User user);

    // Fetch all ratings for a specific place
    List<PlaceRating> findByPlace(Place place);


    Optional<PlaceRating> findByPlaceIdAndUserId(int placeId, int userId);
}