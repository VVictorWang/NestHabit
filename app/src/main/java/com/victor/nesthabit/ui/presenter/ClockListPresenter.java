package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.AlarmResponse;
import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.UserInfo;
import com.victor.nesthabit.ui.contract.ClockListContract;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.DataCloneUtil;
import com.victor.nesthabit.util.PrefsUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class ClockListPresenter implements ClockListContract.Presenter, MainPresenter
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

    //当用户信息获取完全才开始获取鸟窝列表
    @Override
    public void begin(long id) {
        UserInfo info = DataSupport.find(UserInfo.class, id);
        List<String> alarmids = new ArrayList<>();
        if (info != null && info.getAlarm_clocks() != null) {
            alarmids = info.getAlarm_clocks();
        }
        if (!alarmids.isEmpty()) {
            for (String alarmid : alarmids) {
                Observable<AlarmResponse> responseObservable = UserApi.getInstance()
                        .getAlarm(alarmid, PrefsUtils.getValue(AppUtils.getAppContext(),
                                GlobalData.AUTHORIZATION, "null"));
                responseObservable.subscribeOn(Schedulers.newThread())
                        .subscribe(alarmResponseResponse -> {
                                AlarmResponse response = alarmResponseResponse;
                                AlarmTime alarmTime = DataCloneUtil.cloneAlarmRestoTime(response);
                                alarmTime.save();
                                if (sOnAlarmAdded != null) {
                                    sOnAlarmAdded.AlarmAdded(alarmTime);
                                }
                        });
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
