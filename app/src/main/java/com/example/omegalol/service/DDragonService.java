package com.example.omegalol.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DDragonService {
    @GET("cdn/{version}/data/{locale}/championFull.json")
    Call<ResponseBody> getChampionList(
            @Path("version") String version,
            @Path("locale") String locale
    );

    @GET("cdn/{version}/data/{locale}/item.json")
    Call<ResponseBody> getItemList(
            @Path("version") String version,
            @Path("locale") String locale
    );

    @GET("cdn/{version}/data/{locale}/runesReforged.json")
    Call<ResponseBody> getRuneList(
            @Path("version") String version,
            @Path("locale") String locale
    );

    @GET("cdn/{version}/data/{locale}/map.json")
    Call<ResponseBody> getMapList(
            @Path("version") String version,
            @Path("locale") String locale
    );
}
