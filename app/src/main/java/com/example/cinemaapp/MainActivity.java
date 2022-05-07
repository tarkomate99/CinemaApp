package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemaapp.Adapter.MovieAdapter;
import com.example.cinemaapp.Models.Movie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    MovieAdapter adapter;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = findViewById(R.id.movieList);
        data = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        loadDataInListView();


        user = FirebaseAuth.getInstance().getCurrentUser();


        
        
    }

    private void loadDataInListView() {

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
                            adapter = new MovieAdapter(MainActivity.this, data);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.login:
                Log.d("MainActivity", "Login clicked!");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Log.d("MainActivity", "Logout clicked!");
                FirebaseAuth.getInstance().signOut();
                return true;
            case R.id.register:
                Log.d("MainActivity", "Register clicked!");
                Intent intent1 = new Intent(this, RegisterActivity.class);
                startActivity(intent1);
                return true;
            case R.id.user_reservations:
                Intent intent2 = new Intent(this, ReservationsActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        return super.onPrepareOptionsMenu(menu);
    }
}