package com.example.cinemaapp.Models;

public class Reservation {

    private String name;
    private String places;
    private String movie;
    private String date;

    public Reservation(){}

    public Reservation(String name, String places, String movie, String date){

        this.name=name;
        this.places=places;
        this.movie=movie;
        this.date=date;

    }

    public String getName() {
        return name;
    }

    public String getPlaces() {
        return places;
    }

    public String getMovie() {
        return movie;
    }

    public String getDate() {
        return date;
    }
}
