package com.example.shamsuddha.tourmate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.shamsuddha.tourmate.api_collections.RetrofitClient;
import com.example.shamsuddha.tourmate.api_collections.WeatherApi;
import com.example.shamsuddha.tourmate.models.forecast.Forecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastWeatherFragment extends Fragment {
    WeatherApi weatherApi;
    ListView forecastLV;
    List<List> forecastlist;
    String url="forecast/daily?q=Dhaka,bd&units=metric&appid=e384f9ac095b2109c751d95296f8ea76";
    String units="C";
    TextView tv1;
    ToggleButton tBTn1;
    //String unitText="C";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forcast_weather, container, false);
        forecastLV = view.findViewById(R.id.flist);
        tv1=view.findViewById(R.id.cityf);
        tBTn1=view.findViewById(R.id.unitsf);
        getForecastWeather(url);
        return view;
    }

    public void getForecastWeather(String url) {
        weatherApi = RetrofitClient.getRetrofitClient().create(WeatherApi.class);
            Call<Forecast> forecastCall = weatherApi.getForecastCall(url);
            forecastCall.enqueue(new Callback<Forecast>() {
                @Override
                public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                    Forecast forecast = response.body();
                    tv1.setText(String.valueOf(forecast.getCity().getName()));
                    ForecastAdapter forecastAdapter = new ForecastAdapter(getContext(), forecast.getList());
                    forecastLV.setAdapter(forecastAdapter);
                }

                @Override
                public void onFailure(Call<Forecast> call, Throwable t) {
                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        tBTn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tBTn1.isChecked()){
                    getForecastWeather("forecast/daily?q=Dhaka,bd&units=metric&appid=e384f9ac095b2109c751d95296f8ea76");
                    units="C";
                }
                else {
                    getForecastWeather("forecast/daily?q=Dhaka,bd&units=imperial&appid=e384f9ac095b2109c751d95296f8ea76");
                    units="F";
                }
            }
        });
        }


}
