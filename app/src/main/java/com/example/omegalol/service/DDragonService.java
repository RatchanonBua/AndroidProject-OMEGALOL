package com.example.omegalol.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DDragonService {
    @GET("cdn/{version}/data/{locale}/champion.json")
    Call<ResponseBody> getChampionList(
            @Path("version") String version,
            @Path("locale") String locale
    );
}
