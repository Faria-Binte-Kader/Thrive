package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Pedometer extends AppCompatActivity implements SensorEventListener{

    TextView textViewStepCounter,textViewCalorie,textViewDistance;
    SensorManager sensorManager;
    boolean running = false;

    private int SENSOR_PERMISSION_CODE = 1;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String TAG = "TAG StepCounter";

    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);

    String todaysteps = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(ContextCompat.checkSelfPermission(Pedometer.this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestActivitySensorPermission();
        }

        textViewStepCounter = findViewById(R.id.textViewStepCounter);
        textViewCalorie = findViewById(R.id.textViewCalorie);
        textViewDistance = findViewById(R.id.textViewDistance);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    private void requestActivitySensorPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACTIVITY_RECOGNITION)) {
            new AlertDialog.Builder(this)
                    .setTitle("permission needed")
                    .setMessage("this permission is needed to count your steps")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Pedometer.this, new String[] {Manifest.permission.ACTIVITY_RECOGNITION}, SENSOR_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACTIVITY_RECOGNITION}, SENSOR_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == SENSOR_PERMISSION_CODE) {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(running) {
            int steps = (int) (sensorEvent.values[0]);

            textViewStepCounter.setText(String.valueOf(steps));

            final int[] weight = {0};
            final int[] height = {0};
            final float[] calories = {(float) 0};
            float distance;

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);

            DocumentReference documentReference = fStore.collection("User").document(fAuth.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String temp = document.getString("Height");
                            height[0] = Integer.parseInt(temp);
                            temp = document.getString("Weight");
                            weight[0] = Integer.parseInt(temp);

                            if(height[0] >=180) {
                                if(weight[0] <=45) calories[0] =(float) (steps*.028);
                                if(weight[0] >45 && weight[0] <=55) calories[0] =(float) (steps*.033);
                                if(weight[0] >55 && weight[0] <=64) calories[0] =(float) (steps*.038);
                                if(weight[0] >64 && weight[0] <=73) calories[0] =(float) (steps*.044);
                                if(weight[0] >73 && weight[0] <=82) calories[0] =(float) (steps*.049);
                                if(weight[0] >82 && weight[0] <=91) calories[0] =(float) (steps*.055);
                                if(weight[0] >91 && weight[0] <=100) calories[0] =(float) (steps*.060);
                                if(weight[0] >100 && weight[0] <=114) calories[0] =(float) (steps*.069);
                                if(weight[0] >114 && weight[0] <=125) calories[0] =(float) (steps*.075);
                                if(weight[0] >125 && weight[0] <=136) calories[0] =(float) (steps*.082);
                                if(weight[0] >136) calories[0] =(float) (steps*.090);
                            } else {
                                if(weight[0] <=45) calories[0] =(float) (steps*.025);
                                if(weight[0] >45 && weight[0] <=55) calories[0] =(float) (steps*.030);
                                if(weight[0] >55 && weight[0] <=64) calories[0] =(float) (steps*.035);
                                if(weight[0] >64 && weight[0] <=73) calories[0] =(float) (steps*.040);
                                if(weight[0] >73 && weight[0] <=82) calories[0] =(float) (steps*.045);
                                if(weight[0] >82 && weight[0] <=91) calories[0] =(float) (steps*.050);
                                if(weight[0] >91 && weight[0] <=100) calories[0] =(float) (steps*.055);
                                if(weight[0] >100 && weight[0] <=114) calories[0] =(float) (steps*.062);
                                if(weight[0] >114 && weight[0] <=125) calories[0] =(float) (steps*.068);
                                if(weight[0] >125 && weight[0] <=136) calories[0] =(float) (steps*.075);
                                if(weight[0] >136) calories[0] =(float) (steps*.080);
                            }

                            textViewCalorie.setText(String.valueOf(nf.format(calories[0])));

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "got failed with ", task.getException());
                    }
                }
            });

            distance = ((float) steps/ (float) 1786);

            textViewDistance.setText(String.valueOf(nf.format(distance)));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}