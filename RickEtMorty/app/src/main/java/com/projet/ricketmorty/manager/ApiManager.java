package com.projet.ricketmorty.manager;

import com.projet.ricketmorty.service.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    final static String BASE_URL = "https://rickandmortyapi.com/api/";

    private ApiService apiService = null;

    private static ApiManager instance;

    public ApiService getApiService() {
        return apiService;
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }

        return instance;
    }

    private ApiManager() {
        createRetrofitApi();
    }

    private void createRetrofitApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofitApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofitApi.create(ApiService.class);
    }

}
