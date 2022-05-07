package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.cinemaapp.Adapter.ReservationsAdapter;
import com.example.cinemaapp.Models.Reservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity {


    ListView reservations;
    ArrayList<Reservation> data;
    FirebaseFirestore db;
    ReservationsAdapter adapter;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        reservations = findViewById(R.id.reservationsList);
        data = new ArrayList<>();
        db = FirebaseFirestore.getInstance();



        user = FirebaseAuth.getInstance().getCurrentUser();

        loadDataInTableRow();

        if(user==null){
            finish();
        }

    }

    private void loadDataInTableRow() {

        db.collection("reservations").whereEqualTo("email", user.getEmail()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list){

                                Reservation reservation = d.toObject(Reservation.class);

                                data.add(reservation);

                            }
                            adapter = new ReservationsAdapter(ReservationsActivity.this,data);

                            reservations.setAdapter(adapter);

                        }else{
                            Toast.makeText(ReservationsActivity.this, "No data found...", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReservationsActivity.this, "Failed to load data...", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void deleteReservation(View view) {

        

    }
}