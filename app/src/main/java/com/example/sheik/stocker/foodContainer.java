package com.example.sheik.stocker;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class foodContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        showFragment();
    }
    public void showFragment()
    {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.food_contain, new foodFragment(), "foodFragment");
        trans.commit();
    }
}
