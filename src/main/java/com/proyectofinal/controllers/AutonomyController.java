package com.proyectofinal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.persistence.entities.Autonomy;
import com.proyectofinal.persistence.repositories.AutonomyRepository;

@RestController
@RequestMapping("/api/autonomies")
@CrossOrigin
public class AutonomyController {

    private final AutonomyRepository autonomyRepository;

    // Constructor injection to inject AutonomyRepository
    @Autowired
    public AutonomyController(AutonomyRepository autonomyRepository) {
        this.autonomyRepository = autonomyRepository;
    }

    // Get all autonomies with location, latitude, and longitude
    @GetMapping
    public List<Autonomy> getAllAutonomies() {
        // Fetching all autonomies from the repository
        return autonomyRepository.findAll();
    }
}
