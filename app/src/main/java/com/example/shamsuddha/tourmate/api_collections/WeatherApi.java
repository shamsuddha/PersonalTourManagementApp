package com.example.shamsuddha.tourmate.api_collections;

import com.example.shamsuddha.tourmate.models.CurrentWeather;
import com.example.shamsuddha.tourmate.models.forecast.Forecast;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherApi {

    //"weather?q=Dhaka,bd&units=metric&appid=e384f9ac095b2109c751d95296f8ea76"
    @GET()
    Call<CurrentWeather> getCurrentWeatherCall(@Url String url);

    //"forecast/daily?q=Dhaka,bd&units=metric&appid=e384f9ac095b2109c751d95296f8ea76"
    @GET()
    Call<Forecast> getForecastCall(@Url String url);
}
