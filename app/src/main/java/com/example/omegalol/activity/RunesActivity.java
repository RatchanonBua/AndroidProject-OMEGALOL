package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;

import com.example.omegalol.R;
import com.example.omegalol.adapter.RunesAdapter;
import com.example.omegalol.getter.RunesGetter;

public class RunesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_runes);
        generateRuneList();
    }

    private void generateRuneList() {
        RecyclerView recyclerView = findViewById(R.id.runes_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            RunesGetter getter = new RunesGetter(this);
            RunesAdapter adapter = new RunesAdapter(this, getter.getRuneList());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            finish();
        }
    }
}
