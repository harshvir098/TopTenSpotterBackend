package com.proyectofinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.proyectofinal.persistence.entities.Place;
import com.proyectofinal.persistence.entities.PlaceRating;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.repositories.PlaceRatingRepository;
import com.proyectofinal.persistence.repositories.PlaceRepository;
import com.proyectofinal.persistence.repositories.UserRepository;
import com.proyectofinal.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private PlaceRatingRepository placeRatingRepository;
    private PlaceRepository placeRepository;
    @Autowired
    private UserService userService;

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.status(404).body(null));  // User not found
    }

    // Update user profile with optional photo upload
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id,
                                             @RequestParam(required = false) MultipartFile photo,
                                             @RequestParam String firstName,
                                             @RequestParam String lastName,
                                             @RequestParam int age,
                                             @RequestParam String location) {

        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Update fields other than photo
            existingUser.setFirstName(firstName);
            existingUser.setLastName(lastName);
            existingUser.setAge(age);
            existingUser.setLocation(location);

            // If a new photo is uploaded, handle it
            if (photo != null && !photo.isEmpty()) {
                String photoName = savePhoto(photo);
                existingUser.setPhoto(photoName);  // Save only the filename in the database
            }

            // Save updated user to the database
            userRepository.save(existingUser);
            return ResponseEntity.ok("{\"message\":\"User updated successfully\"}");
        } else {
            return ResponseEntity.status(404).body("{\"error\":\"User not found\"}");
        }
    }

    // Method to save photo and return the filename
    private String savePhoto(MultipartFile photo) {
        try {
            // Define the folder to store uploaded photos (relative to the resources directory)
        	// Use a dynamic path
        	String uploadDir = Paths.get("src/main/resources/static/profileimg/").toAbsolutePath().toString();
        	File uploadDirectory = new File(uploadDir);
        	if (!uploadDirectory.exists()) {
        	    uploadDirectory.mkdirs();  // Create directory if it doesn't exist
        	}

           

            // Get the original filename and create a unique filename
            String filename = System.currentTimeMillis() + "_" + photo.getOriginalFilename();

            // Save the photo to the specified directory
            File fileToSave = new File(uploadDir + filename);
            photo.transferTo(fileToSave);

            // Return the filename to store in the database
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save photo", e);
        }
    }
    
    
      
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteUser() {
        // Get the logged-in user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.deleteUserByUsername(username);

        // Respond with a success message
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }

}
