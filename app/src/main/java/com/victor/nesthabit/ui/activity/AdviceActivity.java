package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.utils.ActivityManager;

public class AdviceActivity extends BaseActivity {

    private android.widget.ImageView back;
    private android.widget.EditText contact;
    private android.widget.Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Activity getActivityToPush() {
        return AdviceActivity.this;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_advice;
    }

    @Override
    protected void initView() {
        this.submit = (Button) findViewById(R.id.submit);
        this.contact = (EditText) findViewById(R.id.contact);
        this.back = (ImageView) findViewById(R.id.back);
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(AdviceActivity.this);
            }
        });

    }
}
