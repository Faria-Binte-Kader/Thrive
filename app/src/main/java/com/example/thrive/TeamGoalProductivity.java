package com.example.thrive;

import androidx.annotation.NonNull;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamGoalProductivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinnerGoalCatProductivity, spinnerfriend1, spinnerfriend2;
    public static final String TAG = "TAG AddGoal";
    SwitchCompat switchCompat;
    EditText goalname, goalduration;
    Button setgoal,goToTeamGoal;
    Button gopublic, later;
    Button reminderbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID,text;
    String category;
    String privacy = "";
    List<String> friends = new ArrayList<String>();

    String goalID;
    String userName, userProPicURL, GoalURL, friendname, friendid;
    String spinner_value1="",spinner_value2="",spinner_subCategory_value="";
    List<String> URL_list = new ArrayList<String>();

    ArrayList<FriendList> friendListArrayList = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_goal_productivity);
        spinnerGoalCatProductivity = findViewById(R.id.spinnerGoalCatProductivity);
        spinnerGoalCatProductivity.setOnItemSelectedListener(this);
        spinnerfriend1 = findViewById(R.id.spinnerfriend1);
        spinnerfriend1.setOnItemSelectedListener(this);
        spinnerfriend2 = findViewById(R.id.spinnerfriend2);
        spinnerfriend2.setOnItemSelectedListener(this);
        switchCompat = findViewById(R.id.switchgoal);
        goalname = findViewById(R.id.goalName);
        goalduration = findViewById(R.id.goalDuration);
        setgoal = findViewById(R.id.setgoalbtn);
        goToTeamGoal = findViewById(R.id.addfriendsbtnhealth);
        reminderbtn = findViewById(R.id.reminderbtn);
        gopublic = findViewById(R.id.publicbtn);
        later = findViewById(R.id.laterbtn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateToday = dateFormat.format(calendar.getTime());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, friends);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, friends);
        friends.add("No Friends");

        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fhobby.png?alt=media&token=58daf7a2-22a2-4afe-969d-ee6497b1b66a");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fsoftskill.png?alt=media&token=fea34dc7-a2b0-450b-9fae-602c370970c7");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fstudy.png?alt=media&token=53db3ced-6220-40dc-858e-12a8090ec146");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Ftechnicalskill.png?alt=media&token=31b68b43-0ea6-4938-aa61-671b3641f3bd");

        reminderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamGoalProductivity.this, ReminderGoals.class);
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
                later.setPressed(true);
                privacy = "";
                return true;
            }
        });

        DocumentReference documentReference = fStore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    userName=value.getString("Name");
                    Toast.makeText(TeamGoalProductivity.this, userName, Toast.LENGTH_SHORT).show();
                    fStore.collection("Friends").document(userID).collection(userName+"Friends")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot querySnapshot : task.getResult()) {
                                        friendname = querySnapshot.getString("FriendName");
                                        friendid = querySnapshot.getString("FriendID");
                                        friends.add(friendname);
                                        FriendList friendList = new FriendList(friendname, friendid);
                                        friendListArrayList.add(friendList);
                                    }

                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerfriend1.setAdapter(dataAdapter);
                                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerfriend2.setAdapter(dataAdapter);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(TeamGoalProductivity.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                    Log.v("---I---", e.getMessage());
                                }
                            });
                }
            }
        });

        setgoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerfriend1.getSelectedItem() != null && spinnerfriend2.getSelectedItem() != null) {
                    spinner_value1 = spinnerfriend1.getSelectedItem().toString();
                    spinner_value2 = spinnerfriend2.getSelectedItem().toString();
                    if (spinner_value1.equals("No friends")) {
                        text = "You must choose a friend for a team goal.";
                    } else if (spinner_value1.equals(spinner_value2)) {
                        text = "You have set a team goal with " + spinner_value1;
                        /////////////////////////////
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
                        if (spinner_value.equals("No category")) {
                            Toast.makeText(TeamGoalProductivity.this, "You must add a category for your goal", Toast.LENGTH_SHORT).show();
                        } else {
                            DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document();
                            Map<String, Object> goal = new HashMap<>();
                            goal.put("Name", name);
                            goal.put("Category", "Productivity");
                            goal.put("Subcategory", spinner_value);
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
                                goal_achieved.put(month_str, "0");
                            }

                            documentReference_goalAchieved.set(goal_achieved).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: goalAchieved stat is created");
                                }
                            });
                            Toast.makeText(TeamGoalProductivity.this, "Goal Added", Toast.LENGTH_SHORT).show();
                            goalname.setText("");
                            goalduration.setText("");
                            gopublic.setPressed(false);
                            later.setPressed(false);
                        }
                        /////////////////////////////
                        for (int i = 0; i < friendListArrayList.size(); i++) {
                            if (friendListArrayList.get(i).getName().equals(spinner_value1)) {
                                String fid = friendListArrayList.get(i).getID();
                                //////////////////////
                                if (!spinner_value.equals("No category")) {
                                    DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(fid).collection("Goals").document();
                                    Map<String, Object> goal = new HashMap<>();
                                    goal.put("Name", name);
                                    goal.put("Category", "Productivity");
                                    goal.put("Subcategory", spinner_value);
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

                                    DocumentReference documentReference_goalAchieved = fStore.collection("Statistics").document(fid).collection("Goals").document(goalID);
                                    Map<String, Object> goal_achieved = new HashMap<>();
                                    goal_achieved.put("Year", year_str);
                                    goal_achieved.put("GoalName", name);
                                    goal_achieved.put("GoalID", documentReference1.getId());

                                    for (int month = 0; month <= 11; month++) {
                                        String month_str = Integer.toString(month);
                                        goal_achieved.put(month_str, "0");
                                    }

                                    documentReference_goalAchieved.set(goal_achieved).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: goalAchieved stat is created");
                                        }
                                    });
                                }
                                /////////////////////
                            }
                        }
                    } else {
                        text = "You have set a team goal with " + spinner_value1 + " and " + spinner_value2;
                        ////////////////////////////
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
                        if (spinner_value.equals("No category")) {
                            Toast.makeText(TeamGoalProductivity.this, "You must add a category for your goal", Toast.LENGTH_SHORT).show();
                        } else {
                            DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document();
                            Map<String, Object> goal = new HashMap<>();
                            goal.put("Name", name);
                            goal.put("Category", "Productivity");
                            goal.put("Subcategory", spinner_value);
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
                                goal_achieved.put(month_str, "0");
                            }

                            documentReference_goalAchieved.set(goal_achieved).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: goalAchieved stat is created");
                                }
                            });
                            Toast.makeText(TeamGoalProductivity.this, "Goal Added", Toast.LENGTH_SHORT).show();
                            goalname.setText("");
                            goalduration.setText("");
                            gopublic.setPressed(false);
                            later.setPressed(false);
                        }
                        ///////////////////////////
                        for (int i = 0; i < friendListArrayList.size(); i++) {
                            if (friendListArrayList.get(i).getName().equals(spinner_value1)) {
                                String fid = friendListArrayList.get(i).getID();
                                //////////////////////
                                if (!spinner_value.equals("No category")) {
                                    DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(fid).collection("Goals").document();
                                    Map<String, Object> goal = new HashMap<>();
                                    goal.put("Name", name);
                                    goal.put("Category", "Productivity");
                                    goal.put("Subcategory", spinner_value);
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

                                    DocumentReference documentReference_goalAchieved = fStore.collection("Statistics").document(fid).collection("Goals").document(goalID);
                                    Map<String, Object> goal_achieved = new HashMap<>();
                                    goal_achieved.put("Year", year_str);
                                    goal_achieved.put("GoalName", name);
                                    goal_achieved.put("GoalID", documentReference1.getId());

                                    for (int month = 0; month <= 11; month++) {
                                        String month_str = Integer.toString(month);
                                        goal_achieved.put(month_str, "0");
                                    }

                                    documentReference_goalAchieved.set(goal_achieved).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: goalAchieved stat is created");
                                        }
                                    });
                                }
                                /////////////////////
                                sendTeamGoalNotification(fid);
                            }
                            if (friendListArrayList.get(i).getName().equals(spinner_value2)) {
                                String fid = friendListArrayList.get(i).getID();
                                //////////////////////
                                if (!spinner_value.equals("No category")) {
                                    DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(fid).collection("Goals").document();
                                    Map<String, Object> goal = new HashMap<>();
                                    goal.put("Name", name);
                                    goal.put("Category", "Productivity");
                                    goal.put("Subcategory", spinner_value);
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

                                    DocumentReference documentReference_goalAchieved = fStore.collection("Statistics").document(fid).collection("Goals").document(goalID);
                                    Map<String, Object> goal_achieved = new HashMap<>();
                                    goal_achieved.put("Year", year_str);
                                    goal_achieved.put("GoalName", name);
                                    goal_achieved.put("GoalID", documentReference1.getId());

                                    for (int month = 0; month <= 11; month++) {
                                        String month_str = Integer.toString(month);
                                        goal_achieved.put(month_str, "0");
                                    }

                                    documentReference_goalAchieved.set(goal_achieved).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: goalAchieved stat is created");
                                        }
                                    });
                                }
                                /////////////////////

                                sendTeamGoalNotification(fid);
                            }
                        }
                    }
                }

                if (spinnerGoalCatProductivity.getSelectedItem() != null) {
                    spinner_subCategory_value = spinnerGoalCatProductivity.getSelectedItem().toString();
                }
            }
        });

        switchCompat.setChecked(true);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                } else {
                    Intent intent = new Intent(TeamGoalProductivity.this, TeamGoalHealth.class);
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
        Intent intent = new Intent(TeamGoalProductivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void sendTeamGoalNotification(String receiverID) {
        String userID = FirebaseAuth.getInstance().getUid();
        fStore.collection("User").document(userID)
                .addSnapshotListener(TeamGoalProductivity.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot valueSender, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (valueSender != null) {
                            fStore.collection("User").document(receiverID)
                                    .addSnapshotListener(TeamGoalProductivity.this, new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot valueReceiver, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                                            if (valueReceiver != null) {
                                                String receiverToken = valueReceiver.getString("Token") + "";

                                                NotificationSenderFriendRequest notificationSender = new NotificationSenderFriendRequest(
                                                        receiverToken + "",
                                                        "Team Goal",
                                                        valueSender.getString("Name") + " has set a team goal with you",
                                                        getApplicationContext(), TeamGoalProductivity.this);

                                                notificationSender.sendNotification();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

}