package com.example.android.test3;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toolbar;

public class HomeScreenActivity extends AppCompatActivity {

    private Button cameraButton;
    private Button statsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().setTitle("SmartR");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar);


        cameraButton = findViewById(R.id.cameraButton);
        statsButton = findViewById(R.id.standard);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraActivity();
            }
        });

//        statsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openStatsActivity();
//            }
//        });
    }

    public void openCameraActivity() {
        Intent intent = new Intent(this, CameraActivity.class); /*Replace CAMACTIVITY
                                                          with actual camera/main activity once created*/
        startActivity(intent);
    }

//    public void openStatsActivity() {
//        Intent intent = new Intent(this, STATSACTIVITY.class); /*Replace STATSACTIVITY
//                                                           with actual stats activity once created*/
//        startActivity(intent);
//    }

}
