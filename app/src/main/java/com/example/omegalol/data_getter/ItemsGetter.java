package com.example.omegalol.data_getter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.omegalol.R;
import com.example.omegalol.service.DDragonService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemsGetter {
    private final Context context;
    private final String[] duplicate = new String[]{
            "2424", "3175", "3400", "3410", "3422", "3455", "3514", "3600"};

    public ItemsGetter(Context context) {
        this.context = context;
    }

    private DDragonService createService(String uri) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(uri).addConverterFactory(
                GsonConverterFactory.create()).build();
        return retrofit.create(DDragonService.class);
    }

    private JSONObject getDataFromService(String uri, String version, String locale) {
        try {
            Call<ResponseBody> call = createService(uri).getItemList(version, locale);
            ResponseBody body = call.execute().body();
            assert body != null;
            JSONObject result = new JSONObject(body.string());
            return new JSONObject(result.get("data").toString());
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<JSONObject> getItemList() throws Exception {
        JSONObject dataList = getItemJSON();
        assert dataList != null;
        Iterator<String> keys = dataList.keys();
        ArrayList<JSONObject> itemList = new ArrayList<>();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONObject obj = (JSONObject) dataList.get(key);
            if (!Arrays.asList(duplicate).contains(key)) {
                itemList.add(obj);
            }
        }
        return sortJSONObject(itemList);
    }

    public JSONObject getItemJSON() {
        return getDataFromService(context.getString(R.string.ddragon_uri)
                , context.getString(R.string.version), getSharedPreferencesLanguage());
    }

    private ArrayList<JSONObject> sortJSONObject(ArrayList<JSONObject> itemList) {
        Collections.sort(itemList, new Comparator<JSONObject>() {
            private final String KEY_NAME = "name";

            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String valA = "";
                String valB = "";
                try {
                    valA = o1.get(KEY_NAME).toString();
                    valB = o2.get(KEY_NAME).toString();
                } catch (JSONException ignored) {}
                return valA.compareTo(valB);
            }
        });
        return itemList;
    }

    private String getSharedPreferencesLanguage() {
        SharedPreferences preferences = context.getSharedPreferences("OMEGALOL", Context.MODE_PRIVATE);
        return preferences.getString("language", context.getString(R.string.default_locale));
    }
}
