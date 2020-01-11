package com.example.sheik.stocker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class clothesProducts extends Fragment {

    RecyclerView recyclerView;
    productsAdaptor adaptor;
    List<product> clothes_products;

    public clothesProducts() {
        // Required empty public constructor
    }
    String desired_string;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clothes_products, container, false);
        Bundle arguments = getArguments();
        desired_string = arguments.getString("clothesProducts");

        TextView textView = (TextView) view.findViewById(R.id.clothes_producthead);
        textView.setText(desired_string+" Products");

        clothes_products = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.clothes_product_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        getData();

        adaptor = new productsAdaptor(view.getContext(), clothes_products, this);
        recyclerView.setAdapter(adaptor);

        return view;
    }

    public void getData() {
        ArrayList<Integer> myImageList = new ArrayList<>();
        myImageList.add(R.drawable.dummy_product);
        clothes_products.add(new product(1, "Fancy suit","Khaadi",24,"8000","with complete Set", R.drawable.dummy_product));
        clothes_products.add(new product(2, "Casual","Khaadi",20,"3000","with dupata only", R.drawable.dummy_product));
        clothes_products.add(new product(3, "Summer Collection 2018","Khaadi",28,"4000","with trouser and dupata", R.drawable.dummy_product));
    }
}