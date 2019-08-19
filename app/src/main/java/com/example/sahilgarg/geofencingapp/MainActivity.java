package com.example.sahilgarg.geofencingapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String batteryTxt;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryTxt=(String.valueOf(level) + "%");
            SendBatteryInfo();
        }

        private void SendBatteryInfo() {
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference().child("battery");
            databaseReference.child("98765445").setValue(batteryTxt);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        disablePullNotificationTouch();

        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_main);
    }

    public void startPhoneActivity(View view) {
        Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    public void startQrActivity(View view) {
        Intent intent = new Intent(MainActivity.this, QrActivity.class);
        startActivity(intent);
    }

    public void startGeoFence(View view) {
//        Intent intent = new Intent(MainActivity.this, GeoActivity.class);
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);

        startActivity(intent);
    }



}
