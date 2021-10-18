package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GoalView extends AppCompatActivity {

    MainActivity mainActivity;

    String goalID = "";

    String TAG = "TAG GoalView";

    String month_str = "";

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    private String userID;

    public TextView progress, name;
    public ProgressBar progressBar;
    public Button updateprogress, editgoal, deletegoal;
    public ImageView goalpicture;

    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_view);

        Intent intent = getIntent();
        goalID = intent.getStringExtra(MainActivity.EXTRA_TEXT);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        progress = findViewById(R.id.progress);
        name = findViewById(R.id.progressgoalname);
        progressBar = findViewById(R.id.progress_bar);
        goalpicture = findViewById(R.id.goalpicture);
        updateprogress = findViewById(R.id.updateprogress_btn);
        editgoal = findViewById(R.id.editgoal_btn);
        deletegoal = findViewById(R.id.deletegoal_btn);

        DocumentReference documentReference = fStore.collection("UserGoalInfo").document(userID).collection("Goals").document(goalID);
        documentReference.addSnapshotListener(GoalView.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    String url = value.getString("GoalURL");
                    String prog = value.getString("Progress");
                    String flag = value.getString("Flag");
                    String nam = value.getString("Name");

                    name.setText(nam);
                    progress.setText(prog);

                    Picasso.get().load(url).into(goalpicture);
                    if(prog!=null) {
                        if (prog.equals("Completed!")) {
                            progressBar.setProgress(100);
                            progress.setText(prog);
                        } else {
                            int strTointProgress = Integer.parseInt(prog);
                            progressBar.setProgress(strTointProgress);
                            progress.setText(prog + "%");
                        }
                    }

                    updateprogress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (flag.equals("0")) {
                                DocumentReference doc = fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID);
                                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    String cnt;

                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document2 = task.getResult();
                                            if (document2.exists()) {
                                                cnt = document2.getString("Days");
                                                int temp = Integer.parseInt(cnt);
                                                temp = temp + 1;
                                                cnt = String.valueOf(temp);
                                                fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID)
                                                        .update("Days", cnt)
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

                                DocumentReference doc2 = fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID);
                                doc2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    String cnt2, d, p;

                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document2 = task.getResult();
                                            if (document2.exists()) {
                                                cnt2 = document2.getString("Days");
                                                d = document2.getString("Duration");
                                                int temp = Integer.parseInt(cnt2);
                                                temp = temp + 1;
                                                int temp2 = Integer.parseInt(d);
                                                int temp3 = (temp * 100) / temp2;
                                                if (temp3 < 100) {
                                                    p = String.valueOf(temp3);
                                                    int proint = Integer.parseInt(p);
                                                    progressBar.setProgress(proint);

                                                } else if (temp3 >= 100) {
                                                    p = "Completed!";
                                                    progressBar.setProgress(100);
                                                }
                                                fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID)
                                                        .update("Progress", p)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        });
                                                fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID)
                                                        .update("Flag", "1")
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

                                //update goal achieved stat by 1 day
                                Calendar calendar = Calendar.getInstance();
                                int month = calendar.get(Calendar.MONTH);
                                month_str = Integer.toString(month);


                                Log.d(TAG, "Month is " + month_str);


                                DocumentReference documentReference_prevGoalAchieved = fStore.collection("Statistics").document(fAuth.getCurrentUser().getUid())
                                        .collection("Goals").document(goalID);

                                documentReference_prevGoalAchieved.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    String goalAchieved_str, prev_goalAchieved_str;

                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {

                                            //Log.d(TAG, "Success ");


                                            DocumentSnapshot document3 = task.getResult();
                                            if (document3.exists()) {
                                                prev_goalAchieved_str = document3.getString(month_str);
                                                int prev_goalAchieved = Integer.parseInt(prev_goalAchieved_str);
                                                int goalAchieved = prev_goalAchieved + 1;
                                                goalAchieved_str = String.valueOf(goalAchieved);

                                                //Log.d(TAG, "Current values is " + goalAchieved_str);


                                                fStore.collection("Statistics").document(fAuth.getUid()).collection("Goals").document(goalID)
                                                        .update(month_str, goalAchieved_str)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "Stat updated for " + month_str);
                                                            }
                                                        });
                                            }
                                        } else {
                                            Log.d(TAG, "Error ");
                                        }
                                    }

                                });

                                name.setText(nam);

                                if (prog.equals("Completed!")) {
                                    progressBar.setProgress(100);
                                    progress.setText(prog);
                                } else {
                                    int strTointProgress = Integer.parseInt(prog);
                                    progressBar.setProgress(strTointProgress);
                                    progress.setText(prog + "%");
                                }
                            } else {
                                Toast.makeText(v.getContext(), "You already updated your daily progress once.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    editgoal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updategoal(goalID);
                        }
                    });

                    deletegoal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Toast.makeText(GoalsAdapter.this, "Goal Deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            Intent intent = new Intent(GoalView.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                }
                //Toast.makeText(Profile.this, "Password Changed ", Toast.LENGTH_SHORT).show();
                // if(imageuri!=null)
                //{ Picasso.get().load(imageuri).into(imageView);}
            }
        });
    }

    public void updategoal(String s) {
        Intent intent = new Intent(GoalView.this, GoalsUpdate.class);
        intent.putExtra(EXTRA_TEXT, s);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GoalView.this, MainActivity.class);
        startActivity(intent);
    }

}