package com.proyectofinal.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectofinal.persistence.entities.Autonomy;
import com.proyectofinal.persistence.repositories.AutonomyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AutonomyService  {

    @Autowired
    private AutonomyRepository autonomyRepository;

 
    
    public List<Autonomy> getAllAutonomies() {
        return autonomyRepository.findAll();
    }
}
