package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.Nests;
import com.victor.nesthabit.bean.UserInfo;
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
    public static final String TAG = "@victor MainPresenter";
    private static NestDateBegin sNestDateBegin;
    private static ClockDataBegin sClockDataBegin;
    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    public static void setNestDateBegin(NestDateBegin nestDateBegin) {
        sNestDateBegin = nestDateBegin;
    }

    public static void setClockDataBegin(ClockDataBegin clockDataBegin) {
        sClockDataBegin = clockDataBegin;
    }

    @Override
    public void start() {
        mView.showProgress();
        NestHabitApi api = NestHabitApi.getInstance();
        String key = Utils.createAcacheKey("get-userinfo", Utils.getUsername());
        Observable<UserInfo> responseObservable = api.getUserInfo(Utils.getUsername(),
                Utils.getHeader()).compose(RxUtil.<UserInfo>rxCacheBeanHelper(key));
        Subscription subscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, UserInfo
                .class), responseObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        if (sNestDateBegin != null && userInfo.getJoined_nests() != null &&
                                !userInfo.getJoined_nests().isEmpty()) {
                            List<String> nestses = userInfo.getJoined_nests();
                            List<String> nestid = new ArrayList<String>();
                            Observable.from(nestses)
                                    .subscribeOn(AndroidSchedulers.mainThread())
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
//                        mView.saveUserId(userInfo.getId());
                    }
                });
        addSubscribe(subscription);

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }


    interface ClockDataBegin {
        void begin(List<String> alarmids);
    }


    interface NestDateBegin {
        void begin(List<String> ids);
    }

}
