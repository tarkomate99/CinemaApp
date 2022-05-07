package com.example.cinemaapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cinemaapp.Models.Reservation;
import com.example.cinemaapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReservationsAdapter extends ArrayAdapter<Reservation> {
    public ReservationsAdapter(@NonNull Context context, ArrayList<Reservation> data) {
        super(context, 0, data);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.reservation_item,parent,false);
        }

        TextView titleTV = (TextView) convertView.findViewById(R.id.res_title);
        TextView placesTV = (TextView) convertView.findViewById(R.id.res_places);
        TextView dateTV = (TextView) convertView.findViewById(R.id.res_date);
        TextView id = (TextView) convertView.findViewById(R.id.res_id);
        Reservation reservation = getItem(position);

        id.setText(reservation._getId());
        titleTV.setText(reservation.getMovie());
        placesTV.setText(reservation.getPlaces());
        dateTV.setText(reservation.getDate());

        return convertView;
    }
}
