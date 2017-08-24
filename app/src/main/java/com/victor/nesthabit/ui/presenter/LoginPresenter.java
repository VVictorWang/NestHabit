package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.bean.GlobalData;
import com.victor.nesthabit.bean.LoginResponse;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.LoginContract;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.PrefsUtils;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class LoginPresenter extends RxPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mView.setPresenter(this);
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
        UserApi userApi = UserApi.getInstance();
        Observable<LoginResponse> response = userApi.login(username, password);
        Subscription subscription = response.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<LoginResponse>() {
                    @Override
                    public void call(LoginResponse loginResponse) {
                        PrefsUtils.putValue(AppUtils.getAppContext(), GlobalData
                                .AUTHORIZATION, loginResponse.getAuthorization());
                        PrefsUtils.putValue(AppUtils.getAppContext(), GlobalData.USERNAME,
                                "test");
                    }
                })
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.switchToMain();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(LoginResponse loginResponseResponse) {

                    }
                });
        addSubscribe(subscription);

    }
}
