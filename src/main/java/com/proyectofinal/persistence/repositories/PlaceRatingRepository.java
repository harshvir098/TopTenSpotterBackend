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

    List<PlaceRating> findByPlaceId(int placeId);

    List<PlaceRating> findByUserId(int userId);
    
    void deleteByPlaceIdAndUserId(int placeId, int userId);  // Delete by place and user
    Optional <PlaceRating> findByPlaceAndUser(Place place, User user);


    List<PlaceRating> findByPlace(Place place);}
