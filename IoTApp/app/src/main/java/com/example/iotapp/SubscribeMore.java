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
        new PlaceInformation("Mê Linh - Hà Nội", 21.184412, 105.702209, 31.34, 84.44, 3.29, 10.78,85.4, 20,5,7,"TỐT" ),
        new PlaceInformation("Hoài Đức - Hà Nội", 21.028115, 105.695343, 32.78, 85.18, 3.61, 11.23,85.7,5,7,8,"KÉM" ),
        new PlaceInformation("Chương Mỹ - Hà Nội", 20.887683, 105.658264, 33.17, 84.57, 4.14,10.65,85.6, 15,5,9,"TỐT" ),
        new PlaceInformation("Phúc Yên - Vĩnh Phúc", 21.351421, 105.739288, 33.28, 85.42, 2.31, 10.78,85.4, 20,5,5,"KÉM" ),
        new PlaceInformation("Ý Yên - Nam Định", 20.332394, 106.010513, 32.59, 79.34,2.78,11.23,85.7,5,7, 6, "KÉM" ),
        new PlaceInformation("Xuân Trường - Nam Định", 20.308569, 106.357956, 32.97, 79.51, 2.67, 10.65,85.6, 15,7,6, "KÉM" ),
        new PlaceInformation("Lạng Giang - Bắc Giang", 21.377639, 106.247406, 32.32, 84.84, 2.85,10.78,85.4, 20,5, 6,"KÉM" ),
        new PlaceInformation("Từ Sơn - Bắc Ninh", 21.128067, 105.956268, 32.84, 84.27, 3.52, 10.65,85.6, 15,7,8,"TỐT" ),
        new PlaceInformation("Sóc Sơn - Hà Nội", 21.275298, 105.821686, 32.63, 84.28, 3.31,10.78,85.4, 20,6, 7,"KÉM" ),
        new PlaceInformation("Hiệp Hòa - Bắc Giang", 21.335432, 105.960388, 32.89, 84.26, 3.17,10.78,85.4, 20,5, 7,"TỐT" ),
        new PlaceInformation("Lập Thạch - Vĩnh Phúc", 21.433895, 105.437164, 32.67, 84.05, 2.36, 10.65,85.6, 15,7,5,"KÉM" ),
        new PlaceInformation("Vĩnh Bảo - Hải Phòng", 20.684184, 106.476059, 33.75, 71.38, 3.14,10.78,85.4, 20,6, 7,"TỐT" ),
    };

    List<LatLng> latLngList = new ArrayList<>();


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
                if(searchView.getQuery() == null) return false;
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
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

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
                if(minDistance < 25000) {
                    String text = "Địa điểm gần nhất là";
                    textView.setText(text);
                    text = places[iDistance].getName();
                    tv.setText(text);
                    TextView textView1 = findViewById(R.id.subscribe);
                    place = places[iDistance];
                    distance = (double)minDistance/1000.0;
                    if (!SubscribedList.subcribedList.contains(place)) {
                        textView1.setText("Đăng ký");
                    } else {
                        textView1.setText("Hủy đăng ký");
                    }
                }
                else {
                    String text = "Không có địa điểm nào gần hơn 20km";
                    textView.setText(text);
                    tv.setText("");
                    place = null;
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
            mMap.addMarker(new MarkerOptions().position(latLng).title(places[i].getName()).icon(BitmapDescriptorFactory.fromBitmap(iconMarker(places[i].getEvaluation()))));
        }
        // Add a marker in Sydney and move the camera
        LatLng center = new LatLng(20.997823, 105.841030);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 9));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onMapClick(LatLng latLng) {
                //mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

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
                if(minDistance < 25000) {
                    String text = "Địa điểm gần nhất là ";
                    textView.setText(text);
                    text = places[iDistance].getName();
                    tv.setText(text);
                    TextView textView1 = findViewById(R.id.subscribe);
                    place = places[iDistance];
                    distance = (double)minDistance/1000.0;
                    if (!SubscribedList.subcribedList.contains(place)) {
                        textView1.setText("Đăng ký");
                    } else {
                        textView1.setText("Hủy đăng ký");
                    }
                }
                else {
                    String text = "Không có địa điểm nào gần hơn 20km!";
                    textView.setText(text);
                    tv.setText("");
                    place = null;
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
            textView.setText("Đăng ký");
            t = Toast.makeText(this, "Đã hủy đăng ký", Toast.LENGTH_SHORT);
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
            textView.setText("Hủy đăng ký");
            t = Toast.makeText(this, "Đã đăng ký", Toast.LENGTH_SHORT);
            SubscribedList.subcribedList.add(place);
            SubscribedList.distanceList.add(distance);
        }
        else{
            t = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        t.show();
    }
}
