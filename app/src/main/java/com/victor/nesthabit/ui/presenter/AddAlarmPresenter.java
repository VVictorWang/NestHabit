package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.ui.contract.AddAlarmContract;
import com.victor.nesthabit.util.CheckUtils;
import com.victor.nesthabit.util.DateUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

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
    public void unscribe() {

    }

    @Override
    public void finish() {

        AlarmTime alarmTime = new AlarmTime();
        alarmTime.setWeeks(mView.getSeletedWeek());
        alarmTime.setHour(Integer.valueOf(mView.getSeletedHour()));
        alarmTime.setMinute(Integer.valueOf(mView.getSeletedMinute()));
        if (CheckUtils.isEmpty(mView.getEditTitle())) {
            mView.setEditTitleError();
        } else if (CheckUtils.isEmpty(mView.getNestName())) {
            mView.setNestError();
        } else if (CheckUtils.isEmpty(mView.getMusic())) {
            mView.setMusicError();
        } else {
            alarmTime.setAlert_music(mView.getMusic());
            alarmTime.setTitle(mView.getEditTitle());
            alarmTime.setReceive_text(mView.getRemindText());
            alarmTime.setReceive_Voice(mView.getVoice());
            alarmTime.setSnap(mView.getSnap());
            alarmTime.save();
            List<Integer> weeks = mView.getSeletedWeek();
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
//            Observable<Response<AlarmResponse>> observable = UserApi.getInstance().addAlarm
//                    (alarmTime.getTitle(), re, "2342", 1, 2, 1, "21321", alarmTime
//                            .isReceive_Voice(), alarmTime.isReceive_text(), new int[]{alarmTime
// .getHour(),alarmTime.getMinute()
//                    },)
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
