package com.proyectofinal.controllers;


import com.proyectofinal.persistence.entities.Autonomy;
import com.proyectofinal.service.AutonomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class AutonomyController {

    @Autowired
    private AutonomyService autonomyService;
    @Autowired
   

    // Endpoint to get all autonomies
    @GetMapping("/autonomies")
    public List<Autonomy> getAllAutonomies() {
        return autonomyService.getAllAutonomies();
    }
}

