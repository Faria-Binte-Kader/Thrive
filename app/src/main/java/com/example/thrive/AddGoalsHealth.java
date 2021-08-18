package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

public class AddGoalsHealth extends AppCompatActivity {

    SwitchCompat switchCompat;
    Button waterIntakebtn, medIntakebtn, workoutbtn, yogabtn, foodhabitbtn, sportbtn, sleepbtn, rehabilitationbrn,bodycarebtn;
    Button reminderbtn;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goals_health);
        switchCompat=findViewById(R.id.switchgoal);
        waterIntakebtn=findViewById(R.id.waterintakebtn);
        medIntakebtn=findViewById(R.id.medintakebtn);
        workoutbtn=findViewById(R.id.workout);
        yogabtn=findViewById(R.id.yogabtn);
        foodhabitbtn=findViewById(R.id.foodhabitbtn);
        sportbtn=findViewById(R.id.sportsbtn);
        sleepbtn=findViewById(R.id.sleepbtn);
        rehabilitationbrn=findViewById(R.id.rehabilitationbtn);
        bodycarebtn=findViewById(R.id.bodycarebtn);
        reminderbtn=findViewById(R.id.reminderbtn);
        reminderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGoalsHealth.this, ReminderGoals.class);
                startActivity(intent);
            }
        });

        waterIntakebtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                waterIntakebtn.setPressed(true);
                medIntakebtn.setPressed(false);
                yogabtn.setPressed(false);
                sportbtn.setPressed(false);
                sleepbtn.setPressed(false);
                rehabilitationbrn.setPressed(false);
                bodycarebtn.setPressed(false);
                workoutbtn.setPressed(false);
                foodhabitbtn.setPressed(false);
                return true;
            }
        });

        medIntakebtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                waterIntakebtn.setPressed(false);
                medIntakebtn.setPressed(true);
                yogabtn.setPressed(false);
                sportbtn.setPressed(false);
                sleepbtn.setPressed(false);
                rehabilitationbrn.setPressed(false);
                bodycarebtn.setPressed(false);
                workoutbtn.setPressed(false);
                foodhabitbtn.setPressed(false);
                return true;
            }
        });

        yogabtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                waterIntakebtn.setPressed(false);
                medIntakebtn.setPressed(false);
                yogabtn.setPressed(true);
                sportbtn.setPressed(false);
                sleepbtn.setPressed(false);
                rehabilitationbrn.setPressed(false);
                bodycarebtn.setPressed(false);
                workoutbtn.setPressed(false);
                foodhabitbtn.setPressed(false);
                return true;
            }
        });

        sportbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                waterIntakebtn.setPressed(false);
                medIntakebtn.setPressed(false);
                yogabtn.setPressed(false);
                sportbtn.setPressed(true);
                sleepbtn.setPressed(false);
                rehabilitationbrn.setPressed(false);
                bodycarebtn.setPressed(false);
                workoutbtn.setPressed(false);
                foodhabitbtn.setPressed(false);
                return true;
            }
        });

        sleepbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                waterIntakebtn.setPressed(false);
                medIntakebtn.setPressed(false);
                yogabtn.setPressed(false);
                sportbtn.setPressed(false);
                sleepbtn.setPressed(true);
                rehabilitationbrn.setPressed(false);
                bodycarebtn.setPressed(false);
                workoutbtn.setPressed(false);
                foodhabitbtn.setPressed(false);
                return true;
            }
        });

        rehabilitationbrn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                waterIntakebtn.setPressed(false);
                medIntakebtn.setPressed(false);
                yogabtn.setPressed(false);
                sportbtn.setPressed(false);
                sleepbtn.setPressed(false);
                rehabilitationbrn.setPressed(true);
                bodycarebtn.setPressed(false);
                workoutbtn.setPressed(false);
                foodhabitbtn.setPressed(false);
                return true;
            }
        });

        bodycarebtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                waterIntakebtn.setPressed(false);
                medIntakebtn.setPressed(false);
                yogabtn.setPressed(false);
                sportbtn.setPressed(false);
                sleepbtn.setPressed(false);
                rehabilitationbrn.setPressed(false);
                bodycarebtn.setPressed(true);
                workoutbtn.setPressed(false);
                foodhabitbtn.setPressed(false);
                return true;
            }
        });
        workoutbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                waterIntakebtn.setPressed(false);
                medIntakebtn.setPressed(false);
                yogabtn.setPressed(false);
                sportbtn.setPressed(false);
                sleepbtn.setPressed(false);
                rehabilitationbrn.setPressed(false);
                bodycarebtn.setPressed(false);
                workoutbtn.setPressed(true);
                foodhabitbtn.setPressed(false);
                return true;
            }
        });
        foodhabitbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                waterIntakebtn.setPressed(false);
                medIntakebtn.setPressed(false);
                yogabtn.setPressed(false);
                sportbtn.setPressed(false);
                sleepbtn.setPressed(false);
                rehabilitationbrn.setPressed(false);
                bodycarebtn.setPressed(false);
                workoutbtn.setPressed(false);
                foodhabitbtn.setPressed(true);
                return true;
            }
        });


        switchCompat.setChecked(false);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(buttonView.isChecked())
             {
                 Intent intent = new Intent(AddGoalsHealth.this, AddGoalsProductivity.class);
                 startActivity(intent);
             }
             else
             {

             }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddGoalsHealth.this, MainActivity.class);
        startActivity(intent);
    }
}