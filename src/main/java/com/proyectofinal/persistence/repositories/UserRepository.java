package com.proyectofinal.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.persistence.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}