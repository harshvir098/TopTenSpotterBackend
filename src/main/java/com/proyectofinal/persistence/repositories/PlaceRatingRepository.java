package com.proyectofinal.persistence.repositories;

import com.proyectofinal.persistence.entities.PlaceRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRatingRepository extends JpaRepository<PlaceRating, Integer> {

    List<PlaceRating> findByPlaceId(int placeId);

    List<PlaceRating> findByUserId(int userId);
    
    void deleteByPlaceIdAndUserId(int placeId, int userId);  // Delete by place and user
}
