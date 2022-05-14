package com.example.iem.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private final String BASE_URL = "http://192.168.50.196:8080/";
    private final Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public API getApi() {
        return retrofit.create(API.class);
    }
}
