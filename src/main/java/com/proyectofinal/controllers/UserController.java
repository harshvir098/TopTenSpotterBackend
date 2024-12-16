package com.proyectofinal.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.repositories.UserRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
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
            // Use the /tmp directory for Render, as it is writable in cloud environments
            String uploadDir = "/tmp/uploads/profileimg";  // /tmp is writable on Render
            
            // Log the upload path to debug
            System.out.println("Attempting to save photo to: " + uploadDir);
            
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                boolean created = uploadDirectory.mkdirs();  // Create the directory if it doesn't exist
                if (!created) {
                    System.out.println("Failed to create directory: " + uploadDir);
                    throw new RuntimeException("Failed to create directory: " + uploadDir);
                }
            }

            // Get the original filename and create a unique filename
            String filename = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            
            // Ensure the file path is correct with a separator
            File fileToSave = new File(uploadDir + "/" + filename);
            
            // Save the photo to the specified directory
            photo.transferTo(fileToSave);

            // Log the saved file path for debugging
            System.out.println("Photo saved to: " + fileToSave.getAbsolutePath());

            // Return the filename to store in the database
            return filename;
        } catch (IOException e) {
            // Log the error and rethrow it
            System.err.println("Error saving photo: " + e.getMessage());
            throw new RuntimeException("Failed to save photo", e);
        }
    }

    
    
    
}