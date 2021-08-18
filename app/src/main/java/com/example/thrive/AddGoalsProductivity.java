package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

public class AddGoalsProductivity extends AppCompatActivity {
    SwitchCompat switchCompat;
    Button studybtn, hobbybtn, softbtn, techbtn,reminderbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goals_productivity);
        switchCompat=findViewById(R.id.switchgoal);
        studybtn=findViewById(R.id.studybtn);
        hobbybtn=findViewById(R.id.hobbybtn);
        softbtn=findViewById(R.id.softskillbtn);
        techbtn=findViewById(R.id.technicalbtn);
        reminderbtn=findViewById(R.id.reminderbtn);
        reminderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGoalsProductivity.this, ReminderGoals.class);
                startActivity(intent);
            }
        });

        studybtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                studybtn.setPressed(true);
                hobbybtn.setPressed(false);
                softbtn.setPressed(false);
                techbtn.setPressed(false);
                return true;
            }
        });

        hobbybtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                studybtn.setPressed(false);
                hobbybtn.setPressed(true);
                softbtn.setPressed(false);
                techbtn.setPressed(false);
                return true;
            }
        });

        softbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                studybtn.setPressed(false);
                hobbybtn.setPressed(false);
                softbtn.setPressed(true);
                techbtn.setPressed(false);
                return true;
            }
        });

        techbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                studybtn.setPressed(false);
                hobbybtn.setPressed(false);
                softbtn.setPressed(false);
                techbtn.setPressed(true);
                return true;
            }
        });
        switchCompat.setChecked(true);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                }
                else
                {
                    Intent intent = new Intent(AddGoalsProductivity.this, AddGoalsHealth.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddGoalsProductivity.this, MainActivity.class);
        startActivity(intent);
    }
}