package com.example.sahilgarg.geofencingapp;

import android.*;
import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

    public class GeoActivity extends AppCompatActivity implements
            GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener{

        protected ArrayList<Geofence> mGeofenceList;
        protected GoogleApiClient mGoogleApiClient;
        private Button mAddGeofencesButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_geo);

            mAddGeofencesButton = (Button) findViewById(R.id.add_geofences_button);
            // Empty list for storing geofences.
            mGeofenceList = new ArrayList<Geofence>();

            // Get the geofences used. Geofence data is hard coded in this sample.
            populateGeofenceList();

            // Kick off the request to build GoogleApiClient.
            buildGoogleApiClient();

        }

        protected synchronized void buildGoogleApiClient() {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        public void populateGeofenceList() {
            for (Map.Entry<String, LatLng> entry : Constants.LANDMARKS.entrySet()) {
                mGeofenceList.add(new Geofence.Builder()
                        .setRequestId(entry.getKey())
                        .setCircularRegion(
                                entry.getValue().latitude,
                                entry.getValue().longitude,
                                Constants.GEOFENCE_RADIUS_IN_METERS
                        )
                        .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                Geofence.GEOFENCE_TRANSITION_EXIT)
                        .build());
            }
        }

        @Override
        protected void onStart() {
            super.onStart();
            if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }
        }

        @Override
        protected void onStop() {
            super.onStop();
            if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }

        @Override
        public void onConnected(Bundle connectionHint) {

        }

        @Override
        public void onConnectionFailed(ConnectionResult result) {
            // Do something with result.getErrorCode());
        }

        @Override
        public void onConnectionSuspended(int cause) {
            mGoogleApiClient.connect();
        }

        public void addGeofencesButtonHandler(View view) {
            if (!mGoogleApiClient.isConnected()) {
                Toast.makeText(this, "Google API Client not connected!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                LocationServices.GeofencingApi.addGeofences(
                        mGoogleApiClient,
                        getGeofencingRequest(),
                        getGeofencePendingIntent()
                ).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Toast.makeText(
                                    GeoActivity.this,
                                    "Geofences Added",
                                    Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            // Get the status code for the error and log it using a user-friendly message.
                            System.out.println("Error");
                            Toast.makeText(GeoActivity.this, "hello"+status, Toast.LENGTH_SHORT).show();
                        }
                    }
                }); // Result processed in onResult().

            } catch (SecurityException securityException) {
                // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
                if (ContextCompat.checkSelfPermission(GeoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 89);
                } else {
                    LocationServices.GeofencingApi.addGeofences(
                            mGoogleApiClient,
                            getGeofencingRequest(),
                            getGeofencePendingIntent()
                    ).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Toast.makeText(
                                        GeoActivity.this,
                                        "Geofences Added",
                                        Toast.LENGTH_SHORT
                                ).show();
                            } else {
                                // Get the status code for the error and log it using a user-friendly message.
                                System.out.println("Error");
                            }
                        }
                    });
                }
                Toast.makeText(this, "Security"+securityException, Toast.LENGTH_SHORT).show();
            }
        }

        private GeofencingRequest getGeofencingRequest() {
            GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
            builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
            builder.addGeofences(mGeofenceList);
            return builder.build();
        }

        private PendingIntent getGeofencePendingIntent() {
            Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
            // We use FLAG_UPDATE_CURRENT so that we get the
            //same pending intent back when calling addgeoFences()
            return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }


        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 89) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    LocationServices.GeofencingApi.addGeofences(
                            mGoogleApiClient,
                            getGeofencingRequest(),
                            getGeofencePendingIntent()
                    ).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Toast.makeText(
                                        GeoActivity.this,
                                        "Geofences Added",
                                        Toast.LENGTH_SHORT
                                ).show();
                            } else {
                                // Get the status code for the error and log it using a user-friendly message.
                                System.out.println("Error");
                            }
                        }
                    });

                }
                else
                    Toast.makeText(GeoActivity.this, "Location permission denied. Please give permission in app settings.", Toast.LENGTH_LONG).show();
            }
        }

    }
