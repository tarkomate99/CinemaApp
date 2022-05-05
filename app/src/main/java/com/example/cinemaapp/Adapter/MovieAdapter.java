package com.example.cinemaapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cinemaapp.Models.Movie;
import com.example.cinemaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {


    public MovieAdapter(@NonNull Context context, ArrayList<Movie> data){

        super(context,0,data);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.movie_item,parent,false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.movie_title);
        TextView roomIdTextView = (TextView) convertView.findViewById(R.id.movie_room_id);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.movie_date);
        ImageView movieImageView = (ImageView) convertView.findViewById(R.id.movieImage);

        Movie movie = getItem(position);

        titleTextView.setText(movie.getTitle());
        roomIdTextView.setText(String.valueOf(movie.getRoom_id()));
        dateTextView.setText(movie.getDate());
        Picasso.get().load(movie.getImageUrl()).into(movieImageView);

        return convertView;

    }
}
