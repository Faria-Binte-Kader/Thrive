package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<Goals> goalsArrayList;
    GoalsAdapter adapter;

    Button dropdownmenu, focusbutton, pedometerbutton;
    Button profileBtn;
    private TextView cointext;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        cointext=findViewById(R.id.cointextview);

        DocumentReference documentReference = fStore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    cointext.setText(value.getString("Coin"));
                }
            }
        });

        dropdownmenu=(Button)findViewById(R.id.threedots);
        dropdownmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu po=new PopupMenu(MainActivity.this,dropdownmenu); //for drop-down menu
                po.getMenuInflater().inflate(R.menu.upperleftmenu,po.getMenu());

                po.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    public boolean onMenuItemClick(@NonNull  MenuItem item)
                    {
                        int id = item.getItemId();

                        if(id == R.id.logout) {
                            startActivity(new Intent(getApplicationContext(), Logout.class));
                        }
                        return true;
                    }
                });

                po.show();
            }
        });


        focusbutton=(Button)findViewById(R.id.focusbutton);
        focusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Focus_Session.class);
                startActivity(intent);
            }
        });

        pedometerbutton=(Button)findViewById(R.id.pedometerbutton);
        pedometerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pedometer.class);
                startActivity(intent);
            }
        });



        BottomNavigationView nav= findViewById(R.id.bottomnavview);
        findViewById(R.id.bottomnavview).setBackground(null);
        nav.setSelectedItemId(R.id.placefolder);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
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

        goalsArrayList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.goalsRV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        fStore.collection("UserGoalInfo").document(userID).collection("Goals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Goals goals = new Goals(querySnapshot.getString("Progress"),
                                    querySnapshot.getString("Name"),
                                    querySnapshot.getString("id"));
                            goalsArrayList.add(goals);
                        }
                        adapter = new GoalsAdapter(MainActivity.this, goalsArrayList);
                        mRecyclerView.setAdapter(adapter);
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


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upperleftmenu, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.)
        {
            Intent intent = new Intent(MainActivity.this, .class);
            startActivity(intent);
        }
        else if(id==R.id.)
        {
            startActivity(new Intent(getApplicationContext(), .class));
        }


        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.getItem(2).setEnabled(false);
        return true;
    }
}