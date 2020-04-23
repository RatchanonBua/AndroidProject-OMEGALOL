package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.omegalol.R;
import com.example.omegalol.adapter.ItemsAdapter;
import com.example.omegalol.data_getter.ItemsGetter;

public class ItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_items);
        generateItemList();
    }

    private void generateItemList() {
        RecyclerView recyclerView = findViewById(R.id.items_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            ItemsGetter getter = new ItemsGetter(this);
            ItemsAdapter adapter = new ItemsAdapter(this, getter.getItemList(), getter.getItemJSON());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, "The error occurs during load data.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
