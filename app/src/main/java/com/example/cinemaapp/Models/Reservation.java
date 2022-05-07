package com.example.cinemaapp.Models;

public class Reservation {

    private String name;
    private String places;
    private String movie;
    private String date;
    private String email;

    public Reservation(){}

    public Reservation(String name, String places, String movie, String date, String email){

        this.name=name;
        this.places=places;
        this.movie=movie;
        this.date=date;
        this.email=email;

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

    public String getEmail() {
        return email;
    }
}
