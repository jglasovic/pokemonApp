package com.example.pokemonapp.rest;

import android.content.Context;
import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiGenerator {

    public static String API_BASE_URL = "https://pokeapi.co/api/v2/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static ApiService service;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static int CONNECT_TIMEOUT = 300000;
    private static int READ_TIMEOUT = 300000;
    private static int WRITE_TIMEOUT = 300000;

    private static ApiService createService() {
        httpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (!httpClient.interceptors().contains(httpLoggingInterceptor)) {
            httpClient.addInterceptor(httpLoggingInterceptor);
        }

        builder.baseUrl(API_BASE_URL);

        builder.client(httpClient.build());
        retrofit = builder.build();

        return retrofit.create(ApiService.class);
    }

    public static ApiService buildService(Context context) {
        return service = createService();
    }
}
