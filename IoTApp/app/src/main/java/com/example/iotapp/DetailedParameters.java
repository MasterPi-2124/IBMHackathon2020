package com.example.iotapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DetailedParameters extends AppCompatActivity {
    static boolean sub = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_parameters);
        setData(SubscribedList.subscribedItem.first.getTemperature(),
                SubscribedList.subscribedItem.first.getHumidity(),
                SubscribedList.subscribedItem.first.getGhi(),
                SubscribedList.subscribedItem.first.getStability());
    }

    public void unsubscribeClick(View v) {
        Toast t = null;
        TextView textView = findViewById(R.id.unsubscribe);
        int len = SubscribedList.subcribedList.size();
        if (SubscribedList.subscribedItem.first != null && SubscribedList.subcribedList.contains(SubscribedList.subscribedItem.first)) {
            sub = true;
            textView.setText("Subscribe");
            t = Toast.makeText(this, "Unsubscribed", Toast.LENGTH_SHORT);
            List<Pair<PlaceInformation,Double>> tempList = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                PlaceInformation location = SubscribedList.subcribedList.get(i);
                double distance_i = SubscribedList.distanceList.get(i);
                if (SubscribedList.subscribedItem.first != location) {
                    tempList.add(new Pair<>(location,distance_i));
                }
            }
            len--;
            SubscribedList.subcribedList.clear();
            SubscribedList.distanceList.clear();
            for(int i = 0; i < len; i++){
                SubscribedList.subcribedList.add(tempList.get(i).first);
                SubscribedList.distanceList.add(tempList.get(i).second);
            }
        } else if (SubscribedList.subscribedItem.first != null && !SubscribedList.subcribedList.contains(SubscribedList.subscribedItem.first)) {
            sub = false;
            textView.setText("Unsubscribe");
            t = Toast.makeText(this, "Subscribed", Toast.LENGTH_SHORT);
            SubscribedList.subcribedList.add(SubscribedList.subscribedItem.first);
            SubscribedList.distanceList.add(SubscribedList.subscribedItem.second);
        } else {
            t = Toast.makeText(this, "None", Toast.LENGTH_SHORT);
        }
        t.show();
    }

    public void setData(double _temperature, double _humidity, double _ghi, String _stability){
        TableLayout detailed = findViewById(R.id.detailed);
        TextView temperatureView = detailed.findViewById(R.id.temperature_value);
        temperatureView.setText(String.format("%.2f",_temperature) + " F");
        TextView humidityView = detailed.findViewById(R.id.humidity_value);
        humidityView.setText(String.format("%.2f",_humidity) + " %");
        TextView ghiView = detailed.findViewById(R.id.ghi_value);
        ghiView.setText(String.format("%.1f",_ghi) + " kWh/m2");
        TextView stabilityView = detailed.findViewById(R.id.stability_value);
        stabilityView.setText(_stability);
    }

}