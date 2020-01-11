package com.example.sheik.stocker;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class admin_Home extends AppCompatActivity {

    String tb;
    String companyname, admin;
    EditText title1, code, stock, price, description;
    Button btnAddData, btnViewData, btndeldata, btnupdatedata;
    adminSQLiteHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        // Menu

        ActionMenuView menu = (ActionMenuView) findViewById(R.id.adminMenuView);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });


        mydb = new adminSQLiteHelper(this);
        Bundle data = getIntent().getExtras();
        admin = data.getString("username");
        if(mydb.getCategoryAndCompany(admin) == null)
        {
            return;
        }
        String []a = mydb.getCategoryAndCompany(admin);
        companyname = a[1];
        tb = a[0];
        //System.out.println(a[0]);
        title1 = findViewById(R.id.name);
        code = findViewById(R.id.Code);
        stock = findViewById(R.id.stock);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);


        btnAddData = (Button) findViewById(R.id.btnadddata);
        btnViewData = (Button) findViewById(R.id.btnviewdata);
        btnupdatedata = (Button) findViewById(R.id.btnupdatedata);
        btndeldata = (Button) findViewById(R.id.btndeldata);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData();
                title1.setText("");
                code.setText("");
                stock.setText("");
                price.setText("");
                description.setText("");
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAll();
                title1.setText("");
                code.setText("");
                stock.setText("");
                price.setText("");
                description.setText("");
            }
        });

        btndeldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData();
                title1.setText("");
                code.setText("");
                stock.setText("");
                price.setText("");
                description.setText("");
            }
        });

        btnupdatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
                title1.setText("");
                code.setText("");
                stock.setText("");
                price.setText("");
                description.setText("");
            }
        });
    }

    public void AddData() {
        if(
                title1.getText().toString().trim().equals("") &&
                code.getText().toString().trim().equals("") &&
                stock.getText().toString().trim().equals("") &&
                price.getText().toString().trim().equals("") &&
                description.getText().toString().trim().equals("") )
        {
            Toast.makeText(admin_Home.this, "Insert Something first", Toast.LENGTH_LONG).show();
            return;
        }
        String[] row = {
                companyname,
                title1.getText().toString(),
                code.getText().toString(),
                stock.getText().toString(),
                price.getText().toString(),
                description.getText().toString(),
                admin,
                "male"
        };
        boolean isInserted = mydb.insertData(tb, row);
        if (isInserted == true) {
            Toast.makeText(admin_Home.this, "Data inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(admin_Home.this, "Data not inserted", Toast.LENGTH_LONG).show();
        }

    }


    public void ViewAll() {
        Cursor res = mydb.getAllData(tb, companyname);
        showAlert(res);
    }


    public void UpdateData() {
        if(
                title1.getText().toString().trim().equals("") &&
                        code.getText().toString().trim().equals("") &&
                        stock.getText().toString().trim().equals("") &&
                        price.getText().toString().trim().equals("") &&
                        description.getText().toString().trim().equals("") )
        {
            Toast.makeText(admin_Home.this, "Insert Something first", Toast.LENGTH_LONG).show();
            return;
        }
        String[] row = {
                companyname,
                title1.getText().toString(),
                code.getText().toString(),
                stock.getText().toString(),
                price.getText().toString(),
                description.getText().toString(),
                admin
        };
        boolean isUpdate = mydb.updateData(tb, row);
        if (isUpdate == true)
            Toast.makeText(admin_Home.this, "Data uodated", Toast.LENGTH_LONG).show();

        else
            Toast.makeText(admin_Home.this, "Data not updated", Toast.LENGTH_LONG).show();
    }


    public void DeleteData() {
        if(title1.getText().toString().trim().equals(""))
        {
            Toast.makeText(admin_Home.this, "Insert Something first", Toast.LENGTH_LONG).show();
            return;
        }
        boolean deleted = mydb.Deletedata(tb, admin, title1.getText().toString());
        if (deleted == true)
            Toast.makeText(admin_Home.this, "Data deleted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(admin_Home.this, "Data not deleted", Toast.LENGTH_LONG).show();


    }
    public void showAlert(Cursor res) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = null;
        view = LayoutInflater.from(this).inflate(R.layout.dialog_view_data, null, false);
        builder.setCancelable(true);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();


        if (res.getCount() == 0) {
            Toast.makeText(this, "Nothing is Entered yet!", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
        }
        String[] records = new String[res.getCount()];
        int i = 0;
        alertDialog.setTitle(companyname + " Products\n\n");
        while (res.moveToNext()) {
            records[i] = "Product :" + res.getString(res.getColumnIndex("title")) + "\n" +
                    "Code :" + res.getString(res.getColumnIndex("code")) + "\n" +
                    "Avaliability :" + res.getString(res.getColumnIndex("stock")) + "\n" +
                    "Price :" + res.getString(res.getColumnIndex("price")) + "\n" +
                    "Description :" + res.getString(res.getColumnIndex("description")) + "\n";
            i++;
        }
        ArrayAdapter myListAdapter = new recordsAdaptor(this, records);
        ListView myListView = (ListView) view.findViewById(R.id.records);
        myListView.setAdapter(myListAdapter);
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_view, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.settings) {
            Toast.makeText(this, "Setting Page", Toast.LENGTH_SHORT).show();

        }
        else if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "About Page", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.Logout) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_view, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.settings) {
                    Toast.makeText(admin_Home.this, "Setting Page", Toast.LENGTH_SHORT).show();

                } else if (menuItem.getItemId() == R.id.about) {
                    Toast.makeText(admin_Home.this, "About Page", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getItemId() == R.id.Logout) {
                    if (CheckIfFileExits("/shared_prefs/admin_login.xml")) {
                        delFile("/shared_prefs/admin_login.xml");
                    }
                    admin_Home.this.finish();
                }
                return true;
            }
        });

        popup.show();
    }
    public boolean CheckIfFileExits(String path)
    {
        File f = new File(getApplicationContext().getApplicationInfo().dataDir + path);
        if(f.exists())
        {
            return true;
        }
        return false;
    }
    public void delFile(String path)
    {
        File f = new File(getApplicationContext().getApplicationInfo().dataDir + path);
        f.delete();
    }
}