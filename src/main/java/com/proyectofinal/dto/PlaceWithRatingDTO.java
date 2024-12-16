package com.proyectofinal.dto;

import java.util.List;

public class PlaceWithRatingDTO {

    private String placeName;
    private String category;
    private double averageRating;
    private String description;  // Description from the Place entity
    private Double latitude;
    private Double longitude;
    private String imagePath;
    private List<RatingDetail> ratings;  // List of ratings with user details

    // Constructor
    public PlaceWithRatingDTO(String placeName, String category, double averageRating, String description, Double longitude, Double latitude, String imagePath, List<RatingDetail> ratings) {
        this.placeName = placeName;
        this.category = category;
        this.averageRating = averageRating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.imagePath = imagePath;
        this.ratings = ratings;
    }

    // Getters and Setters


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RatingDetail> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingDetail> ratings) {
        this.ratings = ratings;
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

    // Inner class to hold rating details (user name and rating value)
    public static class RatingDetail {
        private String userName;
        private int rating;

        public RatingDetail(String userName, int rating) {
            this.userName = userName;
            this.rating = rating;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }


    }
}