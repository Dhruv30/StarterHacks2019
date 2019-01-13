package com.example.android.test3;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        getSupportActionBar().setTitle("SmartR");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar);

        TextView text1 = (TextView) findViewById(R.id.statsText1);
        TextView text2 = (TextView) findViewById(R.id.statsText2);
        TextView text3 = (TextView) findViewById(R.id.statsText3);
        TextView text4 = (TextView) findViewById(R.id.statsText4);

        text1.setText("You have recycled " + CameraActivity.totalRecycle + " items");
        text2.setText("You have composted " + CameraActivity.totalCompost + " items");
        text3.setText("You have recycled " + CameraActivity.totalElectronics + " electronics");
        text4.setText("You have donated " + CameraActivity.totalDonatable + " items");
    }

    public void openHome(View view){
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        this.finish();
    }
}
