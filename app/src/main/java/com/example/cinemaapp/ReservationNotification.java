package com.example.cinemaapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ReservationNotification {

    private NotificationManager notificationManager;
    private Context context;
    private final static String CHANNEL_ID = "cinema_notification_channel";

    public ReservationNotification(Context context){
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Cinema Notification", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.GREEN);
        channel.setDescription("A vetítés kezdete előtt 30 perccel vegye át a jegyeket!");
        this.notificationManager.createNotificationChannel(channel);
    }

    public void notificateForReservation(String movie){
        Intent intent = new Intent(context, ReservationsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(movie)
                .setContentText("A vetítés kezdete előtt 30 perccel vegye át a jegyeket!")
                .setSmallIcon(R.drawable.ic_movie)
                .setContentIntent(pendingIntent);

        this.notificationManager.notify(0,builder.build());


    }
}
