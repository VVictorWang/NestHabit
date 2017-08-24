package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.bean.AlarmResponse;
import com.victor.nesthabit.bean.AlarmTime;
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

public class MainPresenter extends RxPresenter implements MainContract.Presenter,
        AddAlarmPresenter.OnAlarmAdded {
    private MainContract.View mView;
    public static final String TAG = "@victor MainPresenter";
    private static NestDateBegin sNestDateBegin;
    private static ClockDataBegin sClockDataBegin;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
        AddAlarmPresenter.setOnAlarmAdded(this);
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

    @Override
    public void onAlarmAdded(AlarmTime alarmTime) {
        List<Integer> weeks = alarmTime.getWeeks();
        List<Integer> repeat = new ArrayList<>();
        for (int i : weeks) {
            if (weeks.get(i) == 1) {
                repeat.add(i);
            }
        }
        int[] re = new int[repeat.size()];
        for (int i = 0; i < repeat.size(); i++) {
            re[i] = repeat.get(i);
        }
        Observable<AlarmResponse> observable = UserApi.getInstance().addAlarm
                (alarmTime.getTitle(), new int[]{alarmTime
                                .getHour(), alarmTime.getMinute()}, re, alarmTime.getMusic_id
                                (), alarmTime.isSnap(),
                        true, alarmTime.getBind_to_nest(), alarmTime
                                .isReceive_Voice(), alarmTime.isReceive_text(), alarmTime
                                .getNestid(), Utils.getUsername(), Utils.getHeader());
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AlarmResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.showMyToast("添加成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMyToast("添加失败");
                    }

                    @Override
                    public void onNext(AlarmResponse alarmResponse) {

                    }
                });
    }

    interface ClockDataBegin {
        void begin(List<String> alarmids);
    }


    interface NestDateBegin {
        void begin(List<String> ids);
    }

}
