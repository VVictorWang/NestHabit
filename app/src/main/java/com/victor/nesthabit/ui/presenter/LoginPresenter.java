package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.ui.contract.LoginContract;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
