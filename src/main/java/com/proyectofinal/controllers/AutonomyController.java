package com.proyectofinal.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyectofinal.persistence.entities.Autonomy;
import com.proyectofinal.persistence.repositories.AutonomyRepository;
import com.proyectofinal.service.AutonomyService;

@RestController
@RequestMapping("/api/autonomies")
@CrossOrigin
public class AutonomyController {

    private final AutonomyRepository autonomyRepository;
    private AutonomyService autonomyService;
    
    
    
   

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
        Optional<Autonomy> autonomy = autonomyRepository.findByName(name);

        if (autonomy.isPresent()) {
            return ResponseEntity.ok(autonomy.get());
        } else {
            return ResponseEntity.notFound().build();
        }

}
    
    
    
    
}
