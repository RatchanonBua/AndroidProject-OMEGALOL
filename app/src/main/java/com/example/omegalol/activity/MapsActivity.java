package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;

import com.example.omegalol.R;
import com.example.omegalol.adapter.MapsAdapter;
import com.example.omegalol.getter.MapsGetter;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_maps);
        generateMapList();
    }

    private void generateMapList() {
        RecyclerView recyclerView = findViewById(R.id.maps_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            MapsGetter getter = new MapsGetter(this);
            MapsAdapter adapter = new MapsAdapter(this, getter.getMapList());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            finish();
        }
    }
}
