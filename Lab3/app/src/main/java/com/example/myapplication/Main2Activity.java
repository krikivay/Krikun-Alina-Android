package com.example.myapplication;

import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class Main2Activity extends Activity {


    private LocationManager locationManager;
    Button btn_location;
    TextView tv_location;
    private LocationListener listener;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ArrayList<Integer> data = getIntent().getExtras().getIntegerArrayList("date");

        Date today = new Date();
        Date date = new Date(data.get(0), data.get(1), data.get(2));
        Period period = Period.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        TextView txt = (TextView) findViewById(R.id.tvAnswer);
        if (period.getYears() + 1900 >= 0 && period.getYears() + 1900 < 18)
            txt.setText("You are minor");
        if (period.getYears() + 1900 >= 18 && period.getYears() < 28) txt.setText("You are young");
        if (period.getYears() + 1900 >= 28) txt.setText("You are old");


            tv_location = findViewById(R.id.tv_location);
            btn_location = findViewById(R.id.btn_location);

            Thread t = new Thread(new Runnable() {
                public void run() {
                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    listener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Message msg = handler.obtainMessage();
                            Bundle bundle = new Bundle();
                            bundle.putString("Longitude", "" + location.getLongitude());
                            bundle.putString("Latitude", "" + location.getLatitude());
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {}

                        @Override
                        public void onProviderEnabled(String s) {}

                        @Override
                        public void onProviderDisabled(String s) {
                            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(i);
                        }
                    };

                    btn_location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getCoordinates();
                        }
                    });
                }
            });
            t.start();
        }

        public void getCoordinates() {
            if (ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}
                            , 10);
                    getCoordinates();
                    return;
                }
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 5, 0, listener);
        }

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String longitude = bundle.getString("Longitude");
                String latitude = bundle.getString("Latitude");

                tv_location.setText("Coordinates:\n" + longitude + "\n" + latitude);
            }
        };
    }

