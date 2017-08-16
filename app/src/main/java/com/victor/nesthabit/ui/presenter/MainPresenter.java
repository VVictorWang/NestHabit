package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.data.Nests;
import com.victor.nesthabit.data.UserInfo;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.MainContract;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MainPresenter extends RxPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    public static final String TAG = "@victor MainPresenter";
    private static NestDateBegin sNestDateBegin;
    private static ClockDataBegin sClockDataBegin;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showProgress();
        UserApi api = UserApi.getInstance();
        String key = Utils.createAcacheKey("get-userinfo", Utils.getUsername());
        Observable<UserInfo> responseObservable = api.getUserInfo(Utils.getUsername(),
                Utils.getHeader()).compose(RxUtil.<UserInfo>rxCacheBeanHelper(key));
        Subscription subscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, UserInfo
                .class), responseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        if (sNestDateBegin != null && userInfo.getJoined_nests()!=null && !userInfo.getJoined_nests().isEmpty()) {
                            List<Nests> nestses = userInfo.getJoined_nests();
                            List<String> nestid = new ArrayList<String>();
                            Observable.from(nestses)
                                    .subscribeOn(AndroidSchedulers.mainThread())
                                    .map(new Func1<Nests, String>() {

                                        @Override
                                        public String call(Nests nests) {
                                            return nests.get_id();
                                        }
                                    })
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onCompleted() {
                                            if (!nestid.isEmpty()) {
                                                sNestDateBegin.begin(nestid);
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(String s) {
                                            nestid.add(s);
                                        }
                                    });

                        }
                        if (sClockDataBegin != null) {
                            sClockDataBegin.begin(userInfo.getAlarm_clocks());
                        }
                        mView.saveUserId(userInfo.getId());
                    }
                });
        addSubscribe(subscription);
//        if (userInfoResponse.code() == 200) {
//            DataSupport.deleteAll(UserInfo.class);
//            userInfoResponse.body().save();
//        }
//        Log.d(TAG, "code: " + userInfoResponse.code());
//        if (userInfoResponse.code() == 200) {
//            mView.saveUserId(userInfoResponse.body().getId());
//
//        }

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    public static void setNestDateBegin(NestDateBegin nestDateBegin) {
        sNestDateBegin = nestDateBegin;
    }

    public static void setClockDataBegin(ClockDataBegin clockDataBegin) {
        sClockDataBegin = clockDataBegin;
    }

    interface ClockDataBegin {
        void begin(List<String> alarmids);
    }


    interface NestDateBegin {
        void begin(List<String> ids);
    }

}
