package com.victor.nesthabit.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.victor.nesthabit.R;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.utils.PrefsUtils;

public class FirstActivity extends AppCompatActivity {
    private int second = 1000;
    private String authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ActivityManager.getInstance().pushActivity(FirstActivity.this);
        authorization = PrefsUtils.getValue(FirstActivity.this, GlobalData
                .AUTHORIZATION, "empty");
        if (authorization.equals("empty")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(FirstActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    ActivityManager.getInstance().popActivity(FirstActivity.this);
                }
            }, second);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(FirstActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    ActivityManager.getInstance().popActivity(FirstActivity.this);
                }
            }, second);
        }


    }

}
