package com.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weatherapp.Adapter.WeatherAdapter;
import com.weatherapp.Interface.InterfaceAPI;
import com.weatherapp.Model.WeatherModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    public static final String API_KEY = "ba490b85b56ba98b0b63728860d5cd3b";
    RecyclerView recyclerView;
    //List<ListWeather> weatherModelList = new ArrayList<>();
    List<WeatherModel> weatherModelList = new ArrayList<>();
    TextView cityName;
    String cityID, name;
    SharedPreferences appSharedPrefs;
    String json;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        recyclerView = findViewById(R.id.myRecycler);
        cityName = findViewById(R.id.cityName);

        //Get the ID of the city
        Intent intent = getIntent();
        cityID = intent.getStringExtra("city");
        name = intent.getStringExtra("name");

        cityName.setText(name);

        ProgressDialoog();
        getData(cityID);


        /*
             Retrieve data during offline mode
         */
        if (!isNetworkAvailable()) {
            progressDialog.dismiss();
            try {
                appSharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(this.getApplicationContext());
                Gson gson = new Gson();

                json = appSharedPrefs.getString("MyObject", "");

                Type type = new TypeToken<List<WeatherModel>>() {
                }.getType();
                List<WeatherModel> students = gson.fromJson(json, type);
                for (int i = 0; i < students.size(); i++) {
                    weatherModelList.add(students.get(i));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));
                WeatherAdapter adapter = new WeatherAdapter(weatherModelList);
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                Toast.makeText(WeatherActivity.this, "There isn't saved data yet", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void getData(String cityID) {

        //Using Retrofit as our service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceAPI myInterface = retrofit.create(InterfaceAPI.class);
        Call<WeatherModel> connection = myInterface.getCityWeather(
                cityID,
                API_KEY
        );

        connection.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                } else {
                    //add our weather data to the list
                    for (int i = 0; i < response.body().getList().size(); i++) {
                        weatherModelList.add(response.body());
                    }
                /*
                      Save data during offline mode
                 */
                    appSharedPrefs = PreferenceManager
                            .getDefaultSharedPreferences(WeatherActivity.this);
                    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(weatherModelList);
                    prefsEditor.putString("MyObject", json);
                    prefsEditor.commit();

                    //send list to our RecyclerView Adapter
                    recyclerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));
                    WeatherAdapter adapter = new WeatherAdapter(weatherModelList);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.v("MainActivity", " not successful" + t.getMessage());
            }
        });
    }

    /*
        Checking on Internet state
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*
        progress dialog start untill data shown
     */
    public void ProgressDialoog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }
}
