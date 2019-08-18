package com.example.sahilgarg.geofencingapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PhoneActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<Phone> phoneList;
    private PhoneAdapter mPhoneAdapter;
    private String phone;
    private ProgressDialog mProgressDialog;
    private DatabaseReference dref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        mListView = (ListView) findViewById(R.id.phone_lv);
        phoneList = new ArrayList<Phone>();
        mPhoneAdapter = new PhoneAdapter(PhoneActivity.this, phoneList);
        mListView.setAdapter(mPhoneAdapter);

        mProgressDialog = new ProgressDialog(PhoneActivity.this);
        mProgressDialog.setCancelable(false);//you can not cancel it by pressing back button
        mProgressDialog.setMessage("Loading phone numbers ...");
        mProgressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!checkInternet() && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

            }
        }, 1000);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(PhoneActivity.this, android.R.style.Theme_Material_Light_Dialog_NoActionBar);
                } else {
                    builder = new AlertDialog.Builder(PhoneActivity.this);
                }
                builder.setTitle("Place Call")
                        .setMessage("Are you sure you wish to call this number? Extra carrier charges might apply.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Phone curUser = (Phone) mPhoneAdapter.getItem(pos);
                                phone = curUser.getPhone();
                                if (phone != null && !TextUtils.isEmpty(phone)) {
                                    if (ContextCompat.checkSelfPermission(PhoneActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 88);
                                    } else {
                                        phone = phone.trim();
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:" + phone));
                                        startActivity(callIntent);
                                    }
                                } else {
                                    Toast.makeText(PhoneActivity.this, "Unable to place call. Try again later.", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.sym_call_outgoing)
                        .show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        dref= FirebaseDatabase.getInstance().getReference();
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                phoneList.add(dataSnapshot.getValue(Phone.class));
                mPhoneAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                phoneList.remove(dataSnapshot.getValue(Phone.class));
                mPhoneAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 88) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                phone = phone.trim();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
            else
                Toast.makeText(PhoneActivity.this, "Call permission denied. Please give permission in app settings to place call.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkInternet(){
        ConnectivityManager connManager = (ConnectivityManager)PhoneActivity.this.getSystemService(PhoneActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (!wifi.isConnected() && !mobile.isConnected()) {
            Toast.makeText(PhoneActivity.this, "No internet connectivity.", Toast.LENGTH_SHORT).show();
            return false;
            // If Wi-Fi connected
        }
        return true;
    }
}
