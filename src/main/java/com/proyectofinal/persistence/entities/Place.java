package com.proyectofinal.persistence.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private double latitude;
    private double longitude;

    private String category;  // Category like Restaurant, Nightclub, etc.

    @ManyToOne
    @JoinColumn(name = "autonomy_id", nullable = false)
    private Autonomy autonomy;  // Autonomy (e.g., Catalonia)

    @OneToMany(mappedBy = "place")
    private List<PlaceRating> ratings;  // List of ratings for this place

    // Constructors, Getters, and Setters
    public Place() {}

    public Place(String name, String description, double latitude, double longitude, String category, Autonomy autonomy) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.autonomy = autonomy;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Autonomy getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(Autonomy autonomy) {
        this.autonomy = autonomy;
    }

    public List<PlaceRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<PlaceRating> ratings) {
        this.ratings = ratings;
    }
}
