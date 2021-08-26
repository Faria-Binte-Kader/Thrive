package com.example.thrive;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ReminderGoalsAlertReceiver extends BroadcastReceiver {
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    int hour;
    String shour;
    String userID,goalname;
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar=Calendar.getInstance();
        /*int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekt;
        if(dayOfWeek==1) dayOfWeekt="Sunday";
        else if(dayOfWeek==2) dayOfWeekt="Monday";
        else if(dayOfWeek==3) dayOfWeekt="Tuesday";
        else if(dayOfWeek==4) dayOfWeekt="Wednesday";
        else if(dayOfWeek==5) dayOfWeekt="Thursday";
        else if(dayOfWeek==6) dayOfWeekt="Friday";
        else dayOfWeekt="Saturday";*/
       /* int hour= calendar.get(Calendar.HOUR_OF_DAY);
        String shour= Integer.toString(hour);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        fStore.collection("Alarms/"+userID+"/PersonalAlarms")
                .whereEqualTo("Hour", shour)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            goalname= querySnapshot.getString("ReminderName");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Tag", "failed");
                    }
                });*/
        final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.samsung_relax);
        mediaPlayer.start();
        /*NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notifyalarm")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Reminder!")
                .setAutoCancel(true)
                .setContentText("goal")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(100, builder.build());*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notifyalarm")
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setContentTitle("Reminder")
                    .setAutoCancel(true)
                    .setContentText("Head over to your reminder-list in Thrive.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            notificationManager.notify(Integer.parseInt(intent.getData().getSchemeSpecificPart()),builder.build());
        }

    }
}
