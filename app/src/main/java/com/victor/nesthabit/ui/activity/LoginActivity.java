package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.victor.nesthabit.R;
import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.RegisterResponse;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.LoginContract;
import com.victor.nesthabit.ui.presenter.LoginPresenter;
import com.victor.nesthabit.util.ActivityManager;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    public static final String TAG = "@victor LoginActivity";
    private android.widget.RelativeLayout loginqq;
    private android.widget.RelativeLayout loginweichat;
    private LoginContract.Presenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLoginPresenter = new LoginPresenter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        this.loginweichat = (RelativeLayout) findViewById(R.id.login_weichat);
        this.loginqq = (RelativeLayout) findViewById(R.id.login_qq);
    }

    @Override
    protected void initEvent() {
        loginqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.login("victor", "12345");
            }
        });
        loginweichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.register("example","1234",data -> );
            }
        });
    }

    @Override
    protected Activity getActivity() {
        return LoginActivity.this;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mLoginPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    public void switchToMain() {
        ActivityManager.startActivity(LoginActivity.this, MainActivity
                .class);
        ActivityManager.finishActivity(getActivity());
    }


    @Override
    protected BasePresenter getPresnter() {
        return mLoginPresenter;
    }
}
