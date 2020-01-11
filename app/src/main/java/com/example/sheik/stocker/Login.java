package com.example.sheik.stocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class Login extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    Button b1, b2;
    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new SQLiteHelper(this);

        if(CheckIfFileExits("/shared_prefs/user_login.xml"))
        {
            OnButtonClicked();
        }

        if(CheckIfFileExits("/databases/users.db"))
        {
            usernameInput = (EditText) findViewById(R.id.username);
            passwordInput = (EditText) findViewById(R.id.password);
            b1 = findViewById(R.id.Login);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkInfo(v))
                    {
                        SharedPreferences shref = getSharedPreferences("user_login", Context.MODE_PRIVATE);
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
    public void goToSignup(View view)
    {
        Intent i = new Intent(this, Signup.class);
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
        Cursor res = db.CheckSpecificData(usernameInput.getText().toString(),
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
        Intent i = new Intent (this,  Home.class);
        startActivity(i);
    }
}
