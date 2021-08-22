package com.example.thrive;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddGoalsProductivity extends AppCompatActivity {
    public static final String TAG = "TAG AddGoal";
    SwitchCompat switchCompat;
    Button studybtn, hobbybtn, softbtn, techbtn,reminderbtn;
    EditText goalname, goalduration;
    Button setgoal;
    Button gopublic, later;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String category;
    String privacy="";

    String goalID,GoalURL;

    @SuppressLint("ClickableViewAccessibility")
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

        goalname=findViewById(R.id.goalName);
        goalduration=findViewById(R.id.goalDuration);
        setgoal=findViewById(R.id.setgoalbtn);

        gopublic=findViewById(R.id.publicbtn);
        later=findViewById(R.id.laterbtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateToday=dateFormat.format(calendar.getTime());

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
                category="Study";
                GoalURL="https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fstudy.png?alt=media&token=53db3ced-6220-40dc-858e-12a8090ec146";
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
                category="Hobby";
                GoalURL="https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fhobby.png?alt=media&token=58daf7a2-22a2-4afe-969d-ee6497b1b66a";
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
                category="Soft Skill Development";
                GoalURL="https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fsoftskill.png?alt=media&token=fea34dc7-a2b0-450b-9fae-602c370970c7";
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
                category="Technical Skill Development";
                GoalURL="https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Ftechnicalskill.png?alt=media&token=31b68b43-0ea6-4938-aa61-671b3641f3bd";
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
                privacy="";
                later.setPressed(true);
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
                if((!studybtn.isPressed()) && (!hobbybtn.isPressed()) && (!softbtn.isPressed()) &&
                        (!techbtn.isPressed()))
                {
                    Toast.makeText(AddGoalsProductivity.this, "You must add a category for your goal", Toast.LENGTH_SHORT).show();
                }
                else
                {
                DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document();
                Map<String,Object> goal = new HashMap<>();
                goal.put("Name", name);
                goal.put("Category", "Productivity");
                goal.put("Subcategory", category);
                goal.put("Duration", duration);
                goal.put("Privacy",privacy);
                goal.put("Days","0");
                goal.put("Progress","0");
                goal.put("id",documentReference1.getId());
                goal.put("DateToday",dateToday);
                goal.put("Flag","0");
                goal.put("GoalURL",GoalURL);

                documentReference1.set(goal).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: goal is created");
                    }

                });

                Toast.makeText(AddGoalsProductivity.this, "Goal Added", Toast.LENGTH_SHORT).show();

                if (privacy == "Public") {

                    final String[] userName = new String[1];
                    final String[] userProPicURL = new String[1];

                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference_goal = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document();
                    goalID = documentReference_goal.getId();

                    DocumentReference documentReference_user = fStore.collection("User").document(userID);
                    documentReference_user.addSnapshotListener(AddGoalsProductivity.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(value != null){
                                userName[0] = value.getString("Name");
                                userProPicURL[0] = value.getString("ProPicUrl");
                            }
                        }
                    });

                    DocumentReference documentReference_publicGoal = fStore.collection("PublicGoals").document(goalID);
                    Map<String, Object> public_goal = new HashMap<>();
                    public_goal.put("GoalID", goalID);
                    public_goal.put("GoalName",name);
                    public_goal.put("UserID", userID);
                    public_goal.put("UserName",userName[0]);
                    public_goal.put("UserProPicURL",userProPicURL[0]);
                    documentReference_publicGoal.set(public_goal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: goal is set to public");
                        }
                    });
                }

                goalname.setText("");
                goalduration.setText("");
                studybtn.setPressed(false);
                hobbybtn.setPressed(false);
                softbtn.setPressed(false);
                techbtn.setPressed(false);
                gopublic.setPressed(false);
                later.setPressed(false);
            }}
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

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddGoalsProductivity.this, MainActivity.class);
        startActivity(intent);
    }
}