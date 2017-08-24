package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.view.CircleImageView;

public class ShareSuccessActivity extends BaseActivity {

    private View toolbar;
    private com.victor.nesthabit.view.CircleImageView qq;
    private com.victor.nesthabit.view.CircleImageView qzone;
    private com.victor.nesthabit.view.CircleImageView wtchat;
    private com.victor.nesthabit.view.CircleImageView moment;
    private TextView finish;


    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected Activity getActivity() {
        return ShareSuccessActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_success;
    }

    @Override
    protected void initView() {
        this.moment = (CircleImageView) findViewById(R.id.moment);
        this.wtchat = (CircleImageView) findViewById(R.id.wtchat);
        this.qzone = (CircleImageView) findViewById(R.id.qzone);
        this.qq = (CircleImageView) findViewById(R.id.qq);
        this.toolbar = findViewById(R.id.toolbar);
        finish = (TextView) toolbar.findViewById(R.id.right_text);
        setToolbar();
    }

    @Override
    protected void initEvent() {
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setToolbar() {
        ((TextView) toolbar.findViewById(R.id.title_text)).setText(getString(R.string.add_success));
        finish.setText(getString(R.string.finish));
    }
}