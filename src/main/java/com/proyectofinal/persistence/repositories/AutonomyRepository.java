package com.proyectofinal.persistence.repositories;

import com.proyectofinal.persistence.entities.Autonomy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutonomyRepository extends JpaRepository<Autonomy, Integer> {

    boolean existsByName(String name);

    // Metodo para encontrar una autonomía por nombre
    Optional<Autonomy> findByName(String name);}