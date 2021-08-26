package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

public class AllReminders extends AppCompatActivity {
    RecyclerView reminderRecyclerView;
    ArrayList<ModelReminder> modelReminderArrayList;
    ReminderListAdapter adapter;
    FirebaseFirestore fStore;
    private String userID;
    String name,type,duration,date;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reminders);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        modelReminderArrayList = new ArrayList<>();
        setUpRecyclerView();
        loadUserFromFirebase();
    }

    private void setUpRecyclerView() {
        reminderRecyclerView= findViewById(R.id.recyclerViewReminder);
        reminderRecyclerView.setHasFixedSize(true);
        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadUserFromFirebase() {
        if (modelReminderArrayList.size() > 0)
            modelReminderArrayList.clear();

        fStore.collection("Alarms/"+userID+"/PersonalAlarms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            name=querySnapshot.getString("ReminderName");
                            type=querySnapshot.getString("IntervalType");
                            duration=querySnapshot.getString("Duration");
                            date=querySnapshot.getString("Date");

                                ModelReminder modelReminder= new ModelReminder(name,type,duration,date,querySnapshot.getString("DocumentID"),
                                        querySnapshot.getString("IntentID"));

                                modelReminderArrayList.add(modelReminder);
                            }

                        adapter = new ReminderListAdapter(AllReminders.this, modelReminderArrayList);
                        reminderRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AllReminders.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }
}