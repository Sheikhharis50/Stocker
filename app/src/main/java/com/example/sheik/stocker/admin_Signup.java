package com.example.sheik.stocker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class admin_Signup extends AppCompatActivity {

    EditText usernameInput, passwordInput, email, cat, com, add, con;
    RadioGroup Rgroup;
    RadioButton Rbutton;
    Button b;
    adminSQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);
        db = new adminSQLiteHelper(this);
        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        Rgroup = (RadioGroup) findViewById(R.id.Gender);
        cat = (EditText) findViewById(R.id.category);
        com = (EditText) findViewById(R.id.company);
        add = (EditText) findViewById(R.id.address);
        con = (EditText) findViewById(R.id.contact);
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
                cat.setText("");
                com.setText("");
                add.setText("");
                con.setText("");
            }
        });
    }
    public void saveinfo(View v){

        String [] row = new String[8];
        String tb = "";
        int id = Rgroup.getCheckedRadioButtonId();
        Rbutton = (RadioButton) findViewById(id);

        row[0] = usernameInput.getText().toString();
        row[1] = passwordInput.getText().toString();
        row[2] = email.getText().toString();
        row[3] = cat.getText().toString();
        row[4] = com.getText().toString();
        row[5] = add.getText().toString();
        row[6] = con.getText().toString();
        row[7] = Rbutton.getText().toString();

        if(db.insertData("admininfo", row))
            Toast.makeText(this, "Thanks! Login Now",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Something is Wrong!",Toast.LENGTH_SHORT).show();

        usernameInput.setText("");
        passwordInput.setText("");
        email.setText("");
        cat.setText("");
        com.setText("");
        add.setText("");
        con.setText("");
        Rgroup.clearCheck();
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
            Cursor c = db.CheckSpecificData("admininfo",usernameInput.getText().toString());
            if(c.getCount() > 0)
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
