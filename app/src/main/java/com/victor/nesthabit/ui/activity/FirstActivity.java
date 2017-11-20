package com.victor.nesthabit.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.Constants;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.PrefsUtils;

public class FirstActivity extends AppCompatActivity {
    private int second = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ActivityManager.getInstance().pushActivity(FirstActivity.this);
        String authorization = PrefsUtils.getValue(FirstActivity.this, Constants.AUTHORIZATION, "empty");
        authorization = "empty";
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
//
//
    }
//
}
