package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.victor.nesthabit.R;
import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.RegisterResponse;
import com.victor.nesthabit.db.NestHabitDataBase;
import com.victor.nesthabit.repository.ReposityCallback;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.LoginContract;
import com.victor.nesthabit.ui.presenter.LoginPresenter;
import com.victor.nesthabit.util.ActivityManager;

public class LoginActivity extends BaseActivity implements LoginContract.View {


    public static final String TAG = "@victor LoginActivity";
    private android.widget.RelativeLayout loginqq;
    private android.widget.RelativeLayout loginweichat;

    private LoginContract.Presenter mLoginPresenter;

    private UserRepository mUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mUserRepository = new UserRepository(NestHabitDataBase.getInstance(this).userDao(),
                NestHabitApi.getInstance());
        mLoginPresenter = new LoginPresenter(mUserRepository, this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        this.loginweichat = findViewById(R.id.login_weichat);
        this.loginqq = findViewById(R.id.login_qq);
    }

    @Override
    protected void initEvent() {
        loginqq.setOnClickListener(v -> mLoginPresenter.login("example", "1234"));
        loginweichat.setOnClickListener(v -> mLoginPresenter.register("example", "1234",
                new ReposityCallback<RegisterResponse>() {
                    @Override
                    public void callSuccess(RegisterResponse data) {
                        showToast("注册成功");
                    }

                    @Override
                    public void callFailure(String errorMessage) {
                        showToast("注册失败: " + errorMessage);
                    }
                }));
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
