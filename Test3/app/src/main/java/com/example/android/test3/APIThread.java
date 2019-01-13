package com.example.android.test3;

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
public APIThread(byte[] newImgByteArray) {
    this.client = client;
    this.newImgByteArray = newImgByteArray;
}


    @Override
    public void run() {

        client = new ClarifaiBuilder("b2c657222b8e4b1ea83e97e3196a14ec")
                .client(new OkHttpClient())
                .buildSync();

        ConceptModel model = client.getDefaultModels().generalModel();
       // ModelVersion modelVersion = model.getVersionByID("the-version").executeSync().get();

        ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict(model.id())
                .withInputs(ClarifaiInput.forImage(newImgByteArray))
                .executeSync();

        System.out.println(response.get().toString());

        ArrayList<Concept> conceptList = new ArrayList<Concept>();
        List<ClarifaiOutput<Prediction>> outputPredictionsList = response.get();

        for (int i = 0; i <  outputPredictionsList.size();i++ ) {
            for (Prediction prediction : outputPredictionsList.get(i).data()) {
                conceptList.add(prediction.asConcept());
                System.out.println(conceptList.get(i).name());
            }
        }

    }
}
