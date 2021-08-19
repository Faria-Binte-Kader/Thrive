package com.example.thrive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
    String userID,goalname;
    @Override
    public void onReceive(Context context, Intent intent) {
       /* Calendar calendar=Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekt;
        if(dayOfWeek==1) dayOfWeekt="Sunday";
        else if(dayOfWeek==2) dayOfWeekt="Monday";
        else if(dayOfWeek==3) dayOfWeekt="Tuesday";
        else if(dayOfWeek==4) dayOfWeekt="Wednesday";
        else if(dayOfWeek==5) dayOfWeekt="Thursday";
        else if(dayOfWeek==6) dayOfWeekt="Friday";
        else dayOfWeekt="Saturday";
        int hour= calendar.get(Calendar.HOUR_OF_DAY);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        fStore.collection("Alarms/"+userID+"/PersonalAlarms")
                .whereEqualTo(dayOfWeekt, "1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String name,time;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            name= querySnapshot.getString("ReminderName");
                            time= querySnapshot.getString("Time");
                           /* for(int i=1; i<time.length(); i=i+5)
                            {
                                if(i+2<time.length())
                                {String a= time.substring(i,i+2);
                                int num = Integer.parseInt(a);
                                if(num==hour || num==hour-1 || num==hour+1)
                                { break;}}
                            }
                            goalname=name;
                        }
                        Log.d("Tag", goalname);
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
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notifyalarm")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Reminder!")
                .setAutoCancel(true)
                .setContentText("goal")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200, builder.build());

    }
}
