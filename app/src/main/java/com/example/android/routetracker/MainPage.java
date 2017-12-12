package com.example.android.routetracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class MainPage extends MainActivity implements LocationListener {



    Button getLocationBtn;
    TextView locationText, timeText;
    LocationManager locationManager;
    Context c = com.example.android.routetracker.MainPage.this;
    private static final String TAG = "MainPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        getLocationBtn = (Button) findViewById(R.id.getLocationBtn);
        locationText = (TextView) findViewById(R.id.locationText);
        timeText = (TextView) findViewById(R.id.timeText);

        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

    } //onCreate end

    //Called when the get Location button is pressed
    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this,  null);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        //Code for getting current Time
        Date time = new Date();
        DateFormat format = DateFormat.getTimeInstance();
        String currentTime = format.format(time);

        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
        Log.i(TAG, "Got values of loc/time");
        locationText.setText("Location: (Latitude, Longitude) \n " + latitude + " , " + longitude);
        timeText.setText("Time: " + currentTime);
        Log.i(TAG, "Text outputted on screen");


        //Calling the CSV class to update data to CSV file
        CSV obj = new CSV(latitude, longitude, currentTime, routeName, c);
        try {
            obj.makeCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

    } //onLocationChanged end

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(com.example.android.routetracker.MainPage.this, "Please enable GPS", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
} //MainPage class end
