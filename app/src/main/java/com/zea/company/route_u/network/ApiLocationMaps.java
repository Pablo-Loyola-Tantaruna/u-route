package com.zea.company.route_u.network;

import com.zea.company.route_u.model.ResponseGeneral;
import com.zea.company.route_u.model.results;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiLocationMaps {
    @GET("place/nearbysearch/json?")
    Call<ResponseGeneral> getNearbyLocation(
            @Query("location")String location,
            @Query("radius")String radius,
            @Query("type")String type,
            @Query("key")String key
    );
    @GET("directions/json?mode-walking&")
    Call<ResponseGeneral> getDataAbout(
            @Query("origin")String location,
            @Query("destination")String radius,
            @Query("key")String key
    );
}
