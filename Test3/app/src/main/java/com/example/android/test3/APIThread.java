package com.example.android.test3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;

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
ClarifaiClient client;

public APIThread(ClarifaiClient client) {
    this.client = client;
}

    @Override
    public void run() {

        client = new ClarifaiBuilder("b2c657222b8e4b1ea83e97e3196a14ec")
                .client(new OkHttpClient())
                .buildSync();

        ConceptModel model = client.getDefaultModels().generalModel();
       // ModelVersion modelVersion = model.getVersionByID("the-version").executeSync().get();

        ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict(model.id())
                .withInputs(ClarifaiInput.forImage("https://loopnewslive.blob.core.windows.net/liveimage/sites/default/files/2018-08/SldlWkSDOg.jpg"))
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

        Intent i = new Intent(context, CameraActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.putStringArrayListExtra("data", namesList);
        context.startActivity(i);
    }
}
