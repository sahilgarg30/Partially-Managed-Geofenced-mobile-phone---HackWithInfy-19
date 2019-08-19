package com.example.sahilgarg.geofencingapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by sahilgarg on 19/08/19.
 */

public class Constants {

    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 12 * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 10;

    public static final HashMap<String, LatLng> LANDMARKS = new HashMap<String, LatLng>();
    static {
        // San Francisco International Airport.
//        LANDMARKS.put("Infy", new LatLng(18.35515,73.42249));
        LANDMARKS.put("Infy", new LatLng(18.595102,73.708252));

        // Googleplex.
        LANDMARKS.put("Japantown", new LatLng(17.59764,73.72692));

        // Test
        LANDMARKS.put("SFO", new LatLng(18.7,73.71692));
    }
}