package com.proyectofinal.controllers;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Obtener una autonomía por nombre
    @GetMapping("/{name}")
    public ResponseEntity<Autonomy> getAutonomyByName(@PathVariable String name) {
        // Buscar la autonomía decodificada
        Optional<Autonomy> autonomy = autonomyRepository.findByName(name);

        if (autonomy.isPresent()) {
            return ResponseEntity.ok(autonomy.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}