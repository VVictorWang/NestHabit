package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.view.SwitchButton;

public class AddNestActivity extends BaseActivity {

    private android.widget.ImageView back;
    private android.widget.EditText name;
    private android.widget.RelativeLayout namelayout;
    private android.widget.EditText introduction;
    private android.widget.EditText day;
    private android.widget.RelativeLayout beginlayout;
    private android.support.v7.widget.CardView layouttwo;
    private com.victor.nesthabit.view.SwitchButton limittoggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Activity getActivityToPush() {
        return AddNestActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_nest;
    }

    @Override
    protected void initView() {
        this.limittoggle = (SwitchButton) findViewById(R.id.limit_toggle);
        this.layouttwo = (CardView) findViewById(R.id.layout_two);
        this.beginlayout = (RelativeLayout) findViewById(R.id.begin_layout);
        this.day = (EditText) findViewById(R.id.day);
        this.introduction = (EditText) findViewById(R.id.introduction);
        this.namelayout = (RelativeLayout) findViewById(R.id.name_layout);
        this.name = (EditText) findViewById(R.id.name);
        this.back = (ImageView) findViewById(R.id.back);
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(AddNestActivity.this);
            }
        });

    }
}
