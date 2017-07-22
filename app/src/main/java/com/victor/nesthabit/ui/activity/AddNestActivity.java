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
import com.victor.nesthabit.ui.contract.AddNestContract;
import com.victor.nesthabit.ui.presenter.AddNestPresenter;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.view.SwitchButton;

public class AddNestActivity extends BaseActivity implements AddNestContract.View {

    private android.widget.ImageView back;
    private android.widget.EditText name;
    private android.widget.RelativeLayout namelayout;
    private TextView start_time;
    private android.widget.EditText introduction, amount;
    private android.widget.EditText day;
    private android.widget.RelativeLayout beginlayout;
    private android.support.v7.widget.CardView layouttwo;
    private com.victor.nesthabit.view.SwitchButton limittoggle;
    private Button submit;
    private AddNestContract.Presenter mPresenter;
    private boolean isAmountlimited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddNestPresenter(this);
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
        start_time = (TextView) findViewById(R.id.start_time);
        amount = (EditText) findViewById(R.id.amount);
        submit = (Button) findViewById(R.id.submit);
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(AddNestActivity.this);
            }
        });
        limittoggle.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                isAmountlimited = on;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.finish();
            }
        });
        beginlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(getActivityToPush(), ChooseActivity.class);
            }
        });

    }

    @Override
    public void setPresenter(AddNestContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getName() {
        return name.getText().toString();
    }

    @Override
    public String getIntroduction() {
        return introduction.getText().toString();
    }

    @Override
    public int getDay() {
        return Integer.valueOf(day.getText().toString());
    }

    @Override
    public String getStartTime() {
        return start_time.getText().toString();
    }

    @Override
    public boolean IsAmountLimited() {
        return isAmountlimited;
    }

    @Override
    public int getAmount() {
        return Integer.valueOf(amount.getText().toString());
    }

    @Override
    public void finishActivity() {
        ActivityManager.finishActivity(getActivityToPush());
    }
}
