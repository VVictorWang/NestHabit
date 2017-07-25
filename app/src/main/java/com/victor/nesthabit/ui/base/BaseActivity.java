package com.victor.nesthabit.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.victor.nesthabit.util.ActivityManager;

import butterknife.ButterKnife;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ActivityManager.getInstance().pushActivity(getActivity());
        ButterKnife.bind(getActivity());
        initView();
        initEvent();
    }


    protected abstract Activity getActivity();

    protected abstract int getLayoutId();

    protected abstract void initView();



    protected abstract void initEvent();
}
