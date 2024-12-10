package com.proyectofinal;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.proyectofinal.service.AutonomyDataService;

@SpringBootApplication
public class ProyectoApplication {
	
	private AutonomyDataService autonomyDataService;

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}
	
	
}
