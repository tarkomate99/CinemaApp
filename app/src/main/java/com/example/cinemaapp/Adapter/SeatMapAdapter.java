package com.example.cinemaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cinemaapp.Models.Seat;
import com.example.cinemaapp.R;

import java.util.ArrayList;

public class SeatMapAdapter extends ArrayAdapter<Seat> {
    public SeatMapAdapter(@NonNull Context context, ArrayList<Seat> seatArrayList) {
        super(context,0, seatArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if(listitemView == null){
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.seat_item, parent, false);
        }

        Seat seat = getItem(position);
        TextView textView = listitemView.findViewById(R.id.seatText);
        ImageView seatIcon = listitemView.findViewById(R.id.seatIcon);
        textView.setText(seat.getSeat_name());
        seatIcon.setImageResource(seat.getImgId());

        return listitemView;


    }
}
