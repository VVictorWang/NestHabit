package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.util.ActivityManager;

public class ShareActivity extends BaseActivity {

    private View toolbar;
    private android.widget.EditText sharetext;
    private android.support.v7.widget.CardView sharecardlayout;
    private android.widget.Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Activity getActivity() {
        return ShareActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected void initView() {
        this.submit = (Button) findViewById(R.id.submit);
        this.sharecardlayout = (CardView) findViewById(R.id.share_card_layout);
        this.sharetext = (EditText) findViewById(R.id.share_text);
        this.toolbar = findViewById(R.id.toolbar);
        setToolbar();
    }

    @Override
    protected void initEvent() {
        (toolbar.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(getActivity());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(getActivity(), ShareSuccessActivity.class);
            }
        });
    }

    private void setToolbar() {
        ((TextView) (toolbar.findViewById(R.id.title_text))).setText(getString(R.string
                .share_and_record));
        (toolbar.findViewById(R.id.right_text)).setVisibility(View.GONE);
    }
}