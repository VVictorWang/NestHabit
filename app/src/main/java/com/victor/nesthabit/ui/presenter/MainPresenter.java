package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.MainContract;
import com.victor.nesthabit.util.Utils;

import java.util.List;

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
    private static NestDateBegin sNestDateBegin;
    private static ClockDataBegin sClockDataBegin;
    private MainContract.View mView;

    @Inject
    UserRepository mUserRepository;

    @Inject
    NestRepository mNestRepository;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }



    public static void setClockDataBegin(ClockDataBegin clockDataBegin) {
        sClockDataBegin = clockDataBegin;
    }

    @Override
    public void start() {
        mView.showProgress();
        Observable<UserInfo> userInfoObservable = mUserRepository.login(Utils.getUsername(), null);
        if (userInfoObservable != null) {
            Subscription subscription = userInfoObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userInfo -> {
                        for (String nestId : userInfo.getJoined_nests()) {
                            mNestRepository.loadNestInfo(nestId).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(nestInfo -> {
                                        mView.addNestInfo(nestInfo);
                                    });
                        }
                    });
            addSubscribe(subscription);
        }


    }

    @Override
    public void unscribe() {
        unSubscribe();
    }



}
