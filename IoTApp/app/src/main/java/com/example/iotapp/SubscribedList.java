package com.example.iotapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SubscribedList extends AppCompatActivity{
    LinearLayout linearLayout;
    static List<PlaceInformation> subcribedList = new ArrayList<>();
    static List<Double> distanceList = new ArrayList<>();
    List<View> viewList = new ArrayList<View>();
    int allSubscribedList = 0;
    static Pair<PlaceInformation,Double> subscribedItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribed_list);
        allSubscribedList = subcribedList.size();
        showList();
    }

    public void detailedParametersClick(View v)
    {
        Toast t = Toast.makeText(this, "Show pamameters at "+ subscribedItem.first.getName(), Toast.LENGTH_SHORT);
        t.show();
        Intent intent = new Intent(this, DetailedParameters.class);
        startActivity(intent);
    }
    public void createSubscribedItem(int id,final int i)
    {
        View placeSubsribed = viewList.get(i);
        placeSubsribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribedItem = new Pair<>(subcribedList.get(i), distanceList.get(i));
                detailedParametersClick(v);
            }
        });
    }
    public void showList() {
        linearLayout = findViewById(R.id.layoutList);
        linearLayout.removeAllViews();
        for(int i = 0; i < allSubscribedList; i++) {
            View placeSubscribed = getLayoutInflater().inflate(R.layout.subscribed_item, null, false);
            TextView nameView = placeSubscribed.findViewById(R.id.place_name);
            nameView.setText(subcribedList.get(i).getName());
            TextView distanceView = placeSubscribed.findViewById(R.id.distance);
            distanceView.setText("Cách trạm đo " + String.format("%.5f", distanceList.get(i)) + "km");
            TextView ratedView = placeSubscribed.findViewById(R.id.rated);
            ratedView.setText(subcribedList.get(i).getEvaluation() +"/10");
            viewList.add(placeSubscribed);
            createSubscribedItem(R.id.layoutList, i);
            linearLayout.addView(placeSubscribed);
        }
    }
}
