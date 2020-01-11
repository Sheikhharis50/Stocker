package com.example.sheik.stocker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class recordsAdaptor extends ArrayAdapter<String> {


    public recordsAdaptor(Context context, String [] fruits) {
        super(context, R.layout.records, fruits);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.records, parent, false);

        String singleFruitItem = getItem(position);
        TextView myText = (TextView) customView.findViewById(R.id.text1);

        myText.setText(singleFruitItem);

        return customView;
    }
}