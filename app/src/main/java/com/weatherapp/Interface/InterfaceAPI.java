package com.weatherapp.Interface;

import com.weatherapp.Model.ListWeather;
import com.weatherapp.Model.Main;
import com.weatherapp.Model.WeatherModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceAPI {

    @GET("data/2.5/forecast")
    Call<WeatherModel> getCityWeather(
            @Query("id") String id,
            @Query("appid") String appID
    );

}
