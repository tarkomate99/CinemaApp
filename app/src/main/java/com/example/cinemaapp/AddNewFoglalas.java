package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cinemaapp.Models.Reservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class AddNewFoglalas extends AppCompatActivity {

    private FirebaseFirestore db;

    private EditText nameEditText, placesEditText, movieEditText, dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_foglalas);
        db = FirebaseFirestore.getInstance();
        nameEditText = findViewById(R.id.name_editText);
        placesEditText = findViewById(R.id.reservedPlaces_editText);
        movieEditText = findViewById(R.id.movieTitle);
        dateEditText = findViewById(R.id.date);
    }

    public void addReserve(View view) {

        CollectionReference dbReservations = db.collection("reservations");

        Reservation reservation = new Reservation(nameEditText.getText().toString(), placesEditText.getText().toString(), movieEditText.getText().toString(), dateEditText.getText().toString());


        dbReservations
                .add(reservation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("AddNewFoglalas", "Doc added with ID: "+documentReference.getId());
                Toast.makeText(AddNewFoglalas.this, "Sikeres foglalás!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("AddNewFoglalas", "Error adding document", e);
                Toast.makeText(AddNewFoglalas.this, "Foglalás rögzítése sikertelen!", Toast.LENGTH_SHORT).show();
            }
        });



    }
}