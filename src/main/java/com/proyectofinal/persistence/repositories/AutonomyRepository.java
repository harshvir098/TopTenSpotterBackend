package com.proyectofinal.persistence.repositories;

import com.proyectofinal.persistence.entities.Autonomy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutonomyRepository extends JpaRepository<Autonomy, Integer> {
    
	boolean existsByName(String name);
	
}
