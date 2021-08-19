package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReminderGoals extends AppCompatActivity {

    public Button satbtn, sunbtn, monbtn, tuesbtn, wedbtn, thursbtn, fribtn, setreminderbtn;
    Button addbtn,removebtn;
    private ArrayList<LinearLayout> entryList = new ArrayList<>();
    private FlexboxLayout mLayout;
    EditText remindername;
    String time="";
    int hour, minute;
    String sat,sun,mon,tues,wed, thurs, fri;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String remindernamet;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_goals);
        mLayout=findViewById(R.id.remindergoalslayout);
        satbtn=findViewById(R.id.satbtn);
        sunbtn=findViewById(R.id.sunbtn);
        monbtn=findViewById(R.id.monbtn);
        wedbtn=findViewById(R.id.tuesbtn);
        tuesbtn=findViewById(R.id.wedbtn);
        thursbtn=findViewById(R.id.thursbtn);
        fribtn=findViewById(R.id.fribtn);
        remindername=findViewById(R.id.remindername);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        newEntry(mLayout);

        addbtn=findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEntry(mLayout);
            }
        });
        removebtn=findViewById(R.id.removebtn);
        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });

        setreminderbtn=findViewById(R.id.setreminderbtn);
        setreminderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlarmTime();
                if(remindername.getText()!=null)
                { remindernamet=remindername.getText().toString();}
                if(satbtn.isSelected() && satbtn.isActivated()) sat="1";
                else sat="0";
                if(sunbtn.isSelected() && sunbtn.isActivated()) sun="1";
                else sun="0";
                if(monbtn.isSelected() && monbtn.isActivated()) mon="1";
                else mon="0";
                if(tuesbtn.isSelected() && tuesbtn.isActivated()) tues="1";
                else tues="0";
                if(wedbtn.isSelected() && wedbtn.isActivated()) wed="1";
                else wed="0";
                if(thursbtn.isSelected() && thursbtn.isActivated()) thurs="1";
                else thurs="0";
                if(fribtn.isSelected() && fribtn.isActivated()) fri="1";
                else fri="0";

                DocumentReference documentReference = fStore.collection("Alarms").document(userId).collection("PersonalAlarms").document();
                Map<String, Object> reminder = new HashMap<>();
                reminder.put("UserID", userId);
                reminder.put("Time", time);
                reminder.put("Saturday", sat);
                reminder.put("Sunday", sun);
                reminder.put("Monday", mon);
                reminder.put("Tuesday", tues);
                reminder.put("Wednesday", wed);
                reminder.put("Thursday", thurs);
                reminder.put("Friday", fri);
                reminder.put("ReminderName", remindernamet);
                documentReference.set(reminder).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Tag", "Reminder Added");
                    }
                });

                for(int i=1; i<=time.length(); i=i+6)
                {
                    if(i+5<=time.length())
                    {String a= time.substring(i,i+2);
                        hour = Integer.parseInt(a);
                        String b= time.substring(i+3,i+5);
                        minute= Integer.parseInt(b);
                        Calendar calendar=Calendar.getInstance();
                        if(sat.equals("1"))
                        {
                            int days = Calendar.SATURDAY + (7 - calendar.get(Calendar.DAY_OF_WEEK));
                            //calendar.set(Calendar.DAY_OF_WEEK,4);
                            calendar.add(Calendar.DATE, days);
                        calendar.set(Calendar.HOUR_OF_DAY,hour);
                        calendar.set(Calendar.MINUTE,minute);
                        calendar.set(Calendar.SECOND,00);
                        Log.d("Tag", " "+minute+" "+hour);
                        Intent intent= new Intent(ReminderGoals.this,ReminderGoalsAlertReceiver.class);
                        PendingIntent pendingIntent= PendingIntent.getBroadcast(ReminderGoals.this,0,intent,0);
                        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                        long timeAtButtonClick= System.currentTimeMillis();
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 7*(AlarmManager.INTERVAL_DAY),pendingIntent);}
                        if(sun.equals("1"))
                        {
                            int days = Calendar.SUNDAY+ (7 - calendar.get(Calendar.DAY_OF_WEEK));
                            //calendar.set(Calendar.DAY_OF_WEEK,4);
                            calendar.add(Calendar.DATE, days);
                            calendar.set(Calendar.HOUR_OF_DAY,hour);
                            calendar.set(Calendar.MINUTE,minute);
                            calendar.set(Calendar.SECOND,00);
                            Log.d("Tag", " "+minute+" "+hour);
                            Intent intent= new Intent(ReminderGoals.this,ReminderGoalsAlertReceiver.class);
                            PendingIntent pendingIntent= PendingIntent.getBroadcast(ReminderGoals.this,0,intent,0);
                            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                            long timeAtButtonClick= System.currentTimeMillis();
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 7*(AlarmManager.INTERVAL_DAY),pendingIntent);}
                        if(mon.equals("1"))
                        {
                           int days = Calendar.MONDAY + (7 - calendar.get(Calendar.DAY_OF_WEEK));
                            //calendar.set(Calendar.DAY_OF_WEEK,4);
                           calendar.add(Calendar.DATE, days);
                            calendar.set(Calendar.HOUR_OF_DAY,hour);
                            calendar.set(Calendar.MINUTE,minute);
                            calendar.set(Calendar.SECOND,00);
                            Log.d("Tag", " "+minute+" "+hour);
                            Intent intent= new Intent(ReminderGoals.this,ReminderGoalsAlertReceiver.class);
                            PendingIntent pendingIntent= PendingIntent.getBroadcast(ReminderGoals.this,0,intent,0);
                            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                            long timeAtButtonClick= System.currentTimeMillis();
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 7*(AlarmManager.INTERVAL_DAY),pendingIntent);}
                        if(tues.equals("1"))
                        {
                            int days = Calendar.TUESDAY + (7 - calendar.get(Calendar.DAY_OF_WEEK));
                            //calendar.set(Calendar.DAY_OF_WEEK,4);
                            calendar.add(Calendar.DATE, days);
                            calendar.set(Calendar.HOUR_OF_DAY,hour);
                            calendar.set(Calendar.MINUTE,minute);
                            calendar.set(Calendar.SECOND,00);
                            Log.d("Tag", " "+minute+" "+hour);
                            Intent intent= new Intent(ReminderGoals.this,ReminderGoalsAlertReceiver.class);
                            PendingIntent pendingIntent= PendingIntent.getBroadcast(ReminderGoals.this,0,intent,0);
                            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                            long timeAtButtonClick= System.currentTimeMillis();
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 7*(AlarmManager.INTERVAL_DAY),pendingIntent);}

                        if(wed.equals("1"))
                        {
                            int days = Calendar.WEDNESDAY + (7 - calendar.get(Calendar.DAY_OF_WEEK));
                            //calendar.set(Calendar.DAY_OF_WEEK,4);
                            calendar.add(Calendar.DATE, days);
                            calendar.set(Calendar.HOUR_OF_DAY,hour);
                            calendar.set(Calendar.MINUTE,minute);
                            calendar.set(Calendar.SECOND,00);
                            Log.d("Tag", " "+minute+" "+hour);
                            Intent intent= new Intent(ReminderGoals.this,ReminderGoalsAlertReceiver.class);
                            PendingIntent pendingIntent= PendingIntent.getBroadcast(ReminderGoals.this,0,intent,0);
                            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7,pendingIntent);}


                        if(thurs.equals("1"))
                        {
                           // int days = Calendar.THURSDAY + (7 - calendar.get(Calendar.DAY_OF_WEEK));
                            //calendar.set(Calendar.DAY_OF_WEEK,4);
                           // calendar.add(Calendar.DATE, days);
                            calendar.set(Calendar.HOUR_OF_DAY,hour);
                            calendar.set(Calendar.MINUTE,minute);
                            calendar.set(Calendar.SECOND,00);
                            Intent intent= new Intent(ReminderGoals.this,ReminderGoalsAlertReceiver.class);
                            PendingIntent pendingIntent= PendingIntent.getBroadcast(ReminderGoals.this,0,intent,0);
                            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 7*(AlarmManager.INTERVAL_DAY),pendingIntent);
                        Log.d("Tag", " "+minute+" "+hour+"thursday");}

                        if(fri.equals("1"))
                        {
                            int days = Calendar.FRIDAY + (7 - calendar.get(Calendar.DAY_OF_WEEK));
                            //calendar.set(Calendar.DAY_OF_WEEK,4);
                            calendar.add(Calendar.DATE, days);
                            calendar.set(Calendar.HOUR_OF_DAY,hour);
                            calendar.set(Calendar.MINUTE,minute);
                            calendar.set(Calendar.SECOND,00);
                            Intent intent= new Intent(ReminderGoals.this,ReminderGoalsAlertReceiver.class);
                            PendingIntent pendingIntent= PendingIntent.getBroadcast(ReminderGoals.this,0,intent,0);
                            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 7*(AlarmManager.INTERVAL_DAY),pendingIntent);
                            Log.d("Tag", " "+minute+" "+hour+"fri");}

                    }
                }


            }
        });

        satbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(satbtn.isSelected() && satbtn.isActivated())
                {
                  satbtn.setSelected(false);
                    satbtn.setActivated(false);
                }
                else
                {satbtn.setSelected(true);
                    satbtn.setActivated(true);
                }
            }
        });

        sunbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sunbtn.isSelected() && sunbtn.isActivated())
                {
                    sunbtn.setSelected(false);
                    sunbtn.setActivated(false);
                }
                else
                {sunbtn.setSelected(true);
                    sunbtn.setActivated(true);
                }
            }
        });
        monbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monbtn.isSelected() && monbtn.isActivated())
                {
                    monbtn.setSelected(false);
                    monbtn.setActivated(false);
                }
                else
                {monbtn.setSelected(true);
                    monbtn.setActivated(true);
                }
            }
        });
        tuesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tuesbtn.isSelected() && tuesbtn.isActivated())
                {
                    tuesbtn.setSelected(false);
                    tuesbtn.setActivated(false);
                }
                else
                {tuesbtn.setSelected(true);
                    tuesbtn.setActivated(true);
                }
            }
        });
        wedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wedbtn.isSelected() && wedbtn.isActivated())
                {
                    wedbtn.setSelected(false);
                    wedbtn.setActivated(false);
                }
                else
                {wedbtn.setSelected(true);
                    wedbtn.setActivated(true);
                }
            }
        });
        thursbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thursbtn.isSelected() && thursbtn.isActivated())
                {
                    thursbtn.setSelected(false);
                    thursbtn.setActivated(false);
                }
                else
                {thursbtn.setSelected(true);
                    thursbtn.setActivated(true);
                }
            }
        });
        fribtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fribtn.isSelected() && fribtn.isActivated())
                {
                    fribtn.setSelected(false);
                    fribtn.setActivated(false);
                }
                else
                {fribtn.setSelected(true);
                    fribtn.setActivated(true);
                }
            }
        });

    }


    private void newEntry (FlexboxLayout mLayout) {
        LayoutInflater mInf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mInf = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View v = mInf.inflate(R.layout.reminderentry, mLayout, false);
        LinearLayout entry = v.findViewById(R.id.entry);
        entryList.add(entry);
        mLayout.addView(entry);

    }

    private void remove(){
        if (entryList.size() > 1) {
            mLayout.removeView(entryList.get(entryList.size() - 1));
            entryList.remove(entryList.size() - 1);
        }
    }

    public void getAlarmTime()
    {
        EditText hourt, minutet;
        String hour, minute;
        for (LinearLayout l : entryList) {
            hourt= l.findViewById(R.id.hourtext);
            minutet= l.findViewById(R.id.minutetext);
            hour=hourt.getText().toString();
            minute=minutet.getText().toString();
            if(hour.equals("")) hour="16";
            if(minute.equals("")) minute="00";
            time= time+" "+hour+":"+ minute;
            }
        time=time+" ";
        }

    }

