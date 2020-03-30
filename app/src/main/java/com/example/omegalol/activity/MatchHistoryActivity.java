package com.example.omegalol.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omegalol.R;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class MatchHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String account = getIntent().getStringExtra("account");
        setContentView(R.layout.activity_matchhistory);
        callHistory(account);
    }

    private void callHistory(String account) {
        ApiConfig config = new ApiConfig().setKey(getString(R.string.api_key));
        RiotApi api = new RiotApi(config);
        try {
            Summoner summoner = api.getSummonerByName(Platform.NA, account);
            MatchList matchlist = api.getMatchListByAccountId(Platform.NA, summoner.getAccountId());
        } catch (Exception e) {
            ImageView notfound_img = findViewById(R.id.notfound_img);
            TextView notfound_txt = findViewById(R.id.notfound_txt);
            notfound_img.setImageResource(R.drawable.app_notfound);
            notfound_txt.setText(R.string.match_notfound);
        }
    }
}
