package com.projet.ricketmorty.service;

import com.projet.ricketmorty.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("character/{ids}")
    Call<List<Result>> getApi(@Path("ids") String ids);

}

