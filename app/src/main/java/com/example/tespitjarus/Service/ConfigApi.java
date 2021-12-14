package com.example.tespitjarus.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigApi {
    public static final String BASE_URL = "http://dev.pitjarus.co/";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static ApiService service = retrofit.create(ApiService.class);
}
