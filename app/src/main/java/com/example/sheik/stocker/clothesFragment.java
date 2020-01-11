package com.example.sheik.stocker;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
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


public class clothesFragment extends Fragment {

    public clothesFragment() {
        // Required empty public constructor
    }

    String record;
    RecyclerView recyclerView;
    customAdaptor adaptor;
    List<brand> brandList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_clothes, container, false);

        SharedPreferences sharedPref = this.getActivity().
                getSharedPreferences("sendToFrag", Context.MODE_PRIVATE);
        record = sharedPref.getString("brand_type","");
        String output = record.substring(0, 1).toUpperCase() + record.substring(1);
        output += " Clothing Brands";

        TextView textView = (TextView) rootView.findViewById(R.id.clothes_cat);
        textView.setText(output);

        brandList = new ArrayList<>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.brands_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        getData();

        adaptor = new customAdaptor(rootView.getContext(), brandList, this);
        recyclerView.setAdapter(adaptor);


        return rootView;
    }

    public void getData()
    {
        adminSQLiteHelper db;
        db = new adminSQLiteHelper(this.getActivity());
        Cursor c = db.getAllCompanys("clothes");
        String [] Clothes_Brand_List = new String[c.getCount()+2];
        ArrayList<Integer> myImageList = new ArrayList<>();
        if(record.equals("female"))
        {
            Clothes_Brand_List[0] = "Khaadi";
            Clothes_Brand_List[1] = "Lime Light";
            myImageList.add(R.drawable.khadi_logo);
            myImageList.add(R.drawable.limelight_logo);
        }
        else
        {
            Clothes_Brand_List[0] = "Outfitters";
            Clothes_Brand_List[0] = "StoneAge";
            myImageList.add(R.drawable.outfitters_logo);
            myImageList.add(R.drawable.stoneage_logo);
        }
        int i=2;
        while (c.moveToNext())
        {
            Clothes_Brand_List[i] = c.getString(c.getColumnIndex("company"));
            System.out.println(Clothes_Brand_List[i]);
            myImageList.add(R.drawable.dummy_product);
            i++;
        }
        for(int j=0; j<Clothes_Brand_List.length; j++) {
            brandList.add(
                    new brand(
                            j+1, Clothes_Brand_List[j], myImageList.get(j)
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
