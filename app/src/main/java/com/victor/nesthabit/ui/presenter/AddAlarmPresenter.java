package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.bean.AlarmResponse;
import com.victor.nesthabit.bean.AlarmTime;
import com.victor.nesthabit.service.PostMusicService;
import com.victor.nesthabit.ui.contract.AddAlarmContract;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.CheckUtils;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.PrefsUtils;
import com.victor.nesthabit.util.Utils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AddAlarmPresenter implements AddAlarmContract.Presenter, PostMusicService.OnAlarmAdded {
    public static final String TAG = "@victor AlarmPresenter";
    private static OnDataChanged mOnDataChanged;

    private AddAlarmContract.View mView;
    private AlarmTime mAlarmTime = null;

    private AlarmTime alarmTime;
    private long id = -1;

    public AddAlarmPresenter(AddAlarmContract.View view) {
        mView = view;
        mView.setPresenter(this);
        PostMusicService.setOnAlarmAdded(this);
    }


    @Override
    public void start() {
        id = mView.getIntentId();
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
            String musicUri = PrefsUtils.getValue(AppUtils.getAppContext(), mAlarmTime
                    .getMusic_id(), "empty");
            if (!"empty".equals(musicUri)) {
                File file = new File(musicUri);
                if (file.exists()) {
                    mView.setMusic(file.getName());
                }
            }
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
        alarmTime = new AlarmTime();
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

            alarmTime.setHour(Integer.valueOf(mView.getSeletedHour()));
            alarmTime.setMinute(Integer.valueOf(mView.getSeletedMinute()));
//            alarmTime.setAlert_music(mView.getMusic());
            alarmTime.setTitle(mView.getEditTitle());
            alarmTime.setReceive_text(mView.getRemindText());
            alarmTime.setReceive_Voice(mView.getVoice());
            alarmTime.setSnap(mView.getSnap());
            alarmTime.setBind_to_nest(mView.getNestName());
            alarmTime.setNestid(mView.getNestid());
            alarmTime.setWeeks(mView.getSeletedWeek());
            if (id == -1) {
                mOnDataChanged.OnDataAdded(alarmTime);
            } else {
                mOnDataChanged.OnDataModified(alarmTime);
            }
            mView.startPostService();
            mView.finishActivity();
        }

    }

    public static void setOnDataChanged(OnDataChanged onDataChanged) {
        mOnDataChanged = onDataChanged;
    }

    @Override
    public void onAlarmAdded(String musicId) {
        alarmTime.setMusic_id(musicId);
        alarmTime.save();
        List<Integer> weeks = alarmTime.getWeeks();
        List<Integer> repeat = new ArrayList<>();
        for (int i : weeks) {
            if (i == 1) {
                repeat.add(i);
            }
        }
//        int[] re = new int[repeat.size()];
//        for (int i = 0; i < repeat.size(); i++) {
//            re[i] = repeat.get(i);
//        }
        Log.d(TAG, "time: " + alarmTime.getHour());
        Log.d(TAG, "time minute: " + alarmTime.getMinute());

        List<Integer> time = new ArrayList<>();
        time.add(alarmTime.getHour());
        time.add(alarmTime.getMinute());
        Observable<AlarmResponse> observable = UserApi.getInstance().addAlarm
                (alarmTime.getTitle(), time, repeat, alarmTime.getMusic_id
                                (), alarmTime.isSnap(),
                        true, alarmTime.getNestid(), alarmTime
                                .isReceive_Voice(), alarmTime.isReceive_text(), Utils.getHeader());
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
                        Log.d(TAG, "failure: " + e.getMessage());
                    }

                    @Override
                    public void onNext(AlarmResponse alarmResponse) {

                        Log.d(TAG, "time: " + alarmResponse.getTime());
                        Log.d(TAG, "repeat: " + alarmResponse.getRepeat());
                    }
                });
    }

    public interface OnDataChanged {
        void OnDataAdded(AlarmTime alarmTime);

        void OnDataModified(AlarmTime alarmTime);
    }

}
