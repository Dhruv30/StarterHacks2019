package com.example.android.test3;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    public static String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setTitle("SmartR");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar);
        System.out.println("KEY CODE RECIEVED: "+this.test);
    }

    public void openHome(View view){
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        this.finish();
    }
}


