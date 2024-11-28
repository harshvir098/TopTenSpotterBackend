package com.proyectofinal.persistence.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private Double latitude;  // Use Double instead of double
    private Double longitude;  // Use Double instead of double

    private String category;

    @ManyToOne
    @JoinColumn(name = "autonomy_id", nullable = false)
    private Autonomy autonomy;

    @OneToMany(mappedBy = "place")
    private List<PlaceRating> ratings;

    // Default constructor
    public Place() {}

    // Parameterized constructor
    public Place(String name, String description, Double latitude, Double longitude, String category, Autonomy autonomy) {
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
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
