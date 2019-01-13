package com.example.android.test3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import clarifai2.api.ClarifaiClient;

public class CameraActivity extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1111;
    ImageView mImageView;

    public ClarifaiClient client;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mImageView = findViewById(R.id.add_img);

        context = this; // Set context for API thread to access
        Thread thread = new APIThread(client);
        thread.start();

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] newImgByteArray = stream.toByteArray();
//                imgByteArray = newImgByteArray;

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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ArrayList<String> data = intent.getStringArrayListExtra("data"); // you will get "data_from_thread"

        for (int i = 0; i < data.size(); i++) {
            System.out.println("TEST RESPONSE " + data.get(i));
        }
    }
}
