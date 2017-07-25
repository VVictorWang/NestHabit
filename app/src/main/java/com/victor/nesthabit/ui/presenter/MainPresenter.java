package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.UserInfo;
import com.victor.nesthabit.ui.contract.MainContract;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showProgress();
        UserApi api = UserApi.getInstance();
        Observable<Response<UserInfo>> responseObservable = api.getUserInfo(GlobalData.USERNAME,
                GlobalData.AUTHORIZATION);
        responseObservable.subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Response<UserInfo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<UserInfo> userInfoResponse) {
                        if (userInfoResponse.code() == 200) {
                            userInfoResponse.body().save();
                            mView.saveUserId(userInfoResponse.body().getId());
                            mView.hideProgress();
                            mView.setUpViewPager();
                            mView.setTab();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
