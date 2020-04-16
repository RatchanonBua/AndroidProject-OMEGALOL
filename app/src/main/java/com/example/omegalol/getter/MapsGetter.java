package com.example.omegalol.getter;

import android.content.Context;

import com.example.omegalol.R;
import com.example.omegalol.service.DDragonService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsGetter {
    private Context context;

    public MapsGetter(Context context) {
        this.context = context;
    }

    private DDragonService createService(String uri) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(uri).addConverterFactory(
                GsonConverterFactory.create()).build();
        return retrofit.create(DDragonService.class);
    }

    private JSONObject getDataFromService(String uri, String version, String locale) {
        try {
            Call<ResponseBody> call = createService(uri).getMapList(version, locale);
            ResponseBody body = call.execute().body();
            assert body != null;
            JSONObject result = new JSONObject(body.string());
            return new JSONObject(result.get("data").toString());
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<JSONObject> getMapList() throws Exception {
        JSONObject dataList = getDataFromService(context.getString(R.string.ddragon_uri)
                , context.getString(R.string.version), context.getString(R.string.locale));
        assert dataList != null;
        Iterator<String> keys = dataList.keys();
        ArrayList<JSONObject> mapList = new ArrayList<>();
        while (keys.hasNext()) {
            String key = keys.next();
            mapList.add((JSONObject) dataList.get(key));
        }
        return mapList;
    }
}
