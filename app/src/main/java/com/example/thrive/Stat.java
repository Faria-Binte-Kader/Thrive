package com.example.thrive;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Stat extends AppCompatActivity {

    public static final String TAG = "TAG Stat";

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    RecyclerView statRecyclerView;
    ArrayList<Statistics> statisticsArrayList;
    StatAdapter statAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        BottomNavigationView nav= findViewById(R.id.bottomnavview2);
        findViewById(R.id.bottomnavview2).setBackground(null);
        nav.setSelectedItemId(R.id.stat);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(Stat.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.profile:
                        Intent intent2 = new Intent(Stat.this, Profile.class);
                        startActivity(intent2);
                        break;
                    case R.id.shop:
                        Intent intent3 = new Intent(Stat.this, Shop.class);
                        startActivity(intent3);
                        break;
                    case R.id.stat:
                        break;
                    case R.id.friends:
                        Intent intent4 = new Intent(Stat.this, Friends.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });

        statisticsArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFirebase();
        loadGoalStatFromFirebase();

    }

    private void setUpRecyclerView() {
        statRecyclerView = findViewById(R.id.recyclerViewStat);
        statRecyclerView.setHasFixedSize(true);
        statRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpFirebase() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    private void loadGoalStatFromFirebase() {
        if(statisticsArrayList.size()>0)
            statisticsArrayList.clear();


        fStore.collection("Statistics")
                .document(fAuth.getCurrentUser().getUid()).collection("Goals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot querySnapshot:task.getResult()){
                            String jan = querySnapshot.getString("0");
                            String feb = querySnapshot.getString("1");
                            String mar = querySnapshot.getString("2");
                            String apr = querySnapshot.getString("3");
                            String may = querySnapshot.getString("4");
                            String jun = querySnapshot.getString("5");
                            String jul = querySnapshot.getString("6");
                            String aug = querySnapshot.getString("7");
                            String sep = querySnapshot.getString("8");
                            String oct = querySnapshot.getString("9");
                            String nov = querySnapshot.getString("10");
                            String dec = querySnapshot.getString("11");

                            List<String> monthStat = new ArrayList<>();
                            monthStat.add(jan);
                            monthStat.add(feb);
                            monthStat.add(mar);
                            monthStat.add(apr);
                            monthStat.add(may);
                            monthStat.add(jun);
                            monthStat.add(jul);
                            monthStat.add(aug);
                            monthStat.add(sep);
                            monthStat.add(oct);
                            monthStat.add(nov);
                            monthStat.add(dec);


                            Statistics statistics = new Statistics(
                                    querySnapshot.getString("GoalID"),
                                    querySnapshot.getString("GoalName"),
                                    monthStat
                            );
                            statisticsArrayList.add(statistics);
                        }
                        statAdapter = new StatAdapter(Stat.this,statisticsArrayList);
                        statRecyclerView.setAdapter(statAdapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(Stat.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Stat.this, MainActivity.class);
        startActivity(intent);
    }
}