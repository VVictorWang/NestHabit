package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.AlarmResponse;
import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.ClockListContract;
import com.victor.nesthabit.util.DataCloneUtil;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class ClockListPresenter extends RxPresenter implements ClockListContract.Presenter,
        MainPresenter
        .ClockDataBegin {
    private ClockListContract.View mView;
    public static final String TAG = "@victor ClockListPrese";
    private static onAlarmAdded sOnAlarmAdded;

    public ClockListPresenter(ClockListContract.View view) {
        mView = view;
        mView.setPresenter(this);
        MainPresenter.setClockDataBegin(this);
    }

    @Override
    public void start() {
        List<AlarmTime> alarmTimes = DataSupport.findAll(AlarmTime.class);
        if (alarmTimes != null && !alarmTimes.isEmpty() && sOnAlarmAdded != null) {
            for (AlarmTime alarmTime : alarmTimes) {
                sOnAlarmAdded.AlarmAdded(alarmTime);
            }
        }

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    //当用户信息获取完全才开始获取鸟窝列表
    @Override
    public void begin(List<String> alarmids) {
        if (!alarmids.isEmpty()) {
            for (String alarmid : alarmids) {
                String key = Utils.createAcacheKey("get_alarm_byid", alarmid);
                Observable<AlarmResponse> responseObservable = UserApi.getInstance()
                        .getAlarm(alarmid, Utils.getHeader()).compose(RxUtil
                                .<AlarmResponse>rxCacheBeanHelper(key));
                Subscription subscription = Observable.concat(RxUtil.rxCreateDiskObservable(key,
                        AlarmResponse.class), responseObservable)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<AlarmResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(AlarmResponse alarmResponse) {
                                AlarmTime alarmTime = DataCloneUtil.cloneAlarmRestoTime
                                        (alarmResponse);
                                alarmTime.save();
                                if (sOnAlarmAdded != null) {
                                    sOnAlarmAdded.AlarmAdded(alarmTime);
                                }
                            }
                        });
                addSubscribe(subscription);


            }
        }

    }

    public static void setOnAlarmAdded(onAlarmAdded alarmAdded) {
        sOnAlarmAdded = alarmAdded;
    }

    public interface onAlarmAdded {
        void AlarmAdded(AlarmTime alarmTime);
    }
}
