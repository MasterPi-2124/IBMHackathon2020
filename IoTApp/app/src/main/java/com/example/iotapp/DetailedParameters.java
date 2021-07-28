package com.example.iotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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
        TextView title = (TextView) findViewById(R.id.place_name);
        title.setText(SubscribedList.subscribedItem.first.getName());
        setData(SubscribedList.subscribedItem.first.getTemperature(),
                SubscribedList.subscribedItem.first.getHumidity(),
                SubscribedList.subscribedItem.first.getGhi(),
                SubscribedList.subscribedItem.first.getChieu_sang(),
                SubscribedList.subscribedItem.first.getGoc_mat_troi(),
                SubscribedList.subscribedItem.first.getLuong_mua(),
                SubscribedList.subscribedItem.first.getToc_do_gio(),
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

    public void setData(double _temperature, double _humidity, double _ghi, double _chieu_sang,
                        double _goc_mat_troi, double _luong_mua, double _toc_do_gio, String _stability){
        TableLayout detailed = findViewById(R.id.detailed);
        TextView ghiView = detailed.findViewById(R.id.ghi_value);
        ghiView.setText(String.format("%.1f",_ghi) + " kW/m2");
        TextView temperatureView = detailed.findViewById(R.id.temperature_value);
        temperatureView.setText(String.format("%.2f ",_temperature) +(char) 0x00B0+ "C");
        TextView humidityView = detailed.findViewById(R.id.humidity_value);
        humidityView.setText(String.format("%.2f",_humidity) + " %");
        TextView chieu_sangView = detailed.findViewById(R.id.chieu_sang);
        chieu_sangView.setText(String.format("%.1f",_chieu_sang) + " giờ");
        TextView goc_mat_troiView = detailed.findViewById(R.id.goc_mat_troi);
        goc_mat_troiView.setText(String.format("%.1f ",_goc_mat_troi) + (char) 0x00B0);
        TextView luong_muaView = detailed.findViewById(R.id.luong_mua);
        luong_muaView.setText(String.format("%.0f",_luong_mua) + " mm");
        TextView toc_do_gioView = detailed.findViewById(R.id.toc_do_gio);
        toc_do_gioView.setText(String.format("%.0f",_toc_do_gio) + " m/h");
        TextView stabilityView = detailed.findViewById(R.id.stability_value);
        stabilityView.setText(_stability);
        if(_stability == "KÉM"){
            stabilityView.setTextColor(Color.rgb(255, 199, 0));
        }
    }

}