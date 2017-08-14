package com.jiayuqicz.mygaode;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import java.util.List;


public class WeatherFragment extends Fragment implements WeatherSearch.OnWeatherSearchListener {

    private TextView wind = null;
    private TextView weather = null;
    private TextView humidity = null;
    private TextView temperature = null;

    private GridLayout forcast_root = null;

    private String cityName = "北京市";

    private WeatherSearchQuery query = null;
    private WeatherSearch weatherSearch = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        searchForcastWeather();
        serchLiveWeather();

    }

    public void initView() {

        View view = getView();

        wind = (TextView) view.findViewById(R.id.wind_value);
        weather = (TextView) view.findViewById(R.id.weather_value);
        humidity = (TextView) view.findViewById(R.id.humidity_value);
        temperature = (TextView) view.findViewById(R.id.temperature_value);

        forcast_root = (GridLayout) view.findViewById(R.id.forcast_root);
        
    }

    public void searchForcastWeather() {

        query = new WeatherSearchQuery(cityName,WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        weatherSearch = new WeatherSearch(getContext());
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(query);
        weatherSearch.searchWeatherAsyn();

    }

    public void serchLiveWeather() {
        query = new WeatherSearchQuery(cityName,WeatherSearchQuery.WEATHER_TYPE_LIVE);
        weatherSearch = new WeatherSearch(getContext());
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(query);
        weatherSearch.searchWeatherAsyn();
    }


    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int rcode) {
        if(rcode == AMapException.CODE_AMAP_SUCCESS) {
            if(localWeatherLiveResult != null && localWeatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive live = localWeatherLiveResult.getLiveResult();
                weather.setText(live.getWeather());
                wind.setText(live.getWindPower());
                humidity.setText(live.getHumidity());
                temperature.setText(live.getTemperature());
            }
        }

    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult,
                                          int rcode) {
        if(rcode == AMapException.CODE_AMAP_SUCCESS) {
            if(localWeatherForecastResult != null && localWeatherForecastResult.getForecastResult()
                    != null && localWeatherForecastResult.getForecastResult().getWeatherForecast()
                    != null && localWeatherForecastResult.getForecastResult().getWeatherForecast()
                    .size()>0) {
                LocalWeatherForecast forcast = localWeatherForecastResult.getForecastResult();
                List<LocalDayWeatherForecast> forcastList = forcast.getWeatherForecast();

                for (LocalDayWeatherForecast forecast : forcastList) {
                    {
                        TextView textView = new TextView(getContext());
                        textView.setText(forecast.getDate());
                        textView.setTextSize(18);
                        textView.setTextColor(Color.WHITE);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        forcast_root.addView(textView);
                    }
                    {
                        TextView textView = new TextView(getContext());
                        textView.setText(forecast.getDayWeather()+"/"+forecast.getNightWeather());
                        textView.setTextSize(18);
                        textView.setTextColor(Color.WHITE);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        forcast_root.addView(textView);
                    }
                    {
                        TextView textView = new TextView(getContext());
                        textView.setText(forecast.getDayTemp()+ "/"+ forecast.getNightTemp());
                        textView.setTextSize(18);
                        textView.setTextColor(Color.WHITE);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        forcast_root.addView(textView);
                    }
                }

            }
        }

    }
}
