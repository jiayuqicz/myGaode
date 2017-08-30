package com.jiayuqicz.mygaode.weather;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.jiayuqicz.mygaode.R;
import com.jiayuqicz.mygaode.main.MainActivity;

import java.util.List;


public class WeatherFragment extends Fragment implements WeatherSearch.OnWeatherSearchListener {

    private TextView wind = null;
    private TextView weather = null;
    private TextView humidity = null;
    private TextView temperature = null;

    private String cityName = "北京市";

    private WeatherSearchQuery query = null;
    private WeatherSearch weatherSearch = null;

    private SharedPreferences share = MainActivity.share;

    public static WeatherFragment newIntance() {
        return new WeatherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_fragment_weather, container, false);
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
    }

    public void searchForcastWeather() {

        query = new WeatherSearchQuery(cityName,WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        weatherSearch = new WeatherSearch(getActivity());
        weatherSearch.setOnWeatherSearchListener(this);
        weatherSearch.setQuery(query);
        weatherSearch.searchWeatherAsyn();

    }

    public void serchLiveWeather() {
        query = new WeatherSearchQuery(cityName,WeatherSearchQuery.WEATHER_TYPE_LIVE);
        weatherSearch = new WeatherSearch(getActivity());
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
            if (localWeatherForecastResult != null && localWeatherForecastResult.getForecastResult()
                    != null && localWeatherForecastResult.getForecastResult().getWeatherForecast()
                    != null && localWeatherForecastResult.getForecastResult().getWeatherForecast()
                    .size() > 0) {
                LocalWeatherForecast forclocalWeatherForecastst = localWeatherForecastResult
                        .getForecastResult();
                List<LocalDayWeatherForecast> forcastList = forclocalWeatherForecastst
                        .getWeatherForecast();

                LocalDayWeatherForecast localDayWeatherForecast;
                TextView textView;

                for (int i = 0; i < forcastList.size(); i++) {

                    //获取第i天的天气
                    localDayWeatherForecast= forcastList.get(i);

                    //根据设置动态调整当天的天气是否显示
                    if(!share.getBoolean("show_today", true) && i==0) {

                        getView().findViewWithTag("day0_forcast_empty")
                                .setVisibility(View.GONE);

                        getView().findViewWithTag("Date_value_forcast_day" + String.valueOf(i))
                                .setVisibility(View.GONE);

                        getView().findViewWithTag("weather_value_forcast_day" + String.valueOf(i))
                                .setVisibility(View.GONE);

                        getView().findViewWithTag("temperature_value_forcast_day"
                                + String.valueOf(i)).setVisibility(View.GONE);
                        continue;
                    }

                    //日期
                    textView = (TextView) getView().findViewWithTag("Date_value_forcast_day" +
                            String.valueOf(i));
                    textView.setText(localDayWeatherForecast.getDate());

                    //天气
                    textView = (TextView) getView()
                                .findViewWithTag("weather_value_forcast_day" + String.valueOf(i));
                    textView.setText(localDayWeatherForecast.getDayWeather() + "/" +
                            localDayWeatherForecast.getNightWeather());

                    //温度
                    textView = (TextView) getView().findViewWithTag("temperature_value_forcast_day"
                            + String.valueOf(i));
                    textView.setText(localDayWeatherForecast.getDayTemp() + "/"
                            + localDayWeatherForecast.getNightTemp());


                }
            }
        }
    }

}

