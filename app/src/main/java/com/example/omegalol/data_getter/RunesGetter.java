package com.example.omegalol.data_getter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.omegalol.R;
import com.example.omegalol.service.DDragonService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RunesGetter {
    private final Context context;

    public RunesGetter(Context context) {
        this.context = context;
    }

    private DDragonService createService(String uri) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(uri).addConverterFactory(
                GsonConverterFactory.create()).build();
        return retrofit.create(DDragonService.class);
    }

    private JSONArray getDataFromService(String uri, String version, String locale) {
        try {
            Call<ResponseBody> call = createService(uri).getRuneList(version, locale);
            ResponseBody body = call.execute().body();
            assert body != null;
            return new JSONArray(body.string());
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<JSONObject> getRuneList() throws Exception {
        JSONArray dataList = getDataFromService(context.getString(R.string.ddragon_uri)
                , context.getString(R.string.version), getSharedPreferencesLanguage());
        assert dataList != null;
        ArrayList<JSONObject> runeList = new ArrayList<>();
        for (int i = 0; i < dataList.length(); i++) {
            JSONObject json = dataList.getJSONObject(i);
            runeList.add(json);
        }
        return runeList;
    }

    private String getSharedPreferencesLanguage() {
        SharedPreferences preferences = context.getSharedPreferences("OMEGALOL", Context.MODE_PRIVATE);
        return preferences.getString("language", context.getString(R.string.default_locale));
    }
}
