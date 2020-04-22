package com.example.omegalol.data_getter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.omegalol.R;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatisticsGetter {
    private Context context;
    private RiotApi api;
    private Map<Integer, JSONObject> championList;
    private String account;

    @SuppressLint("UseSparseArrays")
    public StatisticsGetter(Context context, String account) {
        this.context = context;
        this.account = account;
        this.championList = new HashMap<>();
    }

    private void createRiotService() {
        ApiConfig config = new ApiConfig().setKey(context.getString(R.string.api_key));
        this.api = new RiotApi(config);
    }

    public Map<Integer, JSONObject> getChampionList() throws Exception {
        ChampionsGetter getter = new ChampionsGetter(this.context);
        ArrayList<JSONObject> list = getter.getChampionList();
        for (JSONObject champion: list) {
            int key = Integer.parseInt(champion.get("key").toString());
            championList.put(key, champion);
        }
        return championList;
    }

    public ArrayList<MatchReference> getMatchlist() throws Exception {
        this.createRiotService();
        Summoner summoner = api.getSummonerByName(Platform.NA, account);
        MatchList matchList = api.getMatchListByAccountId(Platform.NA, summoner.getAccountId());
        return new ArrayList<>(matchList.getMatches());
    }
}
