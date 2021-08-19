package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddGoalsHealth extends AppCompatActivity {

    public static final String TAG = "TAG AddGoal";
    SwitchCompat switchCompat;
    Button waterIntakebtn, medIntakebtn, workoutbtn, yogabtn, foodhabitbtn, sportbtn, sleepbtn, rehabilitationbrn,bodycarebtn;
    Button reminderbtn;
    EditText goalname, goalduration;
    Button setgoal;
    Button gopublic, later;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String category;
    String privacy="";

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

        goalname=findViewById(R.id.goalName);
        goalduration=findViewById(R.id.goalDuration);
        setgoal=findViewById(R.id.setgoalbtn);

        gopublic=findViewById(R.id.publicbtn);
        later=findViewById(R.id.laterbtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

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
                category="Water Intake";
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
                category="Medicine Intake";
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
                category="Yoga";
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
                category="Sports";
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
                category="Sleep";
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
                category="Rehabilitation";
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
                category="Body Care";
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
                category="Working Out";
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
                category="Food Habit";
                return true;
            }
        });

        gopublic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gopublic.setPressed(true);
                privacy="Public";
                later.setPressed(false);
                return true;
            }
        });
        later.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gopublic.setPressed(false);
                later.setPressed(true);
                privacy="";
                return true;
            }
        });

        setgoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = goalname.getText().toString();
                String duration = goalduration.getText().toString();
                if (name.isEmpty()) {
                    showError(goalname, "Name must not be empty");
                    return;
                }
                if (duration.isEmpty() || duration.equals("0")) {
                    showError(goalduration, "Duration must be at least 1 day");
                    return;
                }
                DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document();
                Map<String,Object> goal = new HashMap<>();
                goal.put("Name", name);
                goal.put("Category", "Health");
                goal.put("Subcategory", category);
                goal.put("Duration", duration);
                goal.put("Privacy",privacy);

                documentReference1.set(goal).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: goal is created");
                    }

                });
                Toast.makeText(AddGoalsHealth.this, "Goal Added", Toast.LENGTH_SHORT).show();

                goalname.setText("");
                goalduration.setText("");
                waterIntakebtn.setPressed(false);
                medIntakebtn.setPressed(false);
                yogabtn.setPressed(false);
                sportbtn.setPressed(false);
                sleepbtn.setPressed(false);
                rehabilitationbrn.setPressed(false);
                bodycarebtn.setPressed(false);
                workoutbtn.setPressed(false);
                foodhabitbtn.setPressed(false);
                gopublic.setPressed(false);
                later.setPressed(false);
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

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddGoalsHealth.this, MainActivity.class);
        startActivity(intent);
    }
}