package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.cinemaapp.Adapter.SeatMapAdapter;
import com.example.cinemaapp.Models.Reservation;
import com.example.cinemaapp.Models.Seat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddNewFoglalas extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private FirebaseFirestore db;

    private EditText nameEditText, placesEditText, movieEditText, dateEditText;

    private int seatIcon;

    private GridView gridSeatMap;

    ArrayList<Seat> seatArrayList = new ArrayList<Seat>();

    SeatMapAdapter adapter;

    private FirebaseUser user;

    private static final String CHANNEL_ID = "cinema_notification_channel";

    private ReservationNotification handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_foglalas);
        db = FirebaseFirestore.getInstance();
        nameEditText = findViewById(R.id.name_editText);
        placesEditText = findViewById(R.id.reservedPlaces_editText);
        movieEditText = findViewById(R.id.movieTitle);
        dateEditText = findViewById(R.id.date);

        seatIcon = R.drawable.cinema_chair_not_selected;
        gridSeatMap = (GridView) findViewById(R.id.seatMapGrid);

        totalSeat(20);

        movieEditText.setText(getIntent().getStringExtra("title"));
        dateEditText.setText(getIntent().getStringExtra("date"));

        user = FirebaseAuth.getInstance().getCurrentUser();

        handler = new ReservationNotification(this);

        if(user != null){
            Log.d(AddNewFoglalas.class.getName(), "Auth user!");
        }else{
            Log.d(AddNewFoglalas.class.getName(), "Unauth user!");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


    }

    private void totalSeat(int n) {



        for(int i=1; i<=n; i++){
            seatArrayList.add(new Seat(R.drawable.cinema_chair_not_selected, "Seat "+i));
        }

        adapter = new SeatMapAdapter(this, seatArrayList);
        gridSeatMap.setAdapter(adapter);
        gridSeatMap.setOnItemClickListener(this);

    }


    public void addReserve(View view) {

        CollectionReference dbReservations = db.collection("reservations");

        Reservation reservation = new Reservation(generateId(),nameEditText.getText().toString(), placesEditText.getText().toString(), movieEditText.getText().toString(), dateEditText.getText().toString(), user.getEmail());

        if(nameEditText.getText().toString().equals("")){
            Toast.makeText(AddNewFoglalas.this, "Töltsd ki a név mezőt!", Toast.LENGTH_SHORT).show();
            return;
        }else if(placesEditText.getText().toString().equals("")){
            Toast.makeText(AddNewFoglalas.this, "Foglalj le helyet!", Toast.LENGTH_SHORT).show();
            return;
        }

        dbReservations.document(reservation.getId()).set(reservation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                String movie = movieEditText.getText().toString();
                Log.d("AddNewFoglalas", "Doc added with ID: "+reservation.getId());
                Toast.makeText(AddNewFoglalas.this, "Sikeres foglalás!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNewFoglalas.this, MainActivity.class);
                overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
                handler.notificateForReservation(movie);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("AddNewFoglalas", "Error adding document", e);
                Toast.makeText(AddNewFoglalas.this, "Foglalás rögzítése sikertelen!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String generateId(){

        final String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        String autoId = "";

        for (int i=0; i<20; i++){
            autoId+= character.charAt(
                    (int) Math.floor(Math.random() * character.length())
            );
        }

        return autoId;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Seat seat = seatArrayList.get(position);
        int seatCompare = seat.getImgId();
        if(seatCompare == seatIcon){
            seatSelected(position);
        }
        else{
            seatDeselected(position);
        }

    }

    private void seatDeselected(int position) {

        seatArrayList.remove(position);
        int i = position + 1;
        seatArrayList.add(position, new Seat(seatIcon, "Seat" +i));
        String places = placesEditText.getText().toString();
        String new_places = places.replace(String.valueOf(position), "");
        String new_places2 = new_places.replaceFirst(", ", "");
        placesEditText.setText(new_places2);
        adapter.notifyDataSetChanged();


    }

    private void seatSelected(int position) {

        seatArrayList.remove(position);
        seatArrayList.add(position, new Seat(R.drawable.cinema_chair_selected, "Selected"));
        if(!placesEditText.getText().toString().equals("")){
            String places = placesEditText.getText().toString();
            places+=", "+String.valueOf(position);
            placesEditText.setText(places);
        }else{
            placesEditText.setText(String.valueOf(position));
        }
        adapter.notifyDataSetChanged();



    }
}