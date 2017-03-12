package com.libers.unnc.liber.activities;

/**
 * Created by zengye on 3/2/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;


import org.litepal.crud.DataSupport;

import java.util.List;

import com.libers.unnc.liber.R;
import com.libers.unnc.liber.model.bean.State;
import com.libers.unnc.liber.model.bean.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button btnlogin = (Button) findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText username = (EditText)findViewById(R.id.input_email);
                EditText password = (EditText)findViewById(R.id.input_password);
                String name = username.getText().toString();
                String passW = password.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username Empty!!", Toast.LENGTH_SHORT).show();
                }
                List<User> users = DataSupport.where("username = ?", name).find(User.class);
                if(users.size()== 0){
                    Toast.makeText(LoginActivity.this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
                }else{
                    String rigntPW = users.get(0).getPassword();
                    if(passW.equals(rigntPW)){
                        Toast.makeText(LoginActivity.this, "Login successfull!!", Toast.LENGTH_SHORT).show();
                        State NewState = new State();
                        NewState.setState(1);
                        NewState.setUsername(name);
                        NewState.save();

                        finish();
                    }

                }


            }
        });

    }


}
