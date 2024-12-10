package com.proyectofinal.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyectofinal.persistence.entities.Autonomy;
import com.proyectofinal.persistence.repositories.AutonomyRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AutonomyService {

    @Autowired
    private AutonomyRepository autonomyRepository;

    
}
