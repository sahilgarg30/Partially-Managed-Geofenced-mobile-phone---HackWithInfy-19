package com.example.sahilgarg.geofencingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QrActivity extends AppCompatActivity {

        private TextView textViewName, textViewAddress;

        //qr code scanner object
        private IntentIntegrator qrScan;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_qr);

            //View objects
            textViewName = (TextView) findViewById(R.id.textViewName);
            textViewAddress = (TextView) findViewById(R.id.textViewAddress);

            //intializing scan object
            qrScan = new IntentIntegrator(this);
        }

        //Getting the scan results
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                //if qrcode has nothing in it
                if (result.getContents() == null) {
                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    //if qr contains data
                    try {
                        //converting the data to json
                        JSONObject obj = new JSONObject(result.getContents());
                        //setting values to textviews
                        textViewName.setText(obj.getString("name"));
                        textViewAddress.setText(obj.getString("address"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //if control comes here
                        //that means the encoded format not matches
                        //in this case you can display whatever data is available on the qrcode
                        //to a toast
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }


        public void clickPhoto(View view) {
            qrScan.initiateScan();
        }
}
