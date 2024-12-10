package com.proyectofinal.persistence.repositories;

import com.proyectofinal.persistence.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {
    // Custom query to find places by autonomy ID and category
    List<Place> findByAutonomy_IdAndCategory(Integer autonomyId, String category);

   Place findByName(String placeName);
}