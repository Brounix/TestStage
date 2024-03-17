package com.projet.ricketmorty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.projet.ricketmorty.model.Result;
import com.squareup.picasso.Picasso;


public class CharInfoActivity extends AppCompatActivity{

    TextView txt;
    ImageView avatar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_info_layout);
        txt = findViewById(R.id.infoCharTxt);
        avatar = findViewById(R.id.avatar);


        Intent intent = getIntent();
        Result api = (Result) intent.getSerializableExtra("info");

        String contactInfo;

        if (api != null) {
            contactInfo =
                    "Nom : " + api.getName() + "\n" +
                    "Genre : " + api.getGender() + "\n" +
                    "Statue : " + api.getStatus() + "\n" +
                    "Espèce : " + api.getSpecies() ;

            txt.setText(contactInfo);
            Picasso.get().load(api.getImage()).into(avatar);

        } else {
            txt.setText("Aucune information de contact disponible.");
        }

        Button BQuit = findViewById(R.id.returnBtn);
        BQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BQuit.setBackgroundDrawable(ContextCompat.getDrawable(CharInfoActivity.this, R.drawable.btn_round_press));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BQuit.setBackgroundDrawable(ContextCompat.getDrawable(CharInfoActivity.this, R.drawable.btn_round));
                    }
                }, 100);
                finish();
            }
        });

    }
}
