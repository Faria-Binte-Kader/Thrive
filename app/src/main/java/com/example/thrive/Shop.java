package com.example.thrive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class Shop extends AppCompatActivity {

    Button updateProfileBtn;
    ImageView animal1,animal2,animal3,animal4;
    TextView animal1t,animal2t,animal3t,animal4t;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String animalName1,animalName2,animalName3,animalName4;

    public static final String EXTRA_TEXT7 = "com.example.application.example.EXTRA_TEXT7";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        animal1=findViewById(R.id.animal1pic);
        animal2=findViewById(R.id.animal2pic);
        animal3=findViewById(R.id.animal3pic);
        animal4=findViewById(R.id.animal4pic);
        animal1t=findViewById(R.id.animal1price);
        animal2t=findViewById(R.id.animal2price);
        animal3t=findViewById(R.id.animal3price);
        animal4t=findViewById(R.id.animal4price);

        BottomNavigationView nav= findViewById(R.id.bottomnavview2);
        findViewById(R.id.bottomnavview2).setBackground(null);
        nav.setSelectedItemId(R.id.shop);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(Shop.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.profile:
                        Intent intent2 = new Intent(Shop.this, Profile.class);
                        startActivity(intent2);
                        break;
                    case R.id.shop:
                        break;
                    case R.id.stat:
                        Intent intent3 = new Intent(Shop.this, Stat.class);
                        startActivity(intent3);
                        break;
                    case R.id.friends:
                        Intent intent4 = new Intent(Shop.this, Friends.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });
    loadDataFromFirebase();
    animal1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Shop.this, AnimalDetails.class);
            intent.putExtra(EXTRA_TEXT7,animalName1 );
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    });

        animal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this, AnimalDetails.class);
                intent.putExtra(EXTRA_TEXT7,animalName2 );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        animal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this, AnimalDetails.class);
                intent.putExtra(EXTRA_TEXT7,animalName3 );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        animal4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this, AnimalDetails.class);
                intent.putExtra(EXTRA_TEXT7,animalName4);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void loadDataFromFirebase(){
        fStore.collection("Animals")
                .whereEqualTo("AnimalName", "Cat")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String link,price,name;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            link = querySnapshot.getString("DownloadLink");
                            price = querySnapshot.getString("Price");
                            name= querySnapshot.getString("AnimalName");
                        }
                        Picasso.get().load(link).into(animal1);
                        animal1t.setText(price.toString());
                        animalName1=name;

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Shop.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
        fStore.collection("Animals")
                .whereEqualTo("AnimalName", "Dog")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String link,price,name;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            link = querySnapshot.getString("DownloadLink");
                            price = querySnapshot.getString("Price");
                            name= querySnapshot.getString("AnimalName");
                        }
                        Picasso.get().load(link).into(animal2);
                        animal2t.setText(price.toString());
                        animalName2=name;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Shop.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
        fStore.collection("Animals")
                .whereEqualTo("AnimalName", "Clown-Fish")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String link,price,name;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            link = querySnapshot.getString("DownloadLink");
                            price = querySnapshot.getString("Price");
                            name= querySnapshot.getString("AnimalName");
                        }
                        Picasso.get().load(link).into(animal3);
                        animal3t.setText(price.toString());
                        animalName3=name;

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Shop.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
        fStore.collection("Animals")
                .whereEqualTo("AnimalName", "Reindeer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String link,price,name;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            link = querySnapshot.getString("DownloadLink");
                            price = querySnapshot.getString("Price");
                            name= querySnapshot.getString("AnimalName");
                        }
                        Picasso.get().load(link).into(animal4);
                        animal4t.setText(price.toString());
                        animalName4=name;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Shop.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Shop.this, MainActivity.class);
        startActivity(intent);
    }
}