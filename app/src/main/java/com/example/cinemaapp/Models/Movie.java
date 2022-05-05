package com.example.cinemaapp.Models;

public class Movie {

    private String title;
    private Integer room_id;
    private String date;
    private String imageUrl;

    public Movie() {}

    public Movie(String title, Integer room_id, String date, String imageUrl){

        this.title = title;
        this.room_id = room_id;
        this.date = date;
        this.imageUrl = imageUrl;

    }


    public String getTitle() {
        return title;
    }

    public int getRoom_id() {
        return room_id;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
