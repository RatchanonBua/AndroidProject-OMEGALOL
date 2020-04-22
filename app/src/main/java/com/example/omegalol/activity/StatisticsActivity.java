package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.omegalol.R;
import com.example.omegalol.adapter.StatisticsAdapter;
import com.example.omegalol.data_getter.StatisticsGetter;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String account = getIntent().getStringExtra("account");
        setContentView(R.layout.activity_statistics);
        generateStatisticsData(account);
    }

    private void generateStatisticsData(String account) {
        RecyclerView recyclerView = findViewById(R.id.statistics_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            StatisticsGetter getter = new StatisticsGetter(this, "tryndamere");
            StatisticsAdapter adapter = new StatisticsAdapter(this, getter.getMatchlist(), getter.getChampionList());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, "The error occurs during load data.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
