package com.proyectofinal.service;

import com.proyectofinal.persistence.entities.Place;
import com.proyectofinal.persistence.entities.PlaceRating;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.repositories.PlaceRatingRepository;
import com.proyectofinal.persistence.repositories.PlaceRepository;
import com.proyectofinal.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Método para crear o actualizar la calificación
    public PlaceRating createOrUpdateRating(int placeId, int userId, int rating) {
        // Buscar el lugar y el usuario
        Optional<Place> place = placeRepository.findById(placeId);
        Optional<User> user = userRepository.findById(userId);

        if (place.isEmpty() || user.isEmpty()) {
            throw new RuntimeException("Place or User not found");
        }

        // Verificar si ya existe una calificación para este lugar y usuario
        Optional<PlaceRating> existingRating = placeRatingRepository.findByPlaceAndUser(place.get(), user.get());
        PlaceRating placeRating;

        if (existingRating.isPresent()) {
            // Si existe, actualizar la calificación
            placeRating = existingRating.get();
            placeRating.setRating(rating);
        } else {
            // Si no existe, crear una nueva calificación
            placeRating = new PlaceRating();
            placeRating.setPlace(place.get());
            placeRating.setUser(user.get());
            placeRating.setRating(rating);
        }

        // Guardar la calificación (nueva o actualizada)
        return placeRatingRepository.save(placeRating);
    }

    public Integer getUserRating(int placeId, int userId) {
        Optional<Place> place = placeRepository.findById(placeId);
        Optional<User> user = userRepository.findById(userId);

        if (place.isEmpty() || user.isEmpty()) {
            throw new RuntimeException("Place or User not found");
        }

        // Fetch the rating for the specific user and place
        PlaceRating placeRating = placeRatingRepository.findByPlaceAndUser(place.get(), user.get()).orElse(null);

        if (placeRating != null) {
            return placeRating.getRating();
        }

        return null; // Return null if no rating found
    }

    public PlaceRating getRatingForPlace(int placeId, int userId) {
        // Fetch the rating for the specific place and user
        return placeRatingRepository.findByPlaceIdAndUserId(placeId, userId).orElse(null); // Returns null if no rating exists
    }





}