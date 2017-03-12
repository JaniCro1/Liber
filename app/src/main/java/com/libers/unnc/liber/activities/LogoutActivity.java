package com.libers.unnc.liber.activities;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static com.libers.unnc.liber.app.MyApplication.getContext;

/**
 * Created by zengye on 3/7/17.
 */

public class LogoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AlertDialog.Builder(getContext()).setTitle("Logout").setMessage("Are you sure to logout?").setPositiveButton("Yes", null)
                .setNegativeButton("No", null).show();
    }
}
