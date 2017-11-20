package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.AlarmRepoitory;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.MainContract;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.Utils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MainPresenter extends RxPresenter implements MainContract.Presenter {
    public static final String TAG = "@victor MainPresenter";
    private MainContract.View mView;

    @Inject
    UserRepository mUserRepository;

    @Inject
    NestRepository mNestRepository;

    @Inject
    AlarmRepoitory mAlarmRepoitory;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void start() {
        mView.showProgress();
        mUserRepository.login(Utils.getUsername(), null, new NetWorkBoundUtils.CallBack<UserInfo>
                () {
            @Override
            public void callSuccess(Observable<UserInfo> result) {
                Subscription subscription = result.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(userInfo -> {
                            for (String nestId : userInfo.getJoined_nests()) {
                                mNestRepository.loadNestInfo(nestId, new NetWorkBoundUtils
                                        .CallBack<NestInfo>() {

                                    @Override
                                    public void callSuccess(Observable<NestInfo> result) {
                                        result.subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(nestInfo -> mView.addNestInfo(nestInfo));
                                    }

                                    @Override
                                    public void callFailure(String errorMessage) {
                                        mView.showMyToast(errorMessage);
                                    }
                                });
                            }
                            for (String alarmId : userInfo.getAlarm_clocks()) {
                                mAlarmRepoitory.loadAlarmInfo(alarmId, new NetWorkBoundUtils
                                        .CallBack<AlarmInfo>() {


                                    @Override
                                    public void callSuccess(Observable<AlarmInfo> result) {
                                        result.subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(alarmInfo -> mView.addAlarmTime
                                                        (alarmInfo));
                                    }

                                    @Override
                                    public void callFailure(String errorMessage) {
                                        mView.showMyToast("闹钟获取失败 " + errorMessage);
                                    }
                                });
                            }
                            mView.hideProgress();
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
    public void unscribe() {
        unSubscribe();
    }


}
