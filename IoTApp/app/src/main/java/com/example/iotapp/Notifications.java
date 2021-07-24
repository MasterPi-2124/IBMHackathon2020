package com.example.iotapp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {
    LinearLayout linearLayout1 = null;
    LinearLayout linearLayout2 = null;
    LinearLayout linearLayout3 = null;
    LinearLayout allNotificationLayout = null;
    LinearLayout onlyBadLayout = null;
    LinearLayout onlyGoodLayout = null;
    List<View> allNotificationList = new ArrayList<View>();
    int viewListLength = 0;

    static List<PlaceInformation> notificationList = new ArrayList<>();
    int allNotificationStatus = notificationList.size();
    int []read = new int[allNotificationStatus];
    int []delete = new int[allNotificationStatus];

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        linearLayout1 = findViewById(R.id.notification_layout1);
        linearLayout2 = findViewById(R.id.notification_layout2);
        linearLayout3 = findViewById(R.id.notification_layout3);

        createNotificationList();
        allNotification();
    }
    public void allClick(View v)
    {
        Toast t = Toast.makeText(this, "Click \"All\"", Toast.LENGTH_SHORT);
        t.show();
        linearLayout1.setVisibility(View.VISIBLE);
        linearLayout2.setVisibility(View.INVISIBLE);
        linearLayout3.setVisibility(View.INVISIBLE);
        allNotification();
    }
    public void badClick(View v)
    {
        Toast t = Toast.makeText(this, "Click \"BAD\"", Toast.LENGTH_SHORT);
        t.show();
        linearLayout1.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.VISIBLE);
        linearLayout3.setVisibility(View.INVISIBLE);
        onlyBad();
    }
    public void goodClick(View v)
    {
        Toast t = Toast.makeText(this, "Click \"GOOD\"", Toast.LENGTH_SHORT);
        t.show();
        linearLayout1.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.INVISIBLE);
        linearLayout3.setVisibility(View.VISIBLE);
        onlyGood();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationList(){
        allNotificationList.clear();
        viewListLength = 0;
        for(int i = 0; i < allNotificationStatus; i++) {
            View notification = getLayoutInflater().inflate(R.layout.notification_item, null, false);
            PlaceInformation place = notificationList.get(i);
            TextView textView1 = (TextView) notification.findViewById(R.id.text1);
            textView1.setText(place.getStability());
            if(place.getStability() == "GOOD") textView1.setTextColor(Color.rgb(24,255,0));
            TextView textView2 = (TextView) notification.findViewById(R.id.text2);
            textView2.setText("Location: "+place.getName());
            TextView textView3 = (TextView) notification.findViewById(R.id.text3);
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);
            textView3.setText("Time: "+formattedDate );
            TextView textView4 = (TextView) notification.findViewById(R.id.text4);
            textView4.setText("Temperature: "+String.format("%.2f",place.getTemperature())+" K");
            TextView textView5 = (TextView) notification.findViewById(R.id.text5);
            textView4.setText("GHI: "+String.format("%.1f",place.getGhi())+" kWh/m2");
            TextView textView6 = (TextView) notification.findViewById(R.id.text6);
            textView4.setText("Humidity: "+String.format("%.2f",place.getHumidity())+"%");
            TextView textView7 = (TextView) notification.findViewById(R.id.text7);
            textView4.setText("Evaluation: "+ place.getEvaluation()+"./10");
            TextView textView8 = (TextView) notification.findViewById(R.id.text8);
            textView8.setText("N");

            allNotificationList.add(notification);
            viewListLength++;
        }
    }

    public void clearLayout(){
        allNotificationLayout = findViewById(R.id.layoutAll);
        allNotificationLayout.removeAllViews();
        onlyBadLayout = findViewById(R.id.layoutBad);
        onlyBadLayout.removeAllViews();
        onlyGoodLayout = findViewById(R.id.layoutGood);
        onlyGoodLayout.removeAllViews();
    }

    public void clickNew(final int i)
    {
        final View Notification = allNotificationList.get(i);
        Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView8 = (TextView) allNotificationList.get(i).findViewById(R.id.text8);
                textView8.setText("");
                read[i] = 1;
            }
        });
    }

    public void deleteNotification(final int i){
        final View Notification = allNotificationList.get(i);
        final ImageView deleteImage = (ImageView) Notification.findViewById(R.id.deleteImage);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotificationClick();
                delete[i] = 1;
                allNotificationLayout.removeView(Notification);
                onlyBadLayout.removeView(Notification);
                onlyGoodLayout.removeView(Notification);
            }
        });
    }


    public void allNotification(){
        clearLayout();

        for(int i = 0; i < allNotificationStatus; i++) {
            if(delete[i] == 1) continue;
            View Notification = allNotificationList.get(i);
            clickNew(i);
            deleteNotification(i);
            allNotificationLayout.addView(Notification);
        }
    }

    public void onlyBad(){
        clearLayout();

        for(int i = 0; i < allNotificationStatus; i++) {
            if(delete[i] == 1) continue;
            if(notificationList.get(i).getStability() == "BAD"){
                View Notification = allNotificationList.get(i);
                deleteNotification(i);
                onlyBadLayout.addView(Notification);
            }
        }
    }

    public void onlyGood(){
        clearLayout();

        for(int i = 0; i < allNotificationStatus; i++) {
            if(delete[i] == 1) continue;
            if(notificationList.get(i).getStability() == "GOOD"){
                View Notification = allNotificationList.get(i);
                deleteNotification(i);
                onlyGoodLayout.addView(Notification);
            }
        }
    }

    public void deleteNotificationClick()
    {
        Toast t = Toast.makeText(this, "\"Notification\" deleted", Toast.LENGTH_SHORT);
        t.show();
    }
}

