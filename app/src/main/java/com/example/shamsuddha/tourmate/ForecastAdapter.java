package com.example.shamsuddha.tourmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shamsuddha.tourmate.models.forecast.List;
import com.example.shamsuddha.tourmate.models.forecast.Weather;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

public class ForecastAdapter extends ArrayAdapter<List> {
    Context context;
    java.util.List<List> forecastlist;

    public ForecastAdapter(@NonNull Context context, @NonNull java.util.List<List> forecastlist) {
        super(context, R.layout.forecast_weather_layout, forecastlist);
        this.context = context;
        this.forecastlist = forecastlist;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.forecast_weather_layout, parent, false);
        TextView days = view.findViewById(R.id.day);
        TextView date = view.findViewById(R.id.date);
        TextView min = view.findViewById(R.id.min);
        TextView max = view.findViewById(R.id.max);
        ImageView fimage = view.findViewById(R.id.fimage);
        List list = forecastlist.get(position);
        ///max.setText(String.valueOf(list.getTemp().getMax()));
        date.setText(String.valueOf(list.getDt()));
        if (list.getTemp().getMin()>70&&list.getTemp().getMax()>70){
            min.setText("Min:"+String.valueOf(list.getTemp().getMin())+"°F");
            max.setText("Max:"+String.valueOf(list.getTemp().getMax())+"°F");
        }
        else {
            min.setText("Min:" + String.valueOf(list.getTemp().getMin()) + "°C");
            max.setText("Max:" + String.valueOf(list.getTemp().getMax()) + "°C");
        }
        date.setText(String.valueOf(dateformat(list.getDt())));
        days.setText(String.valueOf(dayformat(list.getDt())));
        java.util.List<Weather> weatherList = list.getWeather();
        for (Weather obj : weatherList) {
            String icon = String.valueOf(obj.getIcon());
            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(fimage);
        }

        return view;
    }

    public String dateformat(int timestamp) {
        //"EEE, MMM d, ''yy"  Wed, Jul 4, '01
        //"h:mm a"    12:08 PM
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000L);
        String date = DateFormat.format("MMMM d, yyyy", cal).toString();

        return date;
    }

    public String dayformat(int timestamp) {
        //"EEE, MMM d, ''yy"  Wed, Jul 4, '01
        //"h:mm a"    12:08 PM
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000L);
        String days = DateFormat.format("EEEE", cal).toString();

        return days;
    }
}
