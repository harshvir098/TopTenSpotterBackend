package com.proyectofinal.persistence.entities;

import jakarta.persistence.*;
@Entity
public class PlaceRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;  // The place being rated

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;    // The user who rated the place

    private int rating;  // Rating value, e.g., 1 to 5

    // Constructors, Getters and Setters
    public PlaceRating() {}

    public PlaceRating(Place place, User user, int rating) {
        this.place = place;
        this.user = user;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}