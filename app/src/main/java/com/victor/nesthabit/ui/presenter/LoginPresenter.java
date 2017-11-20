package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.bean.Constants;
import com.victor.nesthabit.bean.RegisterResponse;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.ReposityCallback;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.LoginContract;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.PrefsUtils;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class LoginPresenter extends RxPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    private UserRepository mUserRepository;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mUserRepository = UserRepository.getInstance();
    }


    @Override
    public void start() {

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    @Override
    public void login(String username, String password) {
        mUserRepository.login(username, password, new NetWorkBoundUtils.CallBack<UserInfo>() {
            @Override
            public void callSuccess(Observable<UserInfo> result) {
                Subscription subscription = result.observeOn
                        (AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .doOnNext(userInfo -> {
                            PrefsUtils.putValue(AppUtils.getAppContext(),
                                    Constants.AUTHORIZATION, userInfo.getSessionToken());
                            PrefsUtils.putValue(AppUtils.getAppContext(), Constants.USERNAME,
                                    userInfo.getUsername());
                        })
                        .subscribe(userInfo -> {
                            mView.showMyToast("登录成功");
                            mView.switchToMain();
                        });
                addSubscribe(subscription);
            }

            @Override
            public void callFailure(String errorMessage) {
                mView.showMyToast("登陆失败 " + errorMessage);
            }
        });

    }

    @Override
    public void register(String username, String password, ReposityCallback<RegisterResponse>
            callback) {
        mUserRepository.register(username, password, callback);
    }
}
