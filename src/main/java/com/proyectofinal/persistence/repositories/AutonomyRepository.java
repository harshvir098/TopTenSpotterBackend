package com.proyectofinal.persistence.repositories;

import com.proyectofinal.persistence.entities.Autonomy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutonomyRepository extends JpaRepository<Autonomy, Integer> {
    
	boolean existsByName(String name);

	Optional<Autonomy> findByName(String name);
	
}
