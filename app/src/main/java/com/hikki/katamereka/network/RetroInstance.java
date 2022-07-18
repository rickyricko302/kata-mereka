package com.hikki.katamereka.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {
    private String BASE_URL = "https://twindev.herokuapp.com/";
    private String BASE_URL2 = "https://flaski-app.herokuapp.com/";
    private Retrofit retrofit;
    public Retrofit getTwin(){
          retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }
    public Retrofit getFlaski(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
