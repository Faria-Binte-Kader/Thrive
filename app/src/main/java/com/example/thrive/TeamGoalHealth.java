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
import android.util.Pair;
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

public class TeamGoalHealth extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinnerGoalCatHealth,spinnerfriend1, spinnerfriend2;
    public static final String TAG = "TAG AddGoal";
    SwitchCompat switchCompat;
    EditText goalname, goalduration;
    Button setgoal,goToTeamGoal;
    Button gopublic, later;
    Button reminderbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String category;
    String privacy = "";
    String text;
    List<String> friends = new ArrayList<String>();
    String spinner_value1="",spinner_value2="",spinner_subCategory_value="";

    String goalID;
    String userName, userProPicURL, GoalURL, friendname, friendid;
    List<String> URL_list = new ArrayList<String>();

    ArrayList<FriendList> friendListArrayList = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_goal_health);
        spinnerGoalCatHealth = findViewById(R.id.spinnerGoalCatHealth);
        spinnerGoalCatHealth.setOnItemSelectedListener(this);
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

        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fwaterintake.png?alt=media&token=44be3217-520b-4373-aa14-d53a175a4d26");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fmedicineintake.png?alt=media&token=2cba95d8-6470-42d3-bbc8-8c0e046f8f84");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fworkout.png?alt=media&token=320f0e46-5841-4be3-b556-16fdb0782546");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Ffoodhabit.png?alt=media&token=8d37d1fe-25c1-4dc6-af8d-290ab67d5bd1");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fyoga.png?alt=media&token=1ac633fc-e621-4d14-bf87-14343d590edd");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fsport.png?alt=media&token=d2db35fb-881f-4fa5-ad01-e6751ea3319c");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fsleep.png?alt=media&token=8bf17670-a7e8-4b3e-9f5a-87ba35c98866");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Fbodycare.png?alt=media&token=d420c96d-af89-49f5-a681-c4b75f44522b");
        URL_list.add("https://firebasestorage.googleapis.com/v0/b/thrive-b1a4e.appspot.com/o/GoalLogos%2Frehab.png?alt=media&token=15027308-f6a7-4c54-b854-6a493835042e");

        reminderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamGoalHealth.this, ReminderGoals.class);
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
                    userName = value.getString("Name");

                    fStore.collection("Friends").document(userID).collection(userName + "Friends")
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
                                    Toast.makeText(TeamGoalHealth.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
                    if (spinner_value1.equals("No Friends") && spinner_value2.equals("No Friends")) {
                        text = "You must choose a friend for a team goal.";
                        Toast.makeText(TeamGoalHealth.this, text, Toast.LENGTH_SHORT).show();
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
                        final String spinner_value = spinnerGoalCatHealth.getSelectedItem().toString();
                        if (spinner_value.equals("Water Intake")) {
                            GoalURL = URL_list.get(0);
                        }
                        if (spinner_value.equals("Medicine Intake")) {
                            GoalURL = URL_list.get(1);
                        }
                        if (spinner_value.equals("Working Out")) {
                            GoalURL = URL_list.get(2);
                        }
                        if (spinner_value.equals("Food Habits")) {
                            GoalURL = URL_list.get(3);
                        }
                        if (spinner_value.equals("Yoga")) {
                            GoalURL = URL_list.get(4);
                        }
                        if (spinner_value.equals("Sports")) {
                            GoalURL = URL_list.get(5);
                        }
                        if (spinner_value.equals("Sleep")) {
                            GoalURL = URL_list.get(6);
                        }
                        if (spinner_value.equals("Body Care")) {
                            GoalURL = URL_list.get(7);
                        }
                        if (spinner_value.equals("Rehabilitation")) {
                            GoalURL = URL_list.get(8);
                        }
                        if (spinner_value.equals("No category")) {
                            Toast.makeText(TeamGoalHealth.this, "You must add a category for your goal", Toast.LENGTH_SHORT).show();
                        } else {
                            DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document();
                            Map<String, Object> goal = new HashMap<>();
                            goal.put("Name", name);
                            goal.put("Category", "Health");
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
                            Toast.makeText(TeamGoalHealth.this, text, Toast.LENGTH_SHORT).show();
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
                                    goal.put("Category", "Health");
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
                        if(spinner_value2.equals("No Friends"))
                        {text = "You have set a team goal with " + spinner_value1;}
                        else if(spinner_value1.equals("No Friends") && !spinner_value2.equals("No friends"))
                        {
                            text = "You have set a team goal with " + spinner_value2;
                        }else
                        {text = "You have set a team goal with " + spinner_value1 + " and " + spinner_value2;}
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
                        final String spinner_value = spinnerGoalCatHealth.getSelectedItem().toString();
                        if (spinner_value.equals("Water Intake")) {
                            GoalURL = URL_list.get(0);
                        }
                        if (spinner_value.equals("Medicine Intake")) {
                            GoalURL = URL_list.get(1);
                        }
                        if (spinner_value.equals("Working Out")) {
                            GoalURL = URL_list.get(2);
                        }
                        if (spinner_value.equals("Food Habits")) {
                            GoalURL = URL_list.get(3);
                        }
                        if (spinner_value.equals("Yoga")) {
                            GoalURL = URL_list.get(4);
                        }
                        if (spinner_value.equals("Sports")) {
                            GoalURL = URL_list.get(5);
                        }
                        if (spinner_value.equals("Sleep")) {
                            GoalURL = URL_list.get(6);
                        }
                        if (spinner_value.equals("Body Care")) {
                            GoalURL = URL_list.get(7);
                        }
                        if (spinner_value.equals("Rehabilitation")) {
                            GoalURL = URL_list.get(8);
                        }
                        if (spinner_value.equals("No category")) {
                            Toast.makeText(TeamGoalHealth.this, "You must add a category for your goal", Toast.LENGTH_SHORT).show();
                        } else {
                            DocumentReference documentReference1 = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document();
                            Map<String, Object> goal = new HashMap<>();
                            goal.put("Name", name);
                            goal.put("Category", "Health");
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
                            Toast.makeText(TeamGoalHealth.this, text, Toast.LENGTH_SHORT).show();
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
                                    goal.put("Category", "Health");
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
                                    goal.put("Category", "Health");
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

                if (spinnerGoalCatHealth.getSelectedItem() != null) {
                    spinner_subCategory_value = spinnerGoalCatHealth.getSelectedItem().toString();
                }
            }
        });

        switchCompat.setChecked(false);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    Intent intent = new Intent(TeamGoalHealth.this, TeamGoalProductivity.class);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TeamGoalHealth.this, MainActivity.class);
        startActivity(intent);
    }

    public void sendTeamGoalNotification(String receiverID) {
        String userID = FirebaseAuth.getInstance().getUid();
        fStore.collection("User").document(userID)
                .addSnapshotListener(TeamGoalHealth.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot valueSender, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (valueSender != null) {
                            fStore.collection("User").document(receiverID)
                                    .addSnapshotListener(TeamGoalHealth.this, new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot valueReceiver, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                                            if (valueReceiver != null) {
                                                String receiverToken = valueReceiver.getString("Token") + "";

                                                NotificationSenderFriendRequest notificationSender = new NotificationSenderFriendRequest(
                                                        receiverToken + "",
                                                        "Team Goal",
                                                        valueSender.getString("Name") + " has set a team goal with you",
                                                        getApplicationContext(), TeamGoalHealth.this);

                                                notificationSender.sendNotification();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }


}