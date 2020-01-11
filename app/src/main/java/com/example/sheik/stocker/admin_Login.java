package com.example.sheik.stocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class admin_Login extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    Button b1, b2;
    adminSQLiteHelper db;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);

        db = new adminSQLiteHelper(this);

        if(CheckIfFileExits("/shared_prefs/admin_login.xml"))
        {
            OnButtonClicked();
        }
        flag = true;
        if(CheckIfFileExits("/databases/admins.db"))
        {
            b1 = findViewById(R.id.Login);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkInfo(v))
                    {
                        SharedPreferences shref = getSharedPreferences("admin_login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shref.edit();
                        editor.putString("username", usernameInput.getText().toString());
                        editor.putString("password", passwordInput.getText().toString());
                        editor.apply();
                        usernameInput.setText("");
                        passwordInput.setText("");
                        OnButtonClicked();
                    }
                }
            });
            b2 = (Button) findViewById(R.id.Register);
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToSignup(v);
                }
            });
        }
        else
        {
            goToSignup(null);
        }
    }
    public void goToSignup(View v)
    {
        Intent i = new Intent(this, admin_Signup.class);
        startActivity(i);
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

    public boolean checkInfo(View view)
    {
        Cursor res = db.CheckSpecificData("admininfo", usernameInput.getText().toString(),
                passwordInput.getText().toString());
        if(res.getCount() <= 0)
        {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            return false;
        }

        Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show();
        return true;
    }

    public void OnButtonClicked()
    {
        Intent i = new Intent (this,  admin_Home.class);
        if(flag == true)
        {
            i.putExtra("username", usernameInput.getText().toString());
        }
        else
        {
            SharedPreferences shref = getSharedPreferences("admin_login", Context.MODE_PRIVATE);
            i.putExtra("username", shref.getString("username", ""));
        }

        startActivity(i);
    }
}
