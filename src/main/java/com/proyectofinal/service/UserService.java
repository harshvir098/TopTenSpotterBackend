package com.proyectofinal.service;

import com.proyectofinal.persistence.entities.Place;
import com.proyectofinal.persistence.entities.PlaceRating;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.repositories.PlaceRatingRepository;
import com.proyectofinal.persistence.repositories.PlaceRepository;
import com.proyectofinal.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceRatingRepository placeRatingRepository;

    @Transactional
    public void deleteUserByUsername(String username) {
        // Fetch the user by username
        User user = userRepository.findByUsername(username);
        // Remove user ratings from their respective places
        for (PlaceRating rating : user.getPlaceRatings()) {
            Place place = rating.getPlace();

            // Remove the rating from the place's ratings list
            place.getRatings().remove(rating);

            // Save the updated place to persist changes
            placeRepository.save(place);

            // Now you can safely delete the rating
            placeRatingRepository.delete(rating); // Delete after updating
        }

        // Delete the user
        userRepository.delete(user);
    }
}
