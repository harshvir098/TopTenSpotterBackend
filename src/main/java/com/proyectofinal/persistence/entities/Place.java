package com.proyectofinal.persistence.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String category;
    private String imagePath; // New field

    @ManyToOne
    @JoinColumn(name = "autonomy_id", nullable = false)
    private Autonomy autonomy;

    @JsonIgnore
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true) // Relación uno a muchos, gestionada por `place` en `Comments`
    private List<PlaceRating> ratings;

    @JsonIgnore
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true) // Relación uno a muchos, gestionada por `place` en `Comments`
    private List<Comments> comments; // Aquí también usamos `mappedBy`

    // Default constructor
    public Place() {}

    // Parameterized constructor
    public Place(String name, String description, Double latitude, Double longitude, String category, Autonomy autonomy, String imagePath, List<Comments> comments ) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.autonomy = autonomy;
        this.imagePath = imagePath;
        this.comments = comments;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0;
        }
        double total = 0;
        for (PlaceRating rating : ratings) {
            total += rating.getRating();
        }
        return total / ratings.size();
    }

    public int getRanking() {
        return (int) Math.round(getAverageRating());
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
