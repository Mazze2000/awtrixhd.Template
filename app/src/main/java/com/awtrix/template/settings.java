package com.awtrix.template;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.awtrix.template.databinding.SettingsBinding;

public class settings extends AppCompatActivity {
    private SettingsBinding binding;

    static public int[] backgroundColor = {0,0,0};
    static public int[] textColor = {255,0,0};

    static SeekBar seek_b_red;
    static SeekBar seek_b_green;
    static SeekBar seek_b_blue;

    static SeekBar seek_t_red;
    static SeekBar seek_t_green;
    static SeekBar seek_t_blue;

    static TextView value_b_red;
    static TextView value_b_green;
    static TextView value_b_blue;

    static TextView value_t_red;
    static TextView value_t_green;
    static TextView value_t_blue;

    static RadioButton radio1;
    static RadioButton radio2;

    static TextView timeTextEx;

    SeekBar.OnSeekBarChangeListener mlistener;
    CompoundButton.OnCheckedChangeListener mlistenerRadio;

    TextView header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        seek_b_red = findViewById(R.id.seek_background_red);
        seek_b_green = findViewById(R.id.seek_background_green);
        seek_b_blue = findViewById(R.id.seek_background_blue);

        seek_t_red = findViewById(R.id.seek_text_red);
        seek_t_green = findViewById(R.id.seek_text_green);
        seek_t_blue = findViewById(R.id.seek_text_blue);

        value_b_red = findViewById(R.id.value_background_red);
        value_b_green = findViewById(R.id.value_background_green);
        value_b_blue = findViewById(R.id.value_background_blue);

        value_t_red = findViewById(R.id.value_text_red);
        value_t_green = findViewById(R.id.value_text_green);
        value_t_blue = findViewById(R.id.value_text_blue);

        radio1 = findViewById(R.id.radioButton);
        radio2 = findViewById(R.id.radioButton2);

        timeTextEx = findViewById(R.id.timeTextEx);
        timeTextEx.setBackgroundColor(Color.rgb(0,0,0));
        timeTextEx.setTextColor(Color.rgb(0,0,255));

        header = (TextView) findViewById(R.id.header);
        header.setText(getString(R.string.settings) + " - " + getString(R.string.app_name));


        mlistenerRadio = new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.radioButton:
                        if(radio1.isChecked()){
                            Typeface seven_segment = getResources().getFont(R.font.seven_segment);
                            timeTextEx.setTypeface(seven_segment);
                        }
                        break;
                    case R.id.radioButton2:
                        if(radio2.isChecked()){
                            Typeface awtrix_old = getResources().getFont(R.font.awtrix_old);
                            timeTextEx.setTypeface(awtrix_old);
                        }
                        break;
                }
            }
        };
        radio1.setOnCheckedChangeListener(mlistenerRadio);
        radio2.setOnCheckedChangeListener(mlistenerRadio);

        mlistener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (seekBar.getId()) {
                    case R.id.seek_background_red:
                        value_b_red.setText("" + progress);
                        break;
                    case R.id.seek_background_green:
                        value_b_green.setText("" + progress);
                        break;
                    case R.id.seek_background_blue:
                        value_b_blue.setText("" + progress);
                        break;
                    case R.id.seek_text_red:
                        value_t_red.setText("" + progress);
                        break;
                    case R.id.seek_text_green:
                        value_t_green.setText("" + progress);
                        break;
                    case R.id.seek_text_blue:
                        value_t_blue.setText("" + progress);
                        break;
                }
                int tempInt1 = Integer.parseInt(String.valueOf(value_b_red.getText()));
                int tempInt2 = Integer.parseInt(String.valueOf(value_b_green.getText()));
                int tempInt3 = Integer.parseInt(String.valueOf(value_b_blue.getText()));

                int tempInt4 = Integer.parseInt(String.valueOf(value_t_red.getText()));
                int tempInt5 = Integer.parseInt(String.valueOf(value_t_green.getText()));
                int tempInt6 = Integer.parseInt(String.valueOf(value_t_blue.getText()));


                backgroundColor[0] = tempInt1;
                backgroundColor[1] = tempInt2;
                backgroundColor[2] = tempInt3;

                textColor[0] = tempInt4;
                textColor[1] = tempInt5;
                textColor[2] = tempInt6;

                timeTextEx.setBackgroundColor(Color.rgb(tempInt1,tempInt2,tempInt3));
                timeTextEx.setTextColor(Color.rgb(tempInt4, tempInt5, tempInt6));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };

        seek_b_red.setOnSeekBarChangeListener(mlistener);
        seek_b_green.setOnSeekBarChangeListener(mlistener);
        seek_b_blue.setOnSeekBarChangeListener(mlistener);

        seek_t_red.setOnSeekBarChangeListener(mlistener);
        seek_t_green.setOnSeekBarChangeListener(mlistener);
        seek_t_blue.setOnSeekBarChangeListener(mlistener);

        this.loadStorage();
        this.loadStartValues();
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
        this.loadStartValues();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = getIntent();
        if(intent.getStringExtra("transition-in")!=null && intent.getStringExtra("transition-out")!=null){
            overridePendingTransition(getResources().getIdentifier(intent.getStringExtra("transition-in"),"anim",getPackageName()),getResources().getIdentifier(intent.getStringExtra("transition-out"),"anim",getPackageName()));
        }
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.saveStorage();
    }

    private void loadStorage(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        this.backgroundColor[0] = prefs.getInt("backgroundColor0",0);
        this.backgroundColor[1] = prefs.getInt("backgroundColor1",0);
        this.backgroundColor[2] = prefs.getInt("backgroundColor2",0);

        this.textColor[0] = prefs.getInt("textColor0",0);
        this.textColor[1] = prefs.getInt("textColor1",0);
        this.textColor[2] = prefs.getInt("textColor2",0);


    }

    private void saveStorage(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("backgroundColor0", this.backgroundColor[0]);
        editor.putInt("backgroundColor1", this.backgroundColor[1]);
        editor.putInt("backgroundColor2", this.backgroundColor[2]);

        editor.putInt("textColor0", this.textColor[0]);
        editor.putInt("textColor1", this.textColor[1]);
        editor.putInt("textColor2", this.textColor[2]);
        editor.apply();
    }

    private void loadStartValues(){
        value_b_red.setText("" + backgroundColor[0]);
        value_b_green.setText("" + backgroundColor[1]);
        value_b_blue.setText("" + backgroundColor[2]);

        value_t_red.setText("" + textColor[0]);
        value_t_green.setText("" + textColor[1]);
        value_t_blue.setText("" + textColor[2]);

        seek_b_red.setProgress(backgroundColor[0]);
        seek_b_green.setProgress(backgroundColor[1]);
        seek_b_blue.setProgress(backgroundColor[2]);

        seek_t_red.setProgress(textColor[0]);
        seek_t_green.setProgress(textColor[1]);
        seek_t_blue.setProgress(textColor[2]);

        timeTextEx.setBackgroundColor(Color.rgb(backgroundColor[0], backgroundColor[1], backgroundColor[2]));
        timeTextEx.setTextColor(Color.rgb(textColor[0], textColor[1], textColor[2]));
    }
}