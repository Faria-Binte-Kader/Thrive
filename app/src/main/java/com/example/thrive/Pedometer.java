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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Pedometer extends AppCompatActivity implements SensorEventListener{

    TextView textViewStepCounter;
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
            String steps = String.valueOf(sensorEvent.values[0]);

            DocumentReference documentReference1 = fStore.collection("Stepcount").document(fAuth.getUid()).collection("Dailysteps").document(formattedDate);
            Map<String, Object> step = new HashMap<>();
            step.put("Count", steps);

            documentReference1.set(step).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: step count is stored");
                }

            });

            DocumentReference documentReference2 = fStore.collection("Stepcount").document(fAuth.getUid()).collection("Dailysteps").document(formattedDate);
            documentReference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            todaysteps = document.getString("Count");
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "got failed with ", task.getException());
                    }
                }
            });

            textViewStepCounter.setText(steps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}