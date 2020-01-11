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


public class foodProducts extends Fragment {

    RecyclerView recyclerView;
    productsAdaptor adaptor;
    List<product> food_products;

    public foodProducts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_foodproducts, container, false);

        Bundle arguments = getArguments();
        String desired_string = arguments.getString("foodProducts");

        TextView textView = (TextView) view.findViewById(R.id.food_producthead);
        textView.setText(desired_string+" Products");

        food_products = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.food_product_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        getData();

        adaptor = new productsAdaptor(view.getContext(), food_products, this);
        recyclerView.setAdapter(adaptor);

        return view;
    }

    public void getData() {
        ArrayList<Integer> myImageList = new ArrayList<>();
        myImageList.add(R.drawable.dummy_product);
        food_products.add(new product(1, "Zinger Burger","Mc donalds",24,"500","with fries and coke", R.drawable.dummy_product));
        food_products.add(new product(2, "Spicy Mc Crisp","Mc donalds",20,"600","with fries and coke", R.drawable.dummy_product));
        food_products.add(new product(3, "Waffle Cone","Mc donalds",28,"150","with chocolate or vanilla", R.drawable.dummy_product));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}