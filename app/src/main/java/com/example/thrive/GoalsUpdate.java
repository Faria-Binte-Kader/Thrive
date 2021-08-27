package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GoalsUpdate extends AppCompatActivity {

    public static final String TAG = "TAG_UPDATE_GOAL";

    Button updateBtn;
    Button gopublic, later;
    private EditText editName, editDuration;

    private String userID;

    String goalID = "";
    String privacy = "";

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_update);

        Intent intent = getIntent();
        goalID = intent.getStringExtra(MainActivity.EXTRA_TEXT);

        updateBtn = findViewById(R.id.updategoalbtn);
        editName = findViewById(R.id.goalUpdateName);
        editDuration = findViewById(R.id.goalUpdateDuration);

        gopublic = findViewById(R.id.publicbtn);
        later = findViewById(R.id.laterbtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document(goalID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    editName.setText(value.getString("Name"));
                    editDuration.setText(value.getString("Duration"));
                    privacy = value.getString("Privacy");
                }
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

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGoalInfo();
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void updateGoalInfo() {
        final String name = editName.getText().toString().trim();
        final String duration = editDuration.getText().toString().trim();

        if (name.isEmpty()) {
            showError(editName, "Name must not be empty");
            return;
        }
        if (duration.isEmpty() || duration.equals("0")) {
            showError(editDuration, "Duration must be at least 1 day");
            return;
        } else {
            fStore.collection("UserGoalInfo").document(userID).collection("Goals").document(goalID)
                    .update("Name", name)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Toast.makeText(GoalsUpdate.this, "Updated Name", Toast.LENGTH_SHORT).show();
                        }
                    });
            fStore.collection("UserGoalInfo").document(userID).collection("Goals").document(goalID)
                    .update("Duration", duration)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Toast.makeText(GoalsUpdate.this, "Updated Duration", Toast.LENGTH_SHORT).show();
                        }
                    });

            DocumentReference doc2 = fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID);
            doc2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                String cnt, duration, progress;

                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document2 = task.getResult();
                        if (document2.exists()) {
                            cnt = document2.getString("Days");
                            duration = document2.getString("Duration");
                            int temp = Integer.parseInt(cnt);
                            int temp2 = Integer.parseInt(duration);
                            int temp3 = (temp * 100) / temp2;
                            progress = String.valueOf(temp3);
                            fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID)
                                    .update("Progress", progress)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                        }
                    } else {

                    }
                }
            });

            fStore.collection("UserGoalInfo").document(userID).collection("Goals").document(goalID)
                    .update("Privacy", privacy)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Toast.makeText(GoalsUpdate.this, "Updated Privacy", Toast.LENGTH_SHORT).show();
                        }
                    });

            if (privacy.equals("")) {
                fStore.collection("PublicGoals").document(goalID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
            } else {
                DocumentReference documentReference_user = fStore.collection("User").document(userID);
                documentReference_user.addSnapshotListener(GoalsUpdate.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            String userName = value.getString("Name");
                            String userProPicURL = value.getString("ProPicUrl");


                            fStore.collection("UserGoalInfo")
                                    .document(userID).collection("Goals").document(goalID)
                                    .addSnapshotListener(GoalsUpdate.this, new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                                            if(value != null){
                                                DocumentReference documentReference_publicGoal = fStore.collection("PublicGoals").document(goalID);
                                                Map<String, Object> public_goal = new HashMap<>();
                                                public_goal.put("GoalID", goalID);
                                                public_goal.put("GoalName", name.toUpperCase());
                                                public_goal.put("Category", value.getString("Category").toUpperCase());
                                                public_goal.put("Subcategory", value.getString("Subcategory").toUpperCase());
                                                public_goal.put("UserID", userID);
                                                public_goal.put("UserName", userName.toUpperCase());
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
                    }
                });
            }
            Toast.makeText(GoalsUpdate.this, "Updated Goal", Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}