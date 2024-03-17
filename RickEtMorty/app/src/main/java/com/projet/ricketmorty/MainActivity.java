package com.projet.ricketmorty;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projet.ricketmorty.manager.IApiDataManagerCallBack;
import com.projet.ricketmorty.manager.MainActivityController;
import com.projet.ricketmorty.model.Result;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IApiDataManagerCallBack {
    private static final int LIMIT = 20;
    ArrayList<Result> resultArrayList;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    boolean load = false;
    private int itemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        getApiData(0,LIMIT);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItemPosition;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                int itemCountBeforeLoad = adapter.getItemCount();


                if (lastVisibleItemPosition >= itemCountBeforeLoad-1 && !load) {
                    itemCount += LIMIT;
                    getApiData(itemCount,itemCount+LIMIT);
                    load = true;
                }
            }
        });
    }

    private void getApiData(int offset, int limit) {
        MainActivityController mainActivityController = new MainActivityController();
        mainActivityController.getRickMorty(this, offset, limit);
    }

    @Override
    public void getApiResponseSuccess(List<Result> rickMorty) {
        resultArrayList = new ArrayList<>(rickMorty);
        adapter.addItems(resultArrayList);
        recyclerView.setAdapter(adapter);
        load = false;
        recyclerView.scrollToPosition(itemCount-5);
    }

    @Override
    public void getApiResponseError(String message) {
        // Gérer l'erreur en affichant une boîte de dialogue
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Erreur")
                .setMessage("Une erreur est survenue lors de la recherche de données : " + message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                .setIcon(R.drawable.ic_launcher_background)
                .create()
                .show();
    }

}