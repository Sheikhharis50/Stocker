package com.example.sheik.stocker;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class productsAdaptor extends RecyclerView.Adapter<productsAdaptor.productViewHolder>{

    private Context mCtx;
    private Fragment f;
    private List<product> productList;

    public productsAdaptor(Context mCtx, List<product> productsList, Fragment f) {
        this.mCtx = mCtx;
        this.f = f;
        this.productList = productsList;
    }

    @Override
    public productViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater myInflater = LayoutInflater.from(mCtx);
        View customView = myInflater.inflate(R.layout.product_card, viewGroup,false);
        return new productViewHolder(customView);
    }

    @Override
    public void onBindViewHolder(final productViewHolder productViewHolder, int i) {
       final product Pro = productList.get(i);
        productViewHolder.imageView.setImageDrawable(mCtx.getResources().getDrawable(Pro.getImage()));
        productViewHolder.title.setText(Pro.getTitle());
        productViewHolder.pCode.setText("Product Code: 00"+Integer.toString(Pro.getpCode()));
        productViewHolder.price.setText("PKR " + Pro.getPrice());
        productViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(Pro);
            }
        });
    }

    public void showAlert(product pro) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        View view = null;
        view = LayoutInflater.from(mCtx).inflate(R.layout.view_productinfo, null, false);
        builder.setCancelable(true);
        builder.setView(view);

        TextView pCode = (TextView) view.findViewById(R.id.PCode);
        TextView pTitle = (TextView) view.findViewById(R.id.PTitle);
        TextView pCompany = (TextView) view.findViewById(R.id.Pcompany);
        TextView pPrice = (TextView) view.findViewById(R.id.Pprice);
        ImageView pImage = (ImageView) view.findViewById(R.id.product_img);
        TextView pStock = (TextView) view.findViewById(R.id.Pstock);
        TextView pDiscription = (TextView) view.findViewById(R.id.Pdiscript);
        final AlertDialog alertDialog = builder.create();

        pCode.setText("Product Code: 00"+Integer.toString(pro.getpCode()));
        pTitle.setText("Title: "+pro.getTitle());
        pCompany.setText("Company: "+pro.getCompany());
        pPrice.setText("Price: "+pro.getPrice());
        pImage.setImageDrawable(mCtx.getResources().getDrawable(pro.getImage()));
        pStock.setText("Quantity: "+Integer.toString(pro.getStock()));
        pDiscription.setText("Description: \n"+pro.getDescription());
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class productViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView pCode;
        TextView title;
        TextView price;
        RelativeLayout parentLayout;

        public productViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.product_pic);
            pCode = (TextView) itemView.findViewById(R.id.textViewPCode);
            title = (TextView) itemView.findViewById(R.id.textViewTitle);
            price = (TextView) itemView.findViewById(R.id.textViewPrice);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.pro_layout);

        }
    }
}
