package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

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
    String userName, userProPicURL, GoalURL, friendname;
    String spinner_value1="",spinner_value2="",spinner_subCategory_value="";

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
                                        friendname=querySnapshot.getString("FriendName");
                                        friends.add(friendname);
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
        if(spinnerfriend1.getSelectedItem()!=null && spinnerfriend2.getSelectedItem()!=null)
        {  spinner_value1 = spinnerfriend1.getSelectedItem().toString();
            spinner_value2 = spinnerfriend2.getSelectedItem().toString();
            if(spinner_value1.equals("No friends"))
            {
                text="You must choose a friend for a team goal.";
            }
            else if (spinner_value1.equals(spinner_value2)) {
                text="You have set a team goal with "+spinner_value1;
            }
            else
            {
                text="You have set a team goal with "+spinner_value1+" and "+spinner_value2;
            }
        }


        if(spinnerGoalCatProductivity.getSelectedItem()!=null)
        {
            spinner_subCategory_value = spinnerGoalCatProductivity.getSelectedItem().toString();
        }
    }
});
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
}