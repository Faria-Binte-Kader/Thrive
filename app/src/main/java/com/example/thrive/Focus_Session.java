package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class Focus_Session extends AppCompatActivity {
    private long startTimeInMillies;
    private EditText timetext;
    private Button setTime;

    private TextView textViewcountdown;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis;
    private long endTime;
    FloatingActionButton startbtn,pausebtn,resetbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus__session);

        timetext=findViewById(R.id.timerminutetext);
        setTime=findViewById(R.id.settimerButton);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String input = timetext.getText().toString();
             if(input.length()==0)
             {
                 Toast.makeText(Focus_Session.this,"Enter a number", Toast.LENGTH_SHORT).show();
                 return;
             }
             long millisInput= Long.parseLong(input)*60000;
             if(millisInput==0){
                 Toast.makeText(Focus_Session.this,"Enter a positive number", Toast.LENGTH_SHORT).show();
                 return;
             }
             setTimer(millisInput);
             timetext.setText("");

            }
        });
        textViewcountdown=findViewById(R.id.countdown);
        startbtn = findViewById(R.id.fab_start);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timerRunning)
                {
                    startTimer();
                }
            }
        });
         pausebtn = findViewById(R.id.fab_pause);
        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning)
                {
                    pauseTimer();
                }

            }
        });
        resetbtn = findViewById(R.id.fab_stop);
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

    }

    private void setTimer(long milliseconds){
      startTimeInMillies=milliseconds;
      resetTimer();
      closeKeyboard();

    }

    private void startTimer() {
        endTime=System.currentTimeMillis()+timeLeftInMillis;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
             timeLeftInMillis=millisUntilFinished;
             updateCountDownText();
            }

            @Override
            public void onFinish() {
              timerRunning=false;
            }
        }.start();

        timerRunning=true;
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning=false;
    }

    private void resetTimer() {
        if(countDownTimer!=null) {
            countDownTimer.cancel();
        }
        timerRunning=false;
        timeLeftInMillis= startTimeInMillies;
        updateCountDownText();
    }

    private void updateCountDownText() {
        int hours= (int) (timeLeftInMillis/1000)/3600;
        int minutes= (int) ((timeLeftInMillis/1000)%3600)/60;
        int seconds= (int) (timeLeftInMillis/1000)%60;
        String timeLeftFormatted;
        if(hours>0){
            timeLeftFormatted = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,seconds);
        }
        else{
        timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        }
        textViewcountdown.setText(timeLeftFormatted);
    }

    /*@Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", timeLeftInMillis);
        outState.putBoolean("timerRunning", timerRunning);
        outState.putLong("endTime", endTime);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        timeLeftInMillis=savedInstanceState.getLong("millisLeft");
        timerRunning= savedInstanceState.getBoolean("timerRunning");
        updateCountDownText();
        if(timerRunning){
            endTime=savedInstanceState.getLong("endTime");
            timeLeftInMillis=endTime-System.currentTimeMillis();
            startTimer();

        }

    }*/

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences= getSharedPreferences("preferences", MODE_PRIVATE);
        timeLeftInMillis=preferences.getLong("millisLeft", startTimeInMillies);
        timerRunning= preferences.getBoolean("timerRunning", false);
        startTimeInMillies= preferences.getLong("startTimeInMillis",600000);
        updateCountDownText();
        if(timerRunning){
            endTime=preferences.getLong("endTime", 0);
            timeLeftInMillis=endTime-System.currentTimeMillis();
            if(timeLeftInMillis<0){
                timeLeftInMillis=0;
                timerRunning=false;
                updateCountDownText();
            }
            else
            {startTimer();}
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences= getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putLong("startTimeInMillis", startTimeInMillies);
        editor.putLong("millisLeft", timeLeftInMillis);
        editor.putBoolean("timerRunning", timerRunning);
        editor.putLong("endTime", endTime);
        editor.apply();
        if(countDownTimer!=null) {
            countDownTimer.cancel();
        }
    }

    private void closeKeyboard()
    {
        View view= this.getCurrentFocus();
        if(view!=null) {
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
        }
    }
