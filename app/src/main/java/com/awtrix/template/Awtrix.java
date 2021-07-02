package com.awtrix.template;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



public class Awtrix extends AppCompatActivity {
    public String appName = "Template";

    private View mContentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        String layout = intent.getStringExtra("layout");

        if(layout!=null){
            if(layout.equals("main")){
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);

                //setContentView(R.layout.main);
            } else if(layout.equals("settings")){
                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);

                //setContentView(R.layout.settings);
            } else{
                Log.v("MYTAG", "this layout is unknown (" + layout + ")");
            }
        }
    }
}
