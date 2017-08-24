package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.util.ActivityManager;

public class AdviceActivity extends BaseActivity {

    private android.widget.ImageView back;
    private android.widget.EditText contact;
    private android.widget.Button submit;

    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected Activity getActivity() {
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
