package com.victor.nesthabit.ui.presenter;

import android.content.ContentValues;

import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.ui.contract.AddAlarmContract;
import com.victor.nesthabit.utils.CheckUtils;
import com.victor.nesthabit.utils.DateUtils;

import org.litepal.crud.DataSupport;

import static android.R.attr.id;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AddAlarmPresenter implements AddAlarmContract.Presenter {
    private AddAlarmContract.View mView;
    public static final String TAG = "@victor AlarmPresenter";
    private static OnDataChanged mOnDataChanged;
    private AlarmTime mAlarmTime = null;

    public AddAlarmPresenter(AddAlarmContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        long id = mView.getIntentId();
        if (id == -1) {
            mView.clearText();
            mView.setSeletedHour(DateUtils.getCurrentHour());
            mView.setSeletedMinute(DateUtils.getCurrentMinute());

        } else {
            mView.setEditToobar();
            mAlarmTime = DataSupport.find(AlarmTime.class, id);
            mView.setEditTitle(mAlarmTime.getTitle());
            mView.setSeletedHour(String.format("%02d", mAlarmTime.getHour()));
            mView.setSeletedMinute(String.format("%02d", mAlarmTime.getMinute()));
            mView.setMusic(mAlarmTime.getAlert_music());
            mView.setSnap(mAlarmTime.isSnap());
            mView.setVoice(mAlarmTime.isReceive_Voice());
            mView.setRemindText(mAlarmTime.isReceive_text());
        }
    }

    @Override
    public void finish() {

        AlarmTime alarmTime = new AlarmTime();
        alarmTime.setWeeks(mView.getSeletedWeek());
        alarmTime.setHour(Integer.valueOf(mView.getSeletedHour()));
        alarmTime.setMinute(Integer.valueOf(mView.getSeletedMinute()));
        if (CheckUtils.isEmpty(mView.getMusic())) {
            mView.setMusicError();
        } else if (CheckUtils.isEmpty(mView.getEditTitle())) {
            mView.setEditTitleError();
        } else if (CheckUtils.isEmpty(mView.getNestName())) {
            mView.setNestError();
        } else {
            alarmTime.setAlert_music(mView.getMusic());
            alarmTime.setTitle(mView.getEditTitle());
            alarmTime.setReceive_text(mView.getRemindText());
            alarmTime.setReceive_Voice(mView.getVoice());
            alarmTime.setSnap(mView.getSnap());
            alarmTime.save();
            if (mOnDataChanged != null) {
                if (mAlarmTime != null) {
                    mAlarmTime.delete();
                    mOnDataChanged.OnDataModified();
                    mAlarmTime = null;
                } else
                    mOnDataChanged.OnDataAdded(alarmTime);
            }
            mView.setAlarm(alarmTime);
            mView.finishActivity();
        }

    }

    public static void setOnDataChanged(OnDataChanged onDataChanged) {
        mOnDataChanged = onDataChanged;
    }

    public interface OnDataChanged {
        void OnDataAdded(AlarmTime alarmTime);

        void OnDataModified();
    }
}
