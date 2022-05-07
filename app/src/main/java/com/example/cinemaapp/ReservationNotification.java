package com.example.cinemaapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

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
        channel.setDescription("A vetítés kezdete előtt vegye át a jegyeket!");
        this.notificationManager.createNotificationChannel(channel);
    }
}
