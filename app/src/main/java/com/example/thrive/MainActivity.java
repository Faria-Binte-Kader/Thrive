package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Button dropdownmenu, focusbutton;
    Button profileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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