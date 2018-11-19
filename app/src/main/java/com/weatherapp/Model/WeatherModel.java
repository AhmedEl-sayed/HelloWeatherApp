package com.weatherapp.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {


        @SerializedName("list")
        @Expose
        private List<ListWeather> list = null;

        public List<ListWeather> getList() {
            return list;
        }

        public void setList(List<ListWeather> list) {
            this.list = list;
        }






    }
