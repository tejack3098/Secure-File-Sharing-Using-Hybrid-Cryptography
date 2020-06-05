package com.example.proteinshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AccountDB db = new AccountDB(this);
    private EditText email,password;
    private TextView email_error,password_error;
    private Toast login_success,login_failed;
    private SharedPreferences saved;
    private SharedPreferences.Editor saved_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**********************************Shared Prefernces**********************************/
        saved = getApplicationContext().getSharedPreferences(getString(R.string.shared_pref),Context.MODE_PRIVATE);
        saved_edit = saved.edit();
        /**********************************Shared Prefernces**********************************/

        /**************************EditText******************************************************/
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        /**************************EditText******************************************************/
        /************************Login Button****************************************************/
        Button login_btn =  findViewById(R.id.btn_log_in);
        login_btn.setOnClickListener(this);
        /************************Login Button****************************************************/

        /**************************Error messages************************************************/
        email_error = findViewById(R.id.login_emailerror);
        password_error = findViewById(R.id.login_passworderror);
        /**************************Error messages************************************************/

        /****************************Toast Messages***********************************************/
        login_success = Toast.makeText(this,"Logged in successfully!",Toast.LENGTH_SHORT);
        login_failed = Toast.makeText(this,"Error ocurred. Try agaiin later!",Toast.LENGTH_LONG);
        /****************************Toast Messages***********************************************/

        /************************Create Account****************************************************/
        TextView create_account =  findViewById(R.id.create_account_link);
        create_account.setOnClickListener(this);
        /************************Create Account****************************************************/
    }


    @Override
    public void onClick(View view){
        switch (view.getId())
        {
            case R.id.create_account_link:
                startActivityForResult(new Intent(getApplication(),Register.class),0);
                break;
            case R.id.btn_log_in:
                email_error.setVisibility(View.GONE);
                password_error.setVisibility(View.GONE);
                int login = db.login(email.getText().toString(),password.getText().toString());
                if(login==1)
                {
                    saved_edit.putInt("logged_in",1);
                    ArrayList<String> details;
                    details = db.nameAddress(email.getText().toString());
                    String name = details.get(0);
                    String address = details.get(1);
                    saved_edit.putString("name",name);
                    saved_edit.putString("address",address);
                    saved_edit.apply();

                    login_success.setGravity(Gravity.CENTER_HORIZONTAL,0,-100);
                    login_success.show();

                    startActivity(new Intent(getApplication(),Home.class));
                    finish();
                }
                else if(login==-1)
                {
                    email_error.setText("Email ID not found!");
                    email_error.setVisibility(View.VISIBLE);
                }
                else if(login == 0)
                {
                    password_error.setText("Password not matched!");
                    password_error.setVisibility(View.VISIBLE);
                }
                else
                {
                    login_failed.setGravity(Gravity.CENTER_HORIZONTAL,0,-100);
                    login_failed.show();
                }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode==1)
        {
            finish();
        }
    }


}
