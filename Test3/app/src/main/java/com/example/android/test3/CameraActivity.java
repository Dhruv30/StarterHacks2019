package com.example.android.test3;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import clarifai2.api.ClarifaiClient;

import clarifai2.api.ClarifaiClient;

public class CameraActivity extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1111;
    Dialog myDialog;

    ImageView mImageView;

    public ClarifaiClient client;
    public static Context context;

    public static int totalRecycle;
    public static int totalCompost;
    public static int totalElectronics;
    public static int totalDonatable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mImageView = findViewById(R.id.add_img);

        context = this; // Set context for API thread to access

        ImageView button = findViewById(R.id.add_img);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,
                CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] newImgByteArray = stream.toByteArray();

                Thread apiThread = new APIThread(newImgByteArray, client);
                apiThread.start();

                // convert byte array to Bitmpap
                Bitmap bitmap = BitmapFactory.decodeByteArray(newImgByteArray, 0,
                        newImgByteArray.length);

                mImageView.setImageBitmap(bitmap);
            }
        }
    }

    public static Context getLastSetContext() {
        return context;
    }

    /*
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ArrayList<String> data = intent.getStringArrayListExtra("data"); // you will get "data_from_thread"

        for (int i = 0; i < data.size(); i++) {
            System.out.println("TEST RESPONSE " + data.get(i));
        }

        // Call waste classifier function using data passed in
        System.out.println("RESULT OF CLASSIFICATION " + wasteClassifier(data));
    }
    */

    /*public void showPopup(){
        myDialog.set
    }*/
}