package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.omegalol.R;
import com.example.omegalol.adapter.SummonerSpellsAdapter;
import com.example.omegalol.data_getter.SummonerSpellsGetter;

public class SummonerSpellsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_summoner_spell);
        generateSummonerSpellsList();
    }

    private void generateSummonerSpellsList() {
        RecyclerView recyclerView = findViewById(R.id.summoner_spells_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            SummonerSpellsGetter getter = new SummonerSpellsGetter(this);
            SummonerSpellsAdapter adapter = new SummonerSpellsAdapter(this, getter.getSummonerSpellList());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, "The error occurs during load data.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
