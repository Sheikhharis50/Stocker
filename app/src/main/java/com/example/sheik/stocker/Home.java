package com.example.sheik.stocker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.PopupMenu;
import android.widget.ImageView;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class Home extends AppCompatActivity {

    SearchView sv;
    ImageView cloths, appliance, department, food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Search Bar

        sv = (SearchView)findViewById(R.id.search_bar);
        sv.onActionViewExpanded();
        sv.clearFocus();
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.setIconified(false);
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sv.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Menu

        ActionMenuView menu = (ActionMenuView) findViewById(R.id.MenuView);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, "main");
            }
        });

        // Categories

        // 1. Clothes

        cloths = (ImageView) findViewById(R.id.Clothes);
        cloths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, "clothes");
            }
        });

        // 2. Food

        food = (ImageView) findViewById(R.id.Food);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, foodContainer.class);
                startActivity(i);
            }
        });

        // 3. Departmental

        department = (ImageView) findViewById(R.id.Departmental);
        department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 4. Appliance

        appliance = (ImageView) findViewById(R.id.Appliance);
        appliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    public void showPopup(View view, String str)
    {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        if(str.equals("main"))
        {
            inflater.inflate(R.menu.menu_view, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.settings) {
                        Toast.makeText(Home.this, "Setting Page", Toast.LENGTH_SHORT).show();

                    }
                    else if (menuItem.getItemId() == R.id.about) {
                        Toast.makeText(Home.this, "About Page", Toast.LENGTH_SHORT).show();
                    }
                    else if (menuItem.getItemId() == R.id.Logout) {
                        if (CheckIfFileExits("/shared_prefs/user_login.xml"))
                        {
                            delFile("/shared_prefs/user_login.xml");
                        }
                        Home.this.finish();
                    }
                    return true;
                }
            });
        }
        else if(str.equals("clothes"))
        {
            inflater.inflate(R.menu.clothes_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    String s="none";
                    if (menuItem.getItemId() == R.id.Female_menu) {
                        Toast.makeText(Home.this, "Female Selected", Toast.LENGTH_SHORT).show();
                        s = "female";
                    }
                    else if (menuItem.getItemId() == R.id.male_menu) {
                        Toast.makeText(Home.this, "Male Selected", Toast.LENGTH_SHORT).show();
                        s = "male";
                    }
                    Intent i = new Intent(Home.this, clothesContainer.class);
                    i.putExtra("record", s);
                    startActivity(i);
                    return true;
                }
            });
        }


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
