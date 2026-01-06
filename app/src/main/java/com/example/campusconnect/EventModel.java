package com.example.campusconnect;

public class EventModel {
    private String title;
    private String description;
    private String postedBy;
    private String date;
    private String photoUri; // Cloudinary image URL
    private String winners;

    public EventModel() {
        // Needed for Firebase
    }

    public EventModel(String title, String description, String postedBy, String date,
                      String photoUri, String winners) {
        this.title = title;
        this.description = description;
        this.postedBy = postedBy;
        this.date = date;
        this.photoUri = photoUri;
        this.winners = winners;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPostedBy() { return postedBy; }
    public String getDate() { return date; }
    public String getPhotoUri() { return photoUri; }
    public String getWinners() { return winners; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPostedBy(String postedBy) { this.postedBy = postedBy; }
    public void setDate(String date) { this.date = date; }
    public void setPhotoUri(String photoUri) { this.photoUri = photoUri; }
    public void setWinners(String winners) { this.winners = winners; }
}
