package com.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    String[] cityNames, cityIDs;
    ListView cityListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define my ListView
        cityListView = findViewById(R.id.mylist);

        //Get my arrays (city names and IDs )
        cityNames = getResources().getStringArray(R.array.city);
        cityIDs = getResources().getStringArray(R.array.ids);

        //set my Basic Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.list_city_name, cityNames);
        cityListView.setAdapter(adapter);

        //Add click event fot each item in my listview
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Navigate to weather page
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                intent.putExtra("city", cityIDs[position]);
                intent.putExtra("name", cityNames[position]);
                startActivity(intent);
            }
        });

    }
    /*
        Contact us Menu code
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contactus_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cont_btn) {
            Intent intent = new Intent(this, ContactusActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
