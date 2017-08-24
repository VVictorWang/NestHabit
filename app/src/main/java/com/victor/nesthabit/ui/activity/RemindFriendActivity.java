package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.ui.adapter.RemindFriendAdapter;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.util.ActivityManager;

public class RemindFriendActivity extends BaseActivity {

    private View toolbar;
    private android.support.v7.widget.RecyclerView list;
    private android.support.design.widget.FloatingActionButton add;


    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected Activity getActivity() {
        return RemindFriendActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remind_friend;
    }

    @Override
    protected void initView() {
        this.add = (FloatingActionButton) findViewById(R.id.add);
        this.list = (RecyclerView) findViewById(R.id.list);
        this.toolbar = findViewById(R.id.toolbar);
        setToolbar();
        list.setLayoutManager(new LinearLayoutManager(RemindFriendActivity.this));
        list.setAdapter(new RemindFriendAdapter(RemindFriendActivity.this));
    }

    @Override
    protected void initEvent() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(RemindFriendActivity.this, AddRemindActivity.class);
            }
        });
        (toolbar.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(RemindFriendActivity.this);
            }
        });

    }

    private void setToolbar() {
        TextView textView = (TextView) (toolbar.findViewById(R.id.title_text));
        (toolbar.findViewById(R.id.right_text)).setVisibility(View.INVISIBLE);
        textView.setText(getString(R.string.remind_friend));
    }
}
