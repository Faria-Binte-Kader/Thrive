package com.example.thrive;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AddGoalsProductivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public static final String TAG = "TAG AddGoal";
    private Spinner spinnerGoalCatProductivity;
    SwitchCompat switchCompat;
    Button studybtn, hobbybtn, softbtn, techbtn, reminderbtn;
    EditText goalname, goalduration;
    Button setgoal,goToTeamGoal;
    Button gopublic, later;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String category;
    String privacy = "";
    List<String> URL_list = new ArrayList<String>();

    String goalID, GoalURL;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goals_productivity);
        spinnerGoalCatProductivity = findViewById(R.id.spinnerGoalCatProductivity);
        spinnerGoalCatProductivity.setOnItemSelectedListener(this);
        switchCompat = findViewById(R.id.switchgoal);
        reminderbtn = findViewById(R.id.reminderbtn);
        goToTeamGoal = findViewById(R.id.addfriendsbtnproductivity);

        goalname = findViewById(R.id.goalName);
        goalduration = findViewById(R.id.goalDuration);
        setgoal = findViewById(R.id.setgoalbtn);

        gopublic = findViewById(R.id.publicbtn);
        later = findViewById(R.id.laterbtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateToday = dateFormat.format(calendar.getTime());

        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fhobby.png?alt=media&token=58daf7a2-22a2-4afe-969d-ee6497b1b66a");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fsoftskill.png?alt=media&token=fea34dc7-a2b0-450b-9fae-602c370970c7");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fstudy.png?alt=media&token=53db3ced-6220-40dc-858e-12a8090ec146");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Ftechnicalskill.png?alt=media&token=31b68b43-0ea6-4938-aa61-671b3641f3bd");

        reminderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGoalsProductivity.this, ReminderGoals.class);
                startActivity(intent);
            }
        });

        goToTeamGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGoalsProductivity.this, TeamGoalProductivity.class);
                startActivity(intent);
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
                privacy = "";
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
                final String spinner_value = spinnerGoalCatProductivity.getSelectedItem().toString();
                if (spinner_value.equals("Hobby")) {
                    GoalURL=URL_list.get(0);
                }
                if (spinner_value.equals("Soft Skill Development")) {
                    GoalURL=URL_list.get(1);
                }
                if (spinner_value.equals("Study")) {
                    GoalURL=URL_list.get(2);
                }
                if (spinner_value.equals("Technical Skill")) {
                    GoalURL=URL_list.get(3);
                }
                if (spinner_value.equals("No category"))
                         {
                    Toast.makeText(AddGoalsProductivity.this, "You must add a category for your goal", Toast.LENGTH_SHORT).show();
                } else {
                    DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document();
                    Map<String, Object> goal = new HashMap<>();
                    goal.put("Name", name);
                    goal.put("Category", "Productivity");
                    goal.put("Subcategory", category);
                    goal.put("Duration", duration);
                    goal.put("Privacy", privacy);
                    goal.put("Days", "0");
                    goal.put("Progress", "0");
                    goal.put("id", documentReference1.getId());
                    goal.put("DateToday", dateToday);
                    goal.put("Flag", "0");
                    goal.put("GoalURL", GoalURL);

                    documentReference1.set(goal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: goal is created");
                        }
                    });

                    Toast.makeText(AddGoalsProductivity.this, "Goal Added", Toast.LENGTH_SHORT).show();

                    fAuth = FirebaseAuth.getInstance();
                    //userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

                    //create goal achieved stat for 12 months
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    String year_str = Integer.toString(year);

                    userID = fAuth.getCurrentUser().getUid();
                    //DocumentReference documentReference_goal_stat = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document();
                    goalID = documentReference1.getId();


                    DocumentReference documentReference_goalAchieved = fStore.collection("Statistics").document(userID).collection("Goals").document(goalID);
                    Map<String, Object> goal_achieved = new HashMap<>();
                    goal_achieved.put("Year", year_str);
                    goal_achieved.put("GoalName", name);
                    goal_achieved.put("GoalID", goalID);

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

                    if (privacy == "Public") {

                        userID = fAuth.getCurrentUser().getUid();

                        DocumentReference documentReference_user = fStore.collection("User").document(userID);
                        documentReference_user.addSnapshotListener(AddGoalsProductivity.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value != null) {
                                    DocumentReference documentReference_publicGoal = fStore.collection("PublicGoals").document(goalID);
                                    Map<String, Object> public_goal = new HashMap<>();
                                    public_goal.put("GoalID", goalID);
                                    public_goal.put("GoalName", name.toUpperCase());
                                    public_goal.put("Category", "PRODUCTIVITY");
                                    public_goal.put("Subcategory", category.toUpperCase());
                                    public_goal.put("UserID", userID);
                                    public_goal.put("UserName", value.getString("Name"));
                                    public_goal.put("UserProPicURL", value.getString("ProPicUrl"));
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
                    gopublic.setPressed(false);
                    later.setPressed(false);
                }
            }
        });

        switchCompat.setChecked(true);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                } else {
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddGoalsProductivity.this, MainActivity.class);
        startActivity(intent);
    }
}