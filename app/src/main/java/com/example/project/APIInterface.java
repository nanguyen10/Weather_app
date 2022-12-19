package com.example.project;

import com.example.project.Model.Asset;
import com.example.project.Model.Map;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("api/master/map/js")
    Call<Map> getMap();

    @GET("api/master/asset/user/current")
    Call<List<Asset>> getCurrent();

    @GET("api/master/asset/{assetID}")
    Call<Asset> getAsset(String assetID);//, @Header("Authorization") String auth);
}
