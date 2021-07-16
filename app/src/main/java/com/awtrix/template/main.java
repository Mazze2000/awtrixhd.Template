package com.awtrix.template;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.awtrix.template.databinding.MainBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class main extends AppCompatActivity {
    private MainBinding binding;

    public static Awtrix awtrix = new Awtrix();

    Timer t = new Timer();

    TextView hour0;
    TextView hour1;
    TextView doublepoint0;
    TextView minute0;
    TextView minute1;
    TextView doublepoint1;
    TextView second0;
    TextView second1;

    LinearLayout background0;

    TextView[] textArray = new TextView[8];
    private String[] oldTimeDigit = {"","","","","","","",""};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        awtrix.init(this.getPackageName());

        hour0 = (TextView) findViewById(R.id.hour0);
        hour1 = (TextView) findViewById(R.id.hour1);
        doublepoint0 = (TextView) findViewById(R.id.doublepoint0);
        minute0 = (TextView) findViewById(R.id.minute0);
        minute1 = (TextView) findViewById(R.id.minute1);
        doublepoint1 = (TextView) findViewById(R.id.doublepoint1);
        second0 = (TextView) findViewById(R.id.second0);
        second1 = (TextView) findViewById(R.id.second1);

        background0 = (LinearLayout) findViewById(R.id.background0);

        textArray[0] = hour0;
        textArray[1] = hour1;
        textArray[2] = doublepoint0;
        textArray[3] = minute0;
        textArray[4] = minute1;
        textArray[5] = doublepoint1;
        textArray[6] = second0;
        textArray[7] = second1;

        for(int i = 0;i<textArray.length;i++){
            textArray[i].setBackgroundColor(Color.rgb(settings.backgroundColor[0], settings.backgroundColor[1], settings.backgroundColor[2]));
            textArray[i].setTextColor(Color.rgb(settings.textColor[0], settings.textColor[1], settings.textColor[2]));
            textArray[i].setAutoSizeTextTypeUniformWithConfiguration(1, 280, 1, TypedValue.COMPLEX_UNIT_FRACTION_PARENT);
        }
        background0.setBackgroundColor(Color.rgb(settings.backgroundColor[0], settings.backgroundColor[1], settings.backgroundColor[2]));

        Animation a = AnimationUtils.loadAnimation(this, R.anim.slide_top_to_bottom);
        for(int i = 0;i<textArray.length;i++){
            a.reset();
            textArray[i].clearAnimation();
        }

        t.schedule(new TimerTask(){
            @Override
            public void run() {
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat df = new DateFormat();
                df.format("hh:mm:ss", currentTime);
                String tempString = "" + df.format("HH:mm:ss", currentTime);

                for(int i = 0;i<textArray.length;i++){
                    if(!(oldTimeDigit[i].equals(tempString.substring(i,i+1)))){
                        textArray[i].setText(tempString.substring(i,i+1));
                        textArray[i].startAnimation(a);
                        oldTimeDigit[i] = tempString.substring(i,i+1);
                    }
                }
            }
        }, 0, 1000);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent.getStringExtra("transition-in")!=null && intent.getStringExtra("transition-out")!=null){
            overridePendingTransition(getResources().getIdentifier(intent.getStringExtra("transition-in"),"anim",getPackageName()),getResources().getIdentifier(intent.getStringExtra("transition-out"),"anim",getPackageName()));
        } else {
            this.finish();
        }

        for(int i = 0;i<textArray.length;i++){
            textArray[i].setBackgroundColor(Color.rgb(settings.backgroundColor[0], settings.backgroundColor[1], settings.backgroundColor[2]));
            textArray[i].setTextColor(Color.rgb(settings.textColor[0], settings.textColor[1], settings.textColor[2]));
        }
        background0.setBackgroundColor(Color.rgb(settings.backgroundColor[0], settings.backgroundColor[1], settings.backgroundColor[2]));
        //awtrix.sendToServer("cmd","hold");
        //mTcpClient.sendMessage("Hallo Welt");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = getIntent();
        if(intent.getStringExtra("transition-in")!=null && intent.getStringExtra("transition-out")!=null){
            overridePendingTransition(getResources().getIdentifier(intent.getStringExtra("transition-in"),"anim",getPackageName()),getResources().getIdentifier(intent.getStringExtra("transition-out"),"anim",getPackageName()));
        } else {
            this.finish();
        }
    }
}