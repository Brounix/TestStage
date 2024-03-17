package com.projet.ricketmorty.manager;

import android.text.TextUtils;
import android.util.Log;

import com.projet.ricketmorty.model.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityController {

    private final ApiManager apiManager;

    public MainActivityController() {
        apiManager = ApiManager.getInstance();
    }


    public void getRickMorty(IApiDataManagerCallBack callBack,int offset, int limit) {
        List<String> idList = new ArrayList<>();

        for (int i = offset + 1; i <= limit; i++) {
            idList.add(String.valueOf(i));
        }
        String idString = TextUtils.join(",", idList);

        Log.e("list", idString);


        Call<List<Result>> getRickMortyChar = apiManager.getApiService().getApi(idString);
        getRickMortyChar.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Result> rickMortyList = response.body();

                    if (rickMortyList != null) {
                        Log.e("Response", "Api found");
                    } else {
                        Log.e("Response", "No results found");
                    }
                    callBack.getApiResponseSuccess(rickMortyList);
                } else {
                    Log.e("Response", "Not successful: " + response.code());
                    callBack.getApiResponseError("Erreur: Le serveur a répondu avec le statut : " + response.code());
                }
            }


            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                Log.e("onFailure", "Erreur lors de la requête : " + t.getLocalizedMessage());
                callBack.getApiResponseError("Erreur lors de la requête : " + t.getLocalizedMessage());
            }
        });
    }
}

