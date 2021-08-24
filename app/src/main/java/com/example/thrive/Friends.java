package com.example.thrive;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Friends extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG Friends";

    FirebaseFirestore fStore;

    RecyclerView friendRecyclerView;
    ArrayList<GoalsFriend> goalsFriendArrayList;
    GoalsFriendAdapter adapter;

    private Spinner spinnerSearchBy;

    public static final String EXTRA_TEXT_ID = "com.example.application.example.EXTRA_TEXT_ID";
    public static final String EXTRA_TEXT_Name = "com.example.application.example.EXTRA_TEXT_Name";
    public static final String EXTRA_TEXT_ProPicURL = "com.example.application.example.EXTRA_TEXT_ProPicURL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        spinnerSearchBy = findViewById(R.id.spinnerSearchByType);
        spinnerSearchBy.setOnItemSelectedListener(this);


        BottomNavigationView nav = findViewById(R.id.bottomnavview2);
        findViewById(R.id.bottomnavview2).setBackground(null);
        nav.setSelectedItemId(R.id.friends);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(Friends.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.profile:
                        Intent intent2 = new Intent(Friends.this, Profile.class);
                        startActivity(intent2);
                        break;
                    case R.id.shop:
                        Intent intent3 = new Intent(Friends.this, Shop.class);
                        startActivity(intent3);
                        break;
                    case R.id.stat:
                        Intent intent4 = new Intent(Friends.this, Stat.class);
                        startActivity(intent4);
                        break;
                    case R.id.friends:
                        break;
                }
                return true;
            }
        });

        goalsFriendArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFirebase();
        loadUserFromFirebase();
        searchUserInFirebase();

    }

    private void loadUserFromFirebase() {
        if (goalsFriendArrayList.size() > 0)
            goalsFriendArrayList.clear();

        fStore.collection("PublicGoals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            GoalsFriend goalsFriend = new GoalsFriend(querySnapshot.getString("GoalID"),
                                    querySnapshot.getString("GoalName"),
                                    querySnapshot.getString("Category"),
                                    querySnapshot.getString("Subcategory"),
                                    querySnapshot.getString("UserID"),
                                    querySnapshot.getString("UserName"),
                                    querySnapshot.getString("UserProPicURL"));
                            goalsFriendArrayList.add(goalsFriend);
                        }
                        adapter = new GoalsFriendAdapter(Friends.this, goalsFriendArrayList);
                        friendRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Friends.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    private void searchUserInFirebase() {
        SearchView searchView = findViewById(R.id.searchViewFriends);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                final String spinner_value = spinnerSearchBy.getSelectedItem().toString();

                if (spinner_value.contains("Goal Category")) {

                    if (goalsFriendArrayList.size() > 0)
                        goalsFriendArrayList.clear();
                    fStore.collection("PublicGoals")
                            .whereGreaterThanOrEqualTo("Category", s.toUpperCase())
                            .orderBy("Category").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot querySnapshot : task.getResult()) {
                                        GoalsFriend goalsFriend = new GoalsFriend(querySnapshot.getString("GoalID"),
                                                querySnapshot.getString("GoalName"),
                                                querySnapshot.getString("Category"),
                                                querySnapshot.getString("Subcategory"),
                                                querySnapshot.getString("UserID"),
                                                querySnapshot.getString("UserName"),
                                                querySnapshot.getString("UserProPicURL"));
                                        goalsFriendArrayList.add(goalsFriend);
                                    }
                                    adapter = new GoalsFriendAdapter(Friends.this, goalsFriendArrayList);
                                    friendRecyclerView.setAdapter(adapter);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Friends.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                    Log.v("---I---", e.getMessage());
                                }
                            });
                } else if (spinner_value.contains("Goal Subcategory")) {
                    if (goalsFriendArrayList.size() > 0)
                        goalsFriendArrayList.clear();

                    fStore.collection("PublicGoals")
                            .whereGreaterThanOrEqualTo("Subcategory", s.toUpperCase())
                            .orderBy("Subcategory").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot querySnapshot : task.getResult()) {
                                        GoalsFriend goalsFriend = new GoalsFriend(querySnapshot.getString("GoalID"),
                                                querySnapshot.getString("GoalName"),
                                                querySnapshot.getString("Category"),
                                                querySnapshot.getString("Subcategory"),
                                                querySnapshot.getString("UserID"),
                                                querySnapshot.getString("UserName"),
                                                querySnapshot.getString("UserProPicURL"));
                                        goalsFriendArrayList.add(goalsFriend);
                                    }
                                    adapter = new GoalsFriendAdapter(Friends.this, goalsFriendArrayList);
                                    friendRecyclerView.setAdapter(adapter);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Friends.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                    Log.v("---I---", e.getMessage());
                                }
                            });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void setUpFirebase() {
        fStore = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        friendRecyclerView = findViewById(R.id.recyclerViewFriends);
        friendRecyclerView.setHasFixedSize(true);
        friendRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Friends.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       // ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void openFriendProfile(String friendID, String friendName, String friendProPicURL) {
        Intent intent = new Intent(Friends.this, FriendProfile.class);
        intent.putExtra(EXTRA_TEXT_ID, friendID);
        intent.putExtra(EXTRA_TEXT_Name, friendName);
        intent.putExtra(EXTRA_TEXT_ProPicURL, friendProPicURL);
        startActivity(intent);
    }

}