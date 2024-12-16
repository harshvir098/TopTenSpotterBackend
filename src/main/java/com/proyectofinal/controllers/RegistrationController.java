package com.proyectofinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.dto.UserRegistrationRequest;
import com.proyectofinal.persistence.entities.User;
import com.proyectofinal.persistence.repositories.UserRepository;
import com.proyectofinal.dto.UserRegistrationRequest;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest request) {
        // Check if username already exists
        User existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("{\"error\":\"Username already exists\"}");
        }

        // Encrypt the password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Create a new user
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(encodedPassword);  // Storing the encrypted password
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setAge(request.getAge());
        newUser.setLocation(request.getLocation());

        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setEnabled(true);

        // Save the user to the database
        userRepository.save(newUser);

        return ResponseEntity.ok("{\"message\":\"User registered successfully\"}");
    }



}