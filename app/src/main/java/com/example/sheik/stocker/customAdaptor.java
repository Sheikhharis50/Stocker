package com.example.sheik.stocker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class customAdaptor extends RecyclerView.Adapter<customAdaptor.customViewHolder> {

    private Context mCtx;
    private Fragment f;
    private List<brand> brandsList;

    public customAdaptor(Context mCtx, List<brand> brandsList, Fragment f) {
        this.mCtx = mCtx;
        this.brandsList = brandsList;
        this.f = f;
    }
    @Override
    public customViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater myInflater = LayoutInflater.from(mCtx);
        View customView = myInflater.inflate(R.layout.brand_card, null);
        return new customViewHolder(customView);
    }

    @Override
    public void onBindViewHolder(final customViewHolder customViewHolder, int i) {

        final brand Brand = brandsList.get(i);
        customViewHolder.imageView.setImageDrawable(mCtx.getResources().getDrawable(Brand.getImage()));
        customViewHolder.textView.setText(Brand.getTitle());
        customViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f instanceof foodFragment)
                {
                    changeFragment(new foodProducts(), "foodProducts", Brand.getTitle());
                }
                else
                {
                    changeFragment(new clothesProducts(), "clothesProducts", Brand.getTitle());
                }
            }
        });

    }

    private void changeFragment(Fragment someFragment,String s, String name) {
        Bundle arguments = new Bundle();
        arguments.putString( s , name);
        someFragment.setArguments(arguments);
        if(s.equals("foodProducts"))
        {
            f.getFragmentManager().beginTransaction().
                    replace(R.id.food_contain, someFragment,s).addToBackStack(null).commit();
        }
        else
        {
            f.getFragmentManager().beginTransaction().
                    replace(R.id.clothes_contain, someFragment,s).addToBackStack(null).commit();
        }
    }

    @Override
    public int getItemCount() {
        return brandsList.size();
    }

    class customViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        RelativeLayout parentLayout;

        public customViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.brand_pic);
            textView = (TextView) itemView.findViewById(R.id.brand_Title);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.rlayout);

        }
    }
}