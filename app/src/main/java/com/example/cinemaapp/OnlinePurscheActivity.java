package com.example.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OnlinePurscheActivity extends AppCompatActivity {

    private String personCount;

    private Integer price;

    private TextView priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pursche);

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            personCount = extras.getString("personCount");
            priceText = (TextView) findViewById(R.id.personCount);
            price = 2000*Integer.parseInt(personCount);
            priceText.setText("Fizetendő: "+price.toString()+" Ft");
        }




    }

    public void pay(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
    }
}