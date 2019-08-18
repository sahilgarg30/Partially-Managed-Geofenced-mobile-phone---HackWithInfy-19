package com.example.sahilgarg.geofencingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sahilgarg on 18/08/19.
 */

public class OnScreenOffReceiver extends BroadcastReceiver {
    private static final String PREF_KIOSK_MODE = "pref_kiosk_mode";
    private DatabaseReference dref;
    boolean ret;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
            AppContext ctx = (AppContext) context.getApplicationContext();
            // is Kiosk Mode active?
            if(isKioskModeActive(ctx)) {
                wakeUpDevice(ctx);
            }
        }
    }

    private void wakeUpDevice(AppContext context) {
        PowerManager.WakeLock wakeLock = context.getWakeLock(); // get WakeLock reference via AppContext
        if (wakeLock.isHeld()) {
            wakeLock.release(); // release old wake lock
        }

        // create a new wake lock...
        wakeLock.acquire();

        // ... and release again
        wakeLock.release();
    }

    private boolean isKioskModeActive(final Context context) {
        dref= FirebaseDatabase.getInstance().getReference().child("blockApp");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue(Integer.class)== 0)
                    ret = false;
                else
                    ret = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ret = false;
            }
        });
        return ret;
    }
}