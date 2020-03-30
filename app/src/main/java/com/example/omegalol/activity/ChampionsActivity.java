package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;

import com.example.omegalol.R;
import com.example.omegalol.getter.ChampionsGetter;
import com.example.omegalol.adapter.ChampionsRecyclerAdapter;

public class ChampionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_champions);
        generateChampionList();
    }

    private void generateChampionList() {
        RecyclerView recyclerView = findViewById(R.id.champions_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            ChampionsGetter getter = new ChampionsGetter(this);
            ChampionsRecyclerAdapter adapter = new ChampionsRecyclerAdapter(this, getter.getChampionList());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            finish();
        }
    }
}
