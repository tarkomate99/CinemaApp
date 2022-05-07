package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.cinemaapp.Adapter.ReservationsAdapter;
import com.example.cinemaapp.Models.Reservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

    private CollectionReference mReservations;


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
                            Toast.makeText(ReservationsActivity.this, "Nem található foglalás!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReservationsActivity.this, "Failed to load data...", Toast.LENGTH_LONG).show();
            }
        });

        reservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                db.collection("reservations").document(data.get(i).getId()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ReservationsActivity.this, "Sikeres foglalás törlés!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ReservationsActivity.this, MainActivity.class);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReservationsActivity.this, "Sikertelen foglalás törlés!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}