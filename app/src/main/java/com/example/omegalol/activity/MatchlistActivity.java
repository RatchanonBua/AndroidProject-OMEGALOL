package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omegalol.R;
import com.example.omegalol.adapter.MatchlistAdapter;
import com.example.omegalol.getter.MatchlistGetter;

public class MatchlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String account = getIntent().getStringExtra("account");
        setContentView(R.layout.activity_matchlist);
        generateMatchHistory(account);
    }

    private void generateMatchHistory(String account) {
        RecyclerView recyclerView = findViewById(R.id.matchlist_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            MatchlistGetter getter = new MatchlistGetter(this, "tryndamere");
            MatchlistAdapter adapter = new MatchlistAdapter(this, getter.getSummonerMatchlist(), getter.getChampionList());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            ImageView notfound_img = findViewById(R.id.notfound_img);
            TextView notfound_txt = findViewById(R.id.notfound_txt);
            notfound_img.setImageResource(R.drawable.app_notfound);
            notfound_txt.setText(R.string.match_notfound);
        }
    }
}
