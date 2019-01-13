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

    public static boolean isRecyclable(String tag, ArrayList<String> recyclables){
        return (recyclables.contains(tag));
    }

    public static boolean isCompost(String tag, ArrayList<String> compost){
        return (compost.contains(tag));
    }

    public static boolean isElectronics(String tag, ArrayList<String> electronics){
        return (electronics.contains(tag));
    }

    public static boolean isDonatable(String tag, ArrayList<String> donateOrReuse){
        return (donateOrReuse.contains(tag));
    }

    public String wasteClassifier(ArrayList<String> tagsList){
        // ArrayList<String> majorCategories = new ArrayList<String>({"plastic", "glass", "paper", "food", "electronics"});
        int userRecycled = 0;
        int userComposted = 0;
        int userElectronics = 0;
        int userDonated = 0;
        
        ArrayList<String> recyclables = new ArrayList<String>( Arrays.asList("plastic", "paper", "glass", "bottle", "aluminum", "cardboard", "boxboard", "healthy", "recycling", "soda", "pop", "can", "beer", "glass items", "wood", "container"));
        ArrayList<String> compost = new ArrayList<String>( Arrays.asList("food", "vegetable", "fruit", "tea bag", "leaf", "banana", "apple", "paper tower", "flower", "plant"));
        ArrayList<String> electronics = new ArrayList<String>( Arrays.asList("battery", "laptop", "phone", "electronic", "technology", "TV", "screen", "fridge", "stove", "appliance", "microwave", "electricity", "internet", "telephone"));
        ArrayList<String> donateOrReuse = new ArrayList<String>( Arrays.asList("luggage", "leather", "fashion", "cloth", "clothes", "clothing", "bag", "jacket", "wear", "fabric", "chair", "furniture", "seat", "jewelry", "hats", "gloves", "mittens", "scarf"));

        /*
        Problem: We can't only check the first tag of the image, as it may not always represent the best classification of the object
        Potential Solution?
         => Create categories representing the recyclability, compostability, electronic recyclability and donatability of an object
            based on the category with the most # classified, the classification can be determined
        */

        int numRecycle = 0, numCompost = 0, numElectronics = 0, numDonatable = 0;
        for (String tag : tagsList){
            if (isRecyclable(tag, recyclables)){
                numRecycle ++;
            }
            if (isCompost(tag, compost)){
                numCompost ++;
            }
            if (isElectronics(tag, electronics)){
                numElectronics ++;
            }
            if (isDonatable(tag, donateOrReuse)){
                numDonatable ++;
            }
        }

        int greatestNum = Math.max(Math.max(numRecycle, numCompost), Math.max(numElectronics, numDonatable));

        // First, check if it is garbage

        if (greatestNum <= 1){ // 1 is the threshold
            return "Garbage";
        }

        // Check if any ties exist

        if (numRecycle == numCompost || numRecycle == numElectronics || numRecycle == numDonatable){
            return "Recycle?? (Not sure)";
        }

        if (numCompost == numElectronics || numCompost == numDonatable) {
            return "Compost?? (Not sure)";
        }

        if (numDonatable == numElectronics) {
            return "Donate or Reuse";
        }

        // Since no ties exist, and the object has not been classified as garbage, there exists a distinct category the object can be classified into
        if (greatestNum == numRecycle) {
            userRecycled ++;
            return "Recycle";
        } else if (greatestNum == numCompost) {
            userComposted ++;
            return "Compost";
        } else if (greatestNum == numElectronics) {
            userElectronics ++;
            return "Electronic Recycling";
        } else if (greatestNum == numDonatable) {
            userDonated ++;
            return "Donate or Reuse";
        }
        return "Waste"; // default
    }

    /*public void showPopup(){
        myDialog.set
    }*/
}
