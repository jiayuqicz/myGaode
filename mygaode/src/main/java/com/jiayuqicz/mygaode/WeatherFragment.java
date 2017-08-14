package com.jiayuqicz.mygaode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView weatherForcast = null;

    private String cityName = "北京市";

    private WeatherSearchQuery query = null;
    private WeatherSearch weatherSearch = null;
    private LocalWeatherForecast forcast = null;
    private LocalWeatherLive live = null;

    private List<LocalDayWeatherForecast> forcastList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        searchForcastWeather();
        serchLiveWeather();
    }

    public void initView() {
        wind = getView().findViewById(R.id.wind_value);
        weather = getView().findViewById(R.id.weather_value);
        humidity = getView().findViewById(R.id.humidity_value);
        temperature = getView().findViewById(R.id.temperature_value);
        weatherForcast = getView().findViewById(R.id.weather_forcast_value);


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

    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult,
                                          int rcode) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if(localWeatherForecastResult != null && localWeatherForecastResult.getForecastResult()
                    != null && localWeatherForecastResult.getForecastResult().getWeatherForecast()
                    != null && localWeatherForecastResult.getForecastResult().getWeatherForecast()
                    .size()>0) {
                forcast = localWeatherForecastResult.getForecastResult();
                forcastList = forcast.getWeatherForecast();

            }
        }

    }
}
