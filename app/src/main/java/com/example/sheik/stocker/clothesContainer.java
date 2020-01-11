package com.example.sheik.stocker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class clothesContainer extends AppCompatActivity {

    String record;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);
        Bundle Data = getIntent().getExtras();

        if(Data != null)
        {
            record = Data.getString("record");
        }
        else return;
        sendDataToFragment();
        showFragment();
    }
    public void MakeFiles(String s,String key, String data)
    {
        SharedPreferences sharedPref = getSharedPreferences(s, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, data);
        editor.apply();
    }
    public void sendDataToFragment()
    {
        MakeFiles("sendToFrag", "brand_type", record);
    }
    public void showFragment()
    {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.clothes_contain, new clothesFragment(), "clothesFragment");
        trans.commit();
    }
}
