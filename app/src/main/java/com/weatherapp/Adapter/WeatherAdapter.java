package com.weatherapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weatherapp.Model.WeatherModel;
import com.weatherapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.myViewHolder> {

    List<WeatherModel> weatherList;
    double ferhh,cel;
    String celDegree;
    public WeatherAdapter(List<WeatherModel> weatherList) {
        this.weatherList=weatherList;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_day_details,parent,false);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        WeatherModel weatherModel = weatherList.get(position);
        holder.dateTextView.setText(weatherModel.getList().get(position).getDtTxt());
        //convert fahrenheit to cel
        String ferh= String.valueOf(weatherModel.getList().get(position).getMain().getTemp());
         ferhh = Float.valueOf(ferh);
         cel = ferhh - 273.15;
         cel=Double.parseDouble(new DecimalFormat("##.#").format(cel));
         celDegree = String.valueOf(cel)+" °C";
        holder.tempTextView.setText(celDegree);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
            TextView dateTextView , tempTextView ;
        public myViewHolder(View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.date);
            tempTextView = itemView.findViewById(R.id.temp);
        }
    }
}
