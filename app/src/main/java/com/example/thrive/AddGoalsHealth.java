package com.example.thrive;

import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddGoalsHealth extends AppCompatActivity {

    public static final String TAG = "TAG AddGoal";
    SwitchCompat switchCompat;
    Button waterIntakebtn, medIntakebtn, workoutbtn, yogabtn, foodhabitbtn, sportbtn, sleepbtn, rehabilitationbrn, bodycarebtn;
    Button reminderbtn;
    EditText goalname, goalduration;
    Button setgoal;
    Button gopublic, later;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String category;
    String privacy = "";

    String goalID;
    String userName, userProPicURL, GoalURL;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goals_health);
        switchCompat = findViewById(R.id.switchgoal);
        waterIntakebtn = findViewById(R.id.waterintakebtn);
        medIntakebtn = findViewById(R.id.medintakebtn);
        workoutbtn = findViewById(R.id.workout);
        yogabtn = findViewById(R.id.yogabtn);
        foodhabitbtn = findViewById(R.id.foodhabitbtn);
        sportbtn = findViewById(R.id.sportsbtn);
        sleepbtn = findViewById(R.id.sleepbtn);
        rehabilitationbrn = findViewById(R.id.rehabilitationbtn);
        bodycarebtn = findViewById(R.id.bodycarebtn);
        reminderbtn = findViewById(R.id.reminderbtn);

        goalname = findViewById(R.id.goalName);
        goalduration = findViewById(R.id.goalDuration);
        setgoal = findViewById(R.id.setgoalbtn);

        gopublic = findViewById(R.id.publicbtn);
        later = findViewById(R.id.laterbtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateToday = dateFormat.format(calendar.getTime());

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
                category = "Water Intake";
                GoalURL = "https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fwaterintake.png?alt=media&token=44be3217-520b-4373-aa14-d53a175a4d26";
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
                category = "Medicine Intake";
                GoalURL = "https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fmedicineintake.png?alt=media&token=2cba95d8-6470-42d3-bbc8-8c0e046f8f84";
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
                category = "Yoga";
                GoalURL = "https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fyoga.png?alt=media&token=1ac633fc-e621-4d14-bf87-14343d590edd";
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
                category = "Sports";
                GoalURL = "https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fsport.png?alt=media&token=d2db35fb-881f-4fa5-ad01-e6751ea3319c";
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
                category = "Sleep";
                GoalURL = "https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fsleep.png?alt=media&token=8bf17670-a7e8-4b3e-9f5a-87ba35c98866";
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
                category = "Rehabilitation";
                GoalURL = "https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Frehab.png?alt=media&token=15027308-f6a7-4c54-b854-6a493835042e";
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
                category = "Body Care";
                GoalURL = "https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fbodycare.png?alt=media&token=d420c96d-af89-49f5-a681-c4b75f44522b";
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
                category = "Working Out";
                GoalURL = "https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fworkout.png?alt=media&token=320f0e46-5841-4be3-b556-16fdb0782546";
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
                category = "Food Habit";
                GoalURL = "https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Ffoodhabit.png?alt=media&token=8d37d1fe-25c1-4dc6-af8d-290ab67d5bd1";
                return true;
            }
        });

        gopublic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gopublic.setPressed(true);
                privacy = "Public";
                later.setPressed(false);
                return true;
            }
        });
        later.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gopublic.setPressed(false);
                later.setPressed(true);
                privacy = "";
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
                if (Integer.parseInt(duration) > 365) {
                    showError(goalduration, "Please give a duration of not more than a year");
                    return;
                }
                if ((!waterIntakebtn.isPressed()) && (!medIntakebtn.isPressed()) && (!yogabtn.isPressed()) &&
                        (!sportbtn.isPressed()) && (!sleepbtn.isPressed()) && (!rehabilitationbrn.isPressed()) && (!bodycarebtn.isPressed()) &&
                        (!workoutbtn.isPressed()) && (!foodhabitbtn.isPressed())) {
                    Toast.makeText(AddGoalsHealth.this, "You must add a category for your goal", Toast.LENGTH_SHORT).show();
                } else {
                    DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document();
                    Map<String, Object> goal = new HashMap<>();
                    goal.put("Name", name);
                    goal.put("Category", "Health");
                    goal.put("Subcategory", category);
                    goal.put("Duration", duration);
                    goal.put("Privacy", privacy);
                    goal.put("Days", "0");
                    goal.put("Progress", "0");
                    goal.put("id", documentReference1.getId());
                    goal.put("DateToday", dateToday);
                    goal.put("Flag", "0");
                    goal.put("GoalURL", GoalURL);

                    goalID = documentReference1.getId();

                    documentReference1.set(goal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: goal is created");
                        }

                    });

                    //create goal achieved stat for 12 months
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    String year_str = Integer.toString(year);


                    DocumentReference documentReference_goalAchieved = fStore.collection("Statistics").document(fAuth.getUid()).collection("Goals").document(goalID);
                    Map<String, Object> goal_achieved = new HashMap<>();
                    goal_achieved.put("Year", year_str);
                    goal_achieved.put("GoalName", name);
                    goal_achieved.put("GoalID", documentReference1.getId());

                    for (int month = 0; month <= 11; month++) {
                        String month_str = Integer.toString(month);
                        goal_achieved.put(month_str,"0");
                    }

                    documentReference_goalAchieved.set(goal_achieved).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: goalAchieved stat is created");
                        }
                    });




                    Toast.makeText(AddGoalsHealth.this, "Goal Added", Toast.LENGTH_SHORT).show();

                    if (privacy == "Public") {

                        userID = fAuth.getCurrentUser().getUid();

                        DocumentReference documentReference_user = fStore.collection("User").document(userID);
                        documentReference_user.addSnapshotListener(AddGoalsHealth.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value != null) {
                                    userName = value.getString("Name");
                                    userProPicURL = value.getString("ProPicUrl");
                                    DocumentReference documentReference_publicGoal = fStore.collection("PublicGoals").document(goalID);
                                    Map<String, Object> public_goal = new HashMap<>();
                                    public_goal.put("GoalID", goalID);
                                    public_goal.put("GoalName", name.toUpperCase());
                                    public_goal.put("Category", "HEALTH");
                                    public_goal.put("Subcategory", category.toUpperCase());
                                    public_goal.put("UserID", userID);
                                    public_goal.put("UserName", userName);
                                    public_goal.put("UserProPicURL", userProPicURL);
                                    documentReference_publicGoal.set(public_goal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: goal is set to public");
                                        }
                                    });
                                }
                            }
                        });

                    }


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
            }

        });

        switchCompat.setChecked(false);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    Intent intent = new Intent(AddGoalsHealth.this, AddGoalsProductivity.class);
                    startActivity(intent);
                } else {

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