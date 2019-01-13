package com.example.android.test3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import okhttp3.OkHttpClient;

public class APIThread extends Thread {
    byte[] newImgByteArray;
    public ClarifaiClient client;

    public APIThread(byte[] newImgByteArray, ClarifaiClient _client) {
        client = _client;
        this.newImgByteArray = newImgByteArray;
    }

    @Override
    public void run() {

        client = new ClarifaiBuilder("0624bdba1a464be5947f261a50158a0b")
                .client(new OkHttpClient())
                .buildSync();

        ConceptModel model = client.getDefaultModels().generalModel();
       // ModelVersion modelVersion = model.getVersionByID("the-version").executeSync().get();

        ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict(model.id())
                .withInputs(ClarifaiInput.forImage(newImgByteArray))
                .executeSync();

        System.out.println(response.get().toString());

        ArrayList<Concept> conceptList = new ArrayList<Concept>();
        ArrayList<String> namesList = new ArrayList<String>();
        List<ClarifaiOutput<Prediction>> outputPredictionsList = response.get();

        for (int i = 0; i < outputPredictionsList.size(); i++ ) {
            for (Prediction prediction : outputPredictionsList.get(i).data()) {
                conceptList.add(prediction.asConcept());
                namesList.add(prediction.asConcept().name());
            }
        }

        Context context = CameraActivity.getLastSetContext(); // Retrieve context from Main


        // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // i.putStringArrayListExtra("data", namesList);

        //ResultActivity.test = wasteClassifier(namesList);
        //Intent intent = new Intent(context, ResultActivity.class);
        //context.startActivity(intent);
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
            return "Recycle";
        } else if (greatestNum == numCompost) {
            return "Compost";
        } else if (greatestNum == numElectronics) {
            return "Electronic Recycling";
        } else if (greatestNum == numDonatable) {
            return "Donate or Reuse";
        }
        return "Waste"; // default
    }
}
