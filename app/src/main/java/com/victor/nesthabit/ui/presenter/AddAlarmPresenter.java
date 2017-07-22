package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.ui.contract.AddAlarmContract;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AddAlarmPresenter implements AddAlarmContract.Presenter {
    private AddAlarmContract.View mView;
    public static final String TAG = "@victor AlarmPresenter";
    private static OnDataChanged mOnDataChanged;

    public AddAlarmPresenter(AddAlarmContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {
        AlarmTime alarmTime = new AlarmTime();
        alarmTime.setWeeks(mView.getSeletedWeek());
        alarmTime.setHour(Integer.valueOf(mView.getSeletedHour()));
        alarmTime.setMinute(Integer.valueOf(mView.getSeletedMinute()));
        alarmTime.setAlert_music(mView.getMusic());
        alarmTime.setTitle(mView.getEditTitle());
        alarmTime.setReceive_text(mView.getRemindText());
        alarmTime.setReceive_Voice(mView.getVoice());
        alarmTime.setSnap(mView.getSnap());
        alarmTime.save();
        if (mOnDataChanged != null) {
            mOnDataChanged.OnDataAdded(alarmTime);
        }
        mView.setAlarm(alarmTime);
        mView.finishActivity();
    }

    public static void setOnDataChanged(OnDataChanged onDataChanged) {
        mOnDataChanged = onDataChanged;
    }

    public interface OnDataChanged {
        void OnDataAdded(AlarmTime alarmTime);
    }
}
