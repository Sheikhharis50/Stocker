package com.example.sheik.stocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;

public class Signup extends AppCompatActivity {

    EditText usernameInput, passwordInput, email;
    RadioGroup Rgroup;
    RadioButton Rbutton;
    Button b;
    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = new SQLiteHelper(this);
        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        Rgroup = (RadioGroup) findViewById(R.id.Gender);
        b = findViewById(R.id.SignUp);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInfo(v))
                {
                    saveinfo(v);
                    OnButtonClicked();
                }
                usernameInput.setText("");
                passwordInput.setText("");
                email.setText("");
                Rgroup.clearCheck();
            }
        });
    }
    public void saveinfo(View v){

        String [] row = new String[4];

        int id = Rgroup.getCheckedRadioButtonId();
        Rbutton = (RadioButton) findViewById(id);

        row[0] = usernameInput.getText().toString();
        row[1] = passwordInput.getText().toString();
        row[2] = email.getText().toString();
        row[3] = Rbutton.getText().toString();

        if(db.insertData(row))
            Toast.makeText(this, "Thanks! Login Now",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Something is Wrong!",Toast.LENGTH_SHORT).show();

        usernameInput.setText("");
        passwordInput.setText("");
        email.setText("");
    }
    public boolean checkInfo(View view)
    {

        if(usernameInput.getText().toString().length() == 0
                && passwordInput.getText().toString().length() == 0)
        {
            Toast.makeText(this, "Please Enter Username \n, Password and email",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
             Cursor c = db.CheckSpecificData(usernameInput.getText().toString());
            if(c.getCount() != 0)
            {
                Toast.makeText(this, "Already Exists", Toast.LENGTH_SHORT).show();
                return false;
            }
            else
            {
                Toast.makeText(this, "SignUp Successfull", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
    }
    public void OnButtonClicked()
    {
        this.finish();
    }
}
