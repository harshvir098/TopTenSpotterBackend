package com.proyectofinal.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.persistence.entities.User;

@RestController
@CrossOrigin
public class AuthController {

    @PostMapping(path = "/login")
    public ResponseEntity<String> basicauth(UsernamePasswordAuthenticationToken upa) {
        // El objeto upa tiene información sobre el usuario y la contraseña
        // Si el login ha sido exitoso, a partir de ese momento, desde el front, mandaremos en la cabecera de cada petición el username y password que han provocado que el login sea exitoso
        User u = (User) upa.getPrincipal(); // Si en IntelliJ nos da un error cannot find symbol, significa que no esta pillando el getter de la id
        return ResponseEntity.ok().body("{\"resp\":\"Login exitoso\", \"id\":"+u.getId()+"}");

    }

}