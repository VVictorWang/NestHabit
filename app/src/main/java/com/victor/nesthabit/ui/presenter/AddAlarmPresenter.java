package com.victor.nesthabit.ui.presenter;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.bean.AlarmTime;
import com.victor.nesthabit.bean.MusicInfo;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.service.PostMusicService;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.AddAlarmContract;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.CheckUtils;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.PrefsUtils;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AddAlarmPresenter extends RxPresenter implements AddAlarmContract.Presenter,
        PostMusicService
                .OnAlarmAdded {
    public static final String TAG = "@victor AlarmPresenter";
    private static OnDataChanged mOnDataChanged;

    private AddAlarmContract.View mView;
    private AlarmTime newAlarm;

    private String id = null;

    public AddAlarmPresenter(AddAlarmContract.View view) {
        mView = view;
        mView.setPresenter(this);
        PostMusicService.setOnAlarmAdded(this);
    }


    @Override
    public void start() {
        id = mView.getIntentId();
//        if (id == null) {
//            mView.clearText();
//            mView.setSeletedHour(DateUtils.getCurrentHour());
//            mView.setSeletedMinute(DateUtils.getCurrentMinute());
//
//        } else {
//            mView.setEditToobar();
//            String key = Utils.createAcacheKey("get_alarm_byid", id);
//            Observable<AlarmInfo> observable = NestHabitApi.getInstance().getAlarm(id, Utils
//                    .getHeader()).compose(RxUtil
//                    .<AlarmInfo>rxCacheBeanHelper(key));
//            Subscription subscription = Observable.concat(RxUtil.rxCreateDiskObservable(key,
//                    AlarmInfo.class), observable)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<AlarmInfo>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                            Log.d(TAG, "error: " + e.getMessage());
//                        }
//
//                        @Override
//                        public void onNext(AlarmInfo alarmResponse) {
//                            getInfo(alarmResponse);
//                        }
//                    });
//            addSubscribe(subscription);
//        }
//    }
//
//
//    private void getInfo(AlarmInfo alarmResponse) {
//        mView.setEditTitle(alarmResponse.title);
//        mView.setSeletedHour(Utils.getAlarmHour(alarmResponse.time));
//        mView.setSeletedMinute(Utils.getAlarmMinute(alarmResponse.time));
//        mView.setSelectedWeek(Utils.getSelectedWeeks(alarmResponse.repeat));
//        mView.setSnap(alarmResponse.nap);
//        mView.setVoice(alarmResponse.willing_music);
//        mView.setRemindText(alarmResponse.willing_text);
//        Observable<NestInfo> observable1 = NestHabitApi.getInstance().getNestInfo
//                (alarmResponse.bind_to_nest
//                        , Utils.getHeader());
//        Subscription subscription1 = observable1.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<NestInfo>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(NestInfo nestInfo) {
//                        mView.setNestname(nestInfo.name);
//                    }
//                });
//        addSubscribe(subscription1);
//        String musicUri = PrefsUtils.getValue(AppUtils.getAppContext(), alarmResponse.music_id,
//                "empty");
//        File file = new File(musicUri.substring(musicUri.indexOf('/') + 2));
//        Observable<MusicInfo> observable = NestHabitApi.getInstance().getMusicName(alarmResponse
//                .music_id, Utils.getHeader());
//        Subscription subscription = observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Action1<MusicInfo>() {
//                    @Override
//                    public void call(MusicInfo musicInfo) {
//                        mView.setMusic(musicInfo.getMusic_name().substring(0, musicInfo
//                                .getMusic_name().lastIndexOf('.')));
//                    }
//                })
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Observer<MusicInfo>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(MusicInfo musicInfo) {
//                        if (!file.exists()) {
//                            DownloadManager.Request request = new DownloadManager.Request
//                                    (Uri.parse(musicInfo.getUrl()));
//                            Log.d(TAG, musicInfo.getUrl());
//                            request.setDestinationInExternalPublicDir("/music/", musicInfo
//                                    .getMusic_name());
//                            DownloadManager downloadManager = (DownloadManager) AppUtils
//                                    .getAppContext().getSystemService(Context
//                                            .DOWNLOAD_SERVICE);
//                            long downloadid = downloadManager.enqueue(request);
//
//                            BroadcastReceiver receiver = new BroadcastReceiver() {
//                                @Override
//                                public void onReceive(Context context, Intent intent) {
//                                    DownloadManager.Query query = new DownloadManager.Query()
//                                            .setFilterById(downloadid);
//                                    Cursor c = downloadManager.query(query);
//                                    if (c.moveToFirst()) {
//                                        int status = c.getInt(c.getColumnIndex
//                                                (DownloadManager.COLUMN_STATUS));
//                                        switch (status) {
//                                            case DownloadManager.STATUS_PAUSED:
//                                            case DownloadManager.STATUS_PENDING:
//                                            case DownloadManager.STATUS_RUNNING:
//                                                break;
//                                            case DownloadManager.STATUS_SUCCESSFUL:
//                                                String url = c.getString(c
//                                                        .getColumnIndexOrThrow
//                                                                (DownloadManager
//                                                                        .COLUMN_LOCAL_URI));
//                                                mView.setMusicUri(url);
//                                                PrefsUtils.putValue(AppUtils.getAppContext(),
//                                                        alarmResponse.music_id, url);
//                                                Log.d(TAG, "url" + url);
//                                                break;
//                                        }
//                                    }
//                                }
//                            };
//                            AppUtils.getAppContext().registerReceiver(receiver, new
//                                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//                        } else {
//                            mView.setMusicUri(musicUri);
//                        }
//                    }
//                });
//        addSubscribe(subscription);
    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    @Override
    public void finish() {
        newAlarm = new AlarmTime();
        newAlarm.setWeeks(mView.getSeletedWeek());
        newAlarm.setHour(Integer.valueOf(mView.getSeletedHour()));
        newAlarm.setMinute(Integer.valueOf(mView.getSeletedMinute()));
        if (CheckUtils.isEmpty(mView.getEditTitle())) {
            mView.setEditTitleError();
        } else if (CheckUtils.isEmpty(mView.getNestName())) {
            mView.setNestError();
        } else if (CheckUtils.isEmpty(mView.getMusic())) {
            mView.setMusicError();
        } else {
            newAlarm.setHour(Integer.valueOf(mView.getSeletedHour()));
            newAlarm.setMinute(Integer.valueOf(mView.getSeletedMinute()));
            newAlarm.setTitle(mView.getEditTitle());
            newAlarm.setReceive_text(mView.getRemindText());
            newAlarm.setReceive_Voice(mView.getVoice());
            newAlarm.setSnap(mView.getSnap());
            newAlarm.setBind_to_nest(mView.getNestName());
            newAlarm.setNestid(mView.getNestid());
            newAlarm.setWeeks(mView.getSeletedWeek());
            if (id == null) {
                mOnDataChanged.OnDataAdded(newAlarm);
            } else {
                mOnDataChanged.OnDataModified(newAlarm);
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
//        newAlarm.setMusic_id(musicId);
//        List<Integer> weeks = newAlarm.getWeeks();
//        List<Integer> repeat = new ArrayList<>();
//        for (int i : weeks) {
//            if (i == 1) {
//                repeat.add(i);
//            }
//        }
//        List<Integer> time = new ArrayList<>();
//        time.add(newAlarm.getHour());
//        time.add(newAlarm.getMinute());
//        Observable<AlarmInfo> observable;
//        if (id == null) {
//            observable = NestHabitApi.getInstance().addAlarm();
//        } else {
//            observable = NestHabitApi.getInstance().changeAlarm(newAlarm);
//
//        }
//        observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .doOnNext(new Action1<AlarmInfo>() {
//                    @Override
//                    public void call(AlarmInfo alarmResponse) {
//                        newAlarm.setMyid(alarmResponse.getObjectId());
//                        newAlarm.save();
//                    }
//                })
//                .subscribe(new Observer<AlarmInfo>() {
//                    @Override
//                    public void onCompleted() {
//                        mView.showMyToast("添加成功");
//                        mOnDataChanged.OnDataModified(newAlarm);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.showMyToast("添加失败");
//                        Log.d(TAG, "failure: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(AlarmInfo alarmResponse) {
//
//                    }
//                });
    }

    public interface OnDataChanged {
        void OnDataAdded(AlarmTime alarmTime);

        void OnDataModified(AlarmTime alarmTime);
    }

}
