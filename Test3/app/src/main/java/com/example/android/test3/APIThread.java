package com.example.android.test3;

import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output.ClarifaiOutput;
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
        System.out.println(response.get());



    }
}
