package com.proyectofinal.persistence.entities;

import jakarta.persistence.*;

@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false) // Especifica la columna de unión con `Place`
    private Place place;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Especifica la columna de unión con `User`
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
