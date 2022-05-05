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
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemaapp.Adapter.MovieAdapter;
import com.example.cinemaapp.Models.Movie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.DocumentViewChangeSet;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView movies;
    ArrayList<Movie> data;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = findViewById(R.id.movieList);
        data = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        
        loadDatainListView();
        
        
        
    }

    private void loadDatainListView() {

        db.collection("movies").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){

                            List<DocumentSnapshot> list  = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list){

                                Movie movie = d.toObject(Movie.class);

                                data.add(movie);

                            }
                            MovieAdapter adapter = new MovieAdapter(MainActivity.this, data);

                            movies.setAdapter(adapter);

                        }else{
                            Toast.makeText(MainActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });

        movies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, AddNewFoglalas.class);
                TextView title = (TextView)view.findViewById(R.id.movie_title);
                TextView date = (TextView)view.findViewById(R.id.movie_date);
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("date", date.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void addNewReserve(View view) {
        Intent intent = new Intent(this, AddNewFoglalas.class);
        TextView title = (TextView)findViewById(R.id.movie_title);
        TextView date = (TextView)findViewById(R.id.movie_date);
        intent.putExtra("title", title.getText().toString());
        intent.putExtra("date", date.getText().toString());
        startActivity(intent);
    }

}