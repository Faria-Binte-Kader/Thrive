package com.example.thrive;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.samsung_relax);
        mediaPlayer.start();
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notifyalarm")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Time's up!")
                .setContentText("Congratulations! You have completed your focus session.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200, builder.build());

    }
}
