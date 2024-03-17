package com.projet.ricketmorty.manager;

import com.projet.ricketmorty.model.Result;
import com.projet.ricketmorty.model.RickMorty;

import java.util.List;

public interface IApiDataManagerCallBack {

    void getApiResponseSuccess(List<Result> rickMorty);
    void getApiResponseError(String message);
}
