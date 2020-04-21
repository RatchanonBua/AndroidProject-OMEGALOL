package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;

import com.example.omegalol.R;
import com.example.omegalol.view_object.ChampionDetailsView;

import org.json.JSONObject;

import java.util.Objects;

public class ChampionDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_champion_details);

        ChampionDetailsView object = new ChampionDetailsView(this);
        object.setChampionViewObject();
        generateChampionDetails(object);
    }

    private void generateChampionDetails(ChampionDetailsView object) {
        try {
            JSONObject champion = new JSONObject(Objects.requireNonNull(getIntent().getStringExtra("Champion")));
            object.setChampionMainDetails(champion);
            object.setChampionTipsButton(champion);
            object.setSkillDetailsView(champion);
            object.setChampionImageObject(champion);
        } catch (Exception e) {
            finish();
        }
    }
}
