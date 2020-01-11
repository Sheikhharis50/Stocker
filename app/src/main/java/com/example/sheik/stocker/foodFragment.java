package com.example.sheik.stocker;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class foodFragment extends Fragment {

    RecyclerView recyclerView;
    customAdaptor adaptor;
    List<brand> foodList;

    public foodFragment() {
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

        View rootView =  inflater.inflate(R.layout.fragment_food, container, false);
        String record;
        TextView textView = (TextView) rootView.findViewById(R.id.food_brandhead);
        record = "Food Brands";
        textView.setText(record);
        foodList = new ArrayList<>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.food_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        getData();

        adaptor = new customAdaptor(rootView.getContext(), foodList, this);
        recyclerView.setAdapter(adaptor);
        return rootView;
    }

    //  Fetching data from the database:

    public void getData()
    {

        adminSQLiteHelper db;
        db = new adminSQLiteHelper(this.getActivity());
        Cursor c = db.getAllCompanys("food");
        String [] Food_Brand_List = new String[c.getCount()+4];
        Food_Brand_List[0] = "Mc donalds";
        Food_Brand_List[1] = "Pizza Hut";
        Food_Brand_List[2] = "California Pizza";
        Food_Brand_List[3] = "Howdy";
        ArrayList<Integer> myImageList = new ArrayList<>();
        myImageList.add(R.drawable.mcdonalds_logo);
        myImageList.add(R.drawable.pizzahut_logo);
        myImageList.add(R.drawable.california_logo);
        myImageList.add(R.drawable.howdy_logo);
        int i=4;
        while (c.moveToNext())
        {
            Food_Brand_List[i] = c.getString(c.getColumnIndex("company"));
            System.out.println(Food_Brand_List[i]);
            myImageList.add(R.drawable.dummy_product);
            i++;
        }
        for(int j=0; j<Food_Brand_List.length; j++) {
            foodList.add(
                    new brand(
                            j+1, Food_Brand_List[j], myImageList.get(j)
                    )
            );
        }
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
