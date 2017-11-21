package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.UpdateInfo;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.AlarmRepoitory;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.ReposityCallback;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.MainContract;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MainPresenter extends RxPresenter implements MainContract.Presenter,
        AddNestPresenter.NotifyNestAdded {
    public static final String TAG = "@victor MainPresenter";
    private MainContract.View mView;

    UserRepository mUserRepository;


    NestRepository mNestRepository;

    AlarmRepoitory mAlarmRepoitory;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mUserRepository = UserRepository.getInstance();
        mAlarmRepoitory = AlarmRepoitory.getInstance();
        mNestRepository = NestRepository.getInstance();
        AddNestPresenter.setNotifyNestAdded(this);
    }


    @Override
    public void start() {
        mView.showProgress();
        mUserRepository.login(Utils.getUsername(), Utils.getPassword(), new NetWorkBoundUtils
                .CallBack<UserInfo>
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


    @Override
    public void notifyNestAdded(String nestId) {
        mUserRepository.login(Utils.getUsername(), null, new NetWorkBoundUtils.CallBack<UserInfo>
                () {
            @Override
            public void callSuccess(Observable<UserInfo> result) {
                result.subscribeOn(Schedulers.io())
                        .doOnNext(userInfo -> {
                            List<String> nests = userInfo.getJoined_nests();
                            if (nests == null) {
                                nests = new ArrayList<>();
                            }
                            List<String> temp = new ArrayList<>();
                            temp.addAll(nests);
                            temp.add(nestId);
                            userInfo.setJoined_nests(temp);
                        })
                        .subscribe(userInfo -> mUserRepository.changeUserInfo(userInfo, new
                                ReposityCallback<UpdateInfo>() {

                                    @Override
                                    public void callSuccess(UpdateInfo data) {
                                        Log.d(TAG, "success " + data.getUpdatedAt());
                                        mUserRepository.deleteUserInDb(userInfo.getUsername());
                                    }

                                    @Override
                                    public void callFailure(String errorMessage) {
                                        Log.d(TAG, errorMessage);
                                    }
                                }));
                mNestRepository.loadNestInfo(nestId, new NetWorkBoundUtils.CallBack<NestInfo>() {
                    @Override
                    public void callSuccess(Observable<NestInfo> result) {
                        result.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(nestInfo -> mView.addNestInfo
                                        (nestInfo));
                    }

                    @Override
                    public void callFailure(String errorMessage) {

                    }
                });
            }

            @Override
            public void callFailure(String errorMessage) {

            }
        });
    }
}
