package com.example.iotapp;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubscribeMore extends AppCompatActivity implements OnMapReadyCallback {
    static boolean sub =  true;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    SearchView searchView;
    //static List<PlaceInformation> places = new ArrayList<>();
    static int placesListLength = 0;
    PlaceInformation place = null;
    double distance = 0;

    public static PlaceInformation[] places = new PlaceInformation[]{
        new PlaceInformation("Bách Khoa Hà Nội", 21.005106, 105.843371, 300.13, 88.88, 1200, 8,"GOOD" ),
        new PlaceInformation("Phương Mai Hà Nội", 21.004224, 105.839673, 300.13, 88.88, 1200, 9,"GOOD" ),
        new PlaceInformation("Đống Đa Hà Nội", 21.009368, 105.824322, 300.13, 88.88, 1200, 4,"BAD" ),
        new PlaceInformation("Giáp Bát Hà Nội", 20.983507, 105.841220, 300.13, 88.88, 1200, 2,"BAD" ),
        new PlaceInformation("Long Biên Hà Nội", 21.018348, 105.882664, 300.13, 88.88, 1200, 7, "GOOD" ),
        new PlaceInformation("Hoàng Mai Hà Nội", 20.970246, 105.846137, 300.13, 88.88, 1200, 5, "BAD" ),
        new PlaceInformation("Bạch Mai Hà Nội", 20.999733, 105.850519, 300.13, 88.88, 1200, 3,"BAD" ),
        new PlaceInformation("Minh Khai Hà Nội", 20.995505, 105.856822, 300.13, 88.88, 1200, 6,"BAD" ),
    };

    List<LatLng> latLngList = new ArrayList<>();

    public static String[] locationList = new String[] {
            "Bách Khoa Hà Nội",
            "Phương Mai",
            "Đống Đa",
            "Giáp Bát",
            "Long Biên",
            "Hoàng Mai",
            "Bạch Mai",
            "Minh Khai"
    };

    public float getDistance(LatLng my_latlong, LatLng frnd_latlong) {

        Location l1 = new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);
        Location l2 = new Location("Two");
        l2.setLatitude(frnd_latlong.latitude);
        l2.setLongitude(frnd_latlong.longitude);

        return l1.distanceTo(l2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribe_more);
        placesListLength = places.length;

        searchView = findViewById(R.id.search_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                Geocoder geocoder = new Geocoder((SubscribeMore.this));
                try{
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert addressList != null;
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

                float minDistance = getDistance(latLng,latLngList.get(0));
                int iDistance = 0;

                for(int i = 1; i < placesListLength; i++) {
                    if(minDistance > getDistance(latLng,latLngList.get(i))){
                        minDistance = getDistance(latLng,latLngList.get(i));
                        iDistance = i;
                    }
                }
                TextView textView = findViewById(R.id.nearest_place);
                TextView tv = findViewById(R.id.place);
                if(minDistance < 1000) {
                    String text = "Your nearest location is";
                    textView.setText(text);
                    text = places[iDistance].getName();
                    tv.setText(text);
                    TextView textView1 = findViewById(R.id.subscribe);
                    place = places[iDistance];
                    distance = (double)minDistance/1000.0;
                    if (!SubscribedList.subcribedList.contains(place)) {
                        textView1.setText("Subscribe");
                    } else {
                        textView1.setText("Unsubscribe");
                    }
                }
                else {
                    String text = "Sorry! There are no places near the location you've chosen.";
                    textView.setText(text);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }

    public Bitmap iconMarker(int point){
        switch(point){
            case 0: return BitmapFactory.decodeResource(getResources(),R.drawable.khong);
            case 1: return BitmapFactory.decodeResource(getResources(),R.drawable.mot);
            case 2: return BitmapFactory.decodeResource(getResources(),R.drawable.hai);
            case 3: return BitmapFactory.decodeResource(getResources(),R.drawable.ba);
            case 4: return BitmapFactory.decodeResource(getResources(),R.drawable.bon);
            case 5: return BitmapFactory.decodeResource(getResources(),R.drawable.nam);
            case 6: return BitmapFactory.decodeResource(getResources(),R.drawable.sau);
            case 7: return BitmapFactory.decodeResource(getResources(),R.drawable.bay);
            case 8: return BitmapFactory.decodeResource(getResources(),R.drawable.tam);
            case 9: return BitmapFactory.decodeResource(getResources(),R.drawable.chin);
            case 10: return BitmapFactory.decodeResource(getResources(),R.drawable.muoi);
        }
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(int i = 0; i < placesListLength; i++){
            LatLng latLng = new LatLng(places[i].getLatitude(), places[i].getLongtitude());
            latLngList.add(latLng);
            mMap.addMarker(new MarkerOptions().position(latLng).title(locationList[i]).icon(BitmapDescriptorFactory.fromBitmap(iconMarker(places[i].getEvaluation()))));
        }
        // Add a marker in Sydney and move the camera
        LatLng center = new LatLng(20.997823, 105.841030);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 14));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onMapClick(LatLng latLng) {
                //mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

                float minDistance = getDistance(latLng,latLngList.get(0));
                int iDistance = 0;

                for(int i = 1; i < placesListLength; i++) {
                    if(minDistance > getDistance(latLng,latLngList.get(i))){
                        minDistance = getDistance(latLng,latLngList.get(i));
                        iDistance = i;
                    }
                }
                TextView textView = findViewById(R.id.nearest_place);
                TextView tv = findViewById(R.id.place);
                if(minDistance < 1000) {
                    String text = "Your nearest location is";
                    textView.setText(text);
                    text = places[iDistance].getName();
                    tv.setText(text);
                    TextView textView1 = findViewById(R.id.subscribe);
                    place = places[iDistance];
                    distance = (double)minDistance/1000.0;
                    if (!SubscribedList.subcribedList.contains(place)) {
                        textView1.setText("Subscribe");
                    } else {
                        textView1.setText("Unsubscribe");
                    }
                }
                else {
                    String text = "Sorry! There are no places near the location you've chosen.";
                    textView.setText(text);
                }
            }
        });
    }

    public void subscribeClick(View v)
    {
        Toast t;
        TextView textView = findViewById(R.id.subscribe);
        int len = SubscribedList.subcribedList.size();
        if(place != null && SubscribedList.subcribedList.contains(place)){
            sub = true;
            textView.setText("Subscribe");
            t = Toast.makeText(this, "Unsubscribed", Toast.LENGTH_SHORT);
            List<Pair<PlaceInformation,Double>> tempList = new ArrayList<>();
            for(int i = 0; i < len; i++){
                PlaceInformation location = SubscribedList.subcribedList.get(i);
                double distance_i = SubscribedList.distanceList.get(i);
                if(!place.equals(location)){
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
        }
        else if(place != null && !SubscribedList.subcribedList.contains(place)){
            sub = false;
            textView.setText("Unsubscribe");
            t = Toast.makeText(this, "Subscribed", Toast.LENGTH_SHORT);
            SubscribedList.subcribedList.add(place);
            SubscribedList.distanceList.add(distance);
        }
        else{
            t = Toast.makeText(this, "None", Toast.LENGTH_SHORT);
        }
        t.show();
    }
}
