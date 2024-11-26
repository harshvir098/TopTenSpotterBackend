package com.proyectofinal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePass {
    public static void main(String[] args) {
        String pass = new BCryptPasswordEncoder().encode("pp");
        System.out.println(pass);
    }
}