package com.example.cinemaapp.Models;

import android.graphics.Bitmap;

public class Seat {

    private int imgId;
    private String seat_name;

    public Seat() {
    }

    public Seat(int imgId, String seat_name) {

        this.imgId = imgId;
        this.seat_name = seat_name;

    }


    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getSeat_name() {
        return seat_name;
    }

    public void setSeat_name(String seat_name) {
        this.seat_name = seat_name;
    }
}


