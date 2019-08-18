package com.example.sahilgarg.geofencingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by sahilgarg on 18/08/19.
 */
public class PhoneAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Phone> phoneList;

    public PhoneAdapter(Context context, ArrayList<Phone> phoneList) {
        this.context = context;
        this.phoneList = phoneList;
    }

    @Override
    public int getCount() {
        return phoneList.size();
    }

    @Override
    public Object getItem(int i) {
        return phoneList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.listitem_phone, parent, false);
        }
        TextView mName = (TextView) convertView.findViewById(R.id.name_tv);
        TextView mPhone = (TextView) convertView.findViewById(R.id.phone_tv);

        mName.setText(phoneList.get(position).getName());
        mPhone.setText(phoneList.get(position).getPhone());
        return convertView;
    }

    public void setAllPhones(ArrayList<Phone> phoneList) {
        this.phoneList = phoneList;
    }
}