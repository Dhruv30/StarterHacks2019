package com.example.android.test3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Looper;

import java.util.ArrayList;
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
        Looper.prepare();

        client = new ClarifaiBuilder("0624bdba1a464be5947f261a50158a0b")
                .client(new OkHttpClient())
                .buildSync();

        ConceptModel model = client.getDefaultModels().generalModel();
       // ModelVersion modelVersion = model.getVersionByID("the-version").executeSync().get();

        ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict(model.id())
                .withInputs(ClarifaiInput.forImage(newImgByteArray))
                .executeSync();

//        System.out.println(response.get().toString());

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
     /*
        Intent i = new Intent(context, CameraActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.putStringArrayListExtra("data", namesList);
        context.startActivity(i);
        */

        ;

         ResultActivity.test = "Testing 123";
         Intent intent = new Intent(context, ResultActivity.class);
         context.startActivity(intent);

    }
}
