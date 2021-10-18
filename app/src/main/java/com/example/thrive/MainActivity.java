package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<Goals> goalsArrayList;
    GoalsAdapter adapter;

    Button dropdownmenu, focusbutton, pedometerbutton, seeremindersbtn, notifybtn;
    Button profileBtn;
    private TextView cointext;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID, id;
    private String Fdate;
    String dateToday;

    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";

    /*public void removeItem(int position) {
        final String id = goalsArrayList.get(position).getGoalID();
        fStore.collection("UserGoalInfo").document(userID).collection("Goals").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
        fStore.collection("PublicGoals").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
        Toast.makeText(MainActivity.this, "Goal Deleted", Toast.LENGTH_SHORT).show();
        goalsArrayList.remove(position);
        adapter.notifyItemRemoved(position);

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goalsArrayList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.goalsRV);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        cointext = findViewById(R.id.cointextview);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateToday = dateFormat.format(calendar.getTime());

        DocumentReference documentReference = fStore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    cointext.setText(value.getString("Coin"));
                }
            }
        });

        dropdownmenu = (Button) findViewById(R.id.threedots);
        dropdownmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu po = new PopupMenu(MainActivity.this, dropdownmenu); //for drop-down menu
                po.getMenuInflater().inflate(R.menu.upperleftmenu, po.getMenu());

                po.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(@NonNull MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.logout) {
                            startActivity(new Intent(getApplicationContext(), Logout.class));
                        }
                        return true;
                    }
                });

                po.show();
            }
        });


        focusbutton = (Button) findViewById(R.id.focusbutton);
        focusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Focus_Session.class);
                startActivity(intent);
            }
        });

        pedometerbutton = (Button) findViewById(R.id.pedometerbutton);
        pedometerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pedometer.class);
                startActivity(intent);
            }
        });

        seeremindersbtn = (Button) findViewById(R.id.seeremindersbtn);
        seeremindersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllReminders.class);
                startActivity(intent);
            }
        });
        notifybtn = (Button) findViewById(R.id.notificationbtn);
        notifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyNotifications.class);
                startActivity(intent);
            }
        });


        BottomNavigationView nav = findViewById(R.id.bottomnavview);
        findViewById(R.id.bottomnavview).setBackground(null);
        nav.setSelectedItemId(R.id.placefolder);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.placefolder:
                        nav.setEnabled(false);
                        break;
                    case R.id.profile:
                        Intent intent = new Intent(MainActivity.this, Profile.class);
                        startActivity(intent);
                        break;
                    case R.id.shop:
                        Intent intent2 = new Intent(MainActivity.this, Shop.class);
                        startActivity(intent2);
                        break;
                    case R.id.stat:
                        Intent intent3 = new Intent(MainActivity.this, Stat.class);
                        startActivity(intent3);
                        break;
                    case R.id.friends:
                        Intent intent4 = new Intent(MainActivity.this, Friends.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });

        FloatingActionButton addgoalsbtn = findViewById(R.id.fab);
        addgoalsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGoalsHealth.class);
                startActivity(intent);
            }
        });

        fStore.collection("UserGoalInfo").document(userID).collection("Goals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Goals goals = new Goals(querySnapshot.getString("Progress"),
                                    querySnapshot.getString("Name"),
                                    querySnapshot.getString("id"),
                                    querySnapshot.getString("DateToday"),
                                    querySnapshot.getString("Flag"),
                                    querySnapshot.getString("GoalURL"));

                            goalsArrayList.add(goals);
                            Fdate = querySnapshot.getString("DateToday");
                            id = querySnapshot.getString("id");

                            if (!dateToday.equals(Fdate)) {
                                fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(id)
                                        .update("DateToday", dateToday)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.v("Tag", dateToday);
                                            }
                                        });
                                fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(id)
                                        .update("Flag", "0")
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.v("Tag", "flag");
                                            }
                                        });
                            }
                        }
                        adapter = new GoalsAdapter(MainActivity.this, goalsArrayList);
                        mRecyclerView.setAdapter(adapter);
                        /*adapter.setOnItemClickListener(new GoalsAdapter.OnItemClickListener() {
                            @Override
                            public void onDeleteClick(int position) {
                                removeItem(position);
                            }
                        });*/
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });

    }

    public void loadgoal(String id) {
        Intent intent = new Intent(MainActivity.this, GoalView.class);
        intent.putExtra(EXTRA_TEXT, id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(2).setEnabled(false);
        return true;
    }

    public void onBackPressed() {
        finishAffinity();
    }
}