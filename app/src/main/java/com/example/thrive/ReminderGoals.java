package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ReminderGoals extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

   // public Button satbtn, sunbtn, monbtn, tuesbtn, wedbtn, thursbtn, fribtn, setreminderbtn;
   // Button addbtn,removebtn;
  //  private ArrayList<LinearLayout> entryList = new ArrayList<>();
   // private FlexboxLayout mLayout;
    EditText remindername,hourt,minutet,durationt;
    public Button setreminderbtn;
    int hour, minute,duration;
    String shour, sminute,sduration;
    //String sat,sun,mon,tues,wed, thurs, fri;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String remindernamet;
    String spinner_value;
    String type;
    int randomnumber;
    private Spinner spinnerReminderDuration;


    List<Calendar> calendarList = new ArrayList<>();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_goals);
        hourt = findViewById(R.id.hourtext2);
        minutet = findViewById(R.id.minutetext2);
        durationt = findViewById(R.id.reminderduration);
        remindername = findViewById(R.id.remindername);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        spinnerReminderDuration = findViewById(R.id.spinnerreminderdurationType);
        spinnerReminderDuration.setOnItemSelectedListener(this);

       /* AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(ReminderGoals.this,AlertReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(ReminderGoals.this,0,intent,0);

        alarmManager.cancel(pendingIntent);*/

        setreminderbtn = findViewById(R.id.setreminderbtn);
        setreminderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                randomnumber=random.nextInt(100000);
                if (remindername.getText() != null) {
                    remindernamet = remindername.getText().toString();
                }
                if (hourt.getText() != null) {
                    shour = hourt.getText().toString();
                }
                else shour="0";
                if (minutet.getText() != null) {
                    sminute= minutet.getText().toString();
                }
                else sminute="0";
                if (durationt.getText() == null) {
                    sduration = "0";

                }
                else sduration= durationt.getText().toString();

                hour=Integer.parseInt(shour);
                minute=Integer.parseInt(sminute);
                duration=Integer.parseInt(sduration);

                spinner_value = spinnerReminderDuration.getSelectedItem().toString();

                if (spinner_value.contains("hour(s)")) {
                    duration=duration*60;
                    type="hour(s)";
                }
                else if (spinner_value.contains("No repeat")) {
                    duration=0;
                    type="No repeat";
                }
                else
                { type="minute(s)";}

                Calendar calendar = Calendar.getInstance();
                Date date= calendar.getTime();
                String datet=date.toString();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 00);
                Log.d("Tag", " " + calendar.getTime());


                DocumentReference documentReference = fStore.collection("Alarms").document(userId).collection("PersonalAlarms").document();
                Map<String, Object> reminder = new HashMap<>();
                reminder.put("UserID", userId);
                reminder.put("Hour", shour);
                reminder.put("Minute", sminute);
                reminder.put("Duration", sduration);
                reminder.put("ReminderName", remindernamet);
                reminder.put("Date", datet);
                reminder.put("IntervalType", type);
                reminder.put("DocumentID", documentReference.getId());
                reminder.put("IntentID", String.valueOf(randomnumber));

                documentReference.set(reminder).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ReminderGoals.this, "Reminder Added", Toast.LENGTH_SHORT).show();
                        Log.d("Tag", shour + sminute+sduration);
                    }
                });


                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(ReminderGoals.this, ReminderGoalsAlertReceiver.class);
                intent.setData(Uri.parse(String.valueOf(randomnumber)));
                intent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION);
                PendingIntent pi = PendingIntent.getBroadcast(ReminderGoals.this, 0, intent, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*60*duration , pi); // Millisec * Second * Minute
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

