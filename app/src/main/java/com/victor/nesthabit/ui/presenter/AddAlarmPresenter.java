package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.ui.contract.AddAlarmContract;
import com.victor.nesthabit.util.CheckUtils;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.Utils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AddAlarmPresenter implements AddAlarmContract.Presenter {
    private AddAlarmContract.View mView;
    public static final String TAG = "@victor AlarmPresenter";
    private static OnDataChanged mOnDataChanged;
    private static OnAlarmAdded sOnAlarmAdded;
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

            alarmTime.setHour(Integer.valueOf(mView.getSeletedHour()));
            alarmTime.setMinute(Integer.valueOf(mView.getSeletedMinute()));
            alarmTime.setAlert_music(mView.getMusic());
            alarmTime.setTitle(mView.getEditTitle());
            alarmTime.setReceive_text(mView.getRemindText());
            alarmTime.setReceive_Voice(mView.getVoice());
            alarmTime.setSnap(mView.getSnap());
            alarmTime.setBind_to_nest(mView.getNestName());
            alarmTime.setNestid(mView.getNestid());
            alarmTime.setWeeks(mView.getSeletedWeek());
            alarmTime.save();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = new File(mView.getMusicUri());
                    RequestBody filebody = RequestBody.create(MediaType.parse
                            ("application/octet-stream"), file);
                    Request request = new Request.Builder().url(GlobalData.BASE_URL + "user/" +
                            Utils.getUsername() + "/uploaded_musics/" + file.getName()).header
                            (GlobalData.HEADER_AU, Utils.getHeader()).post(filebody).build();
                    OkHttpClient client = new OkHttpClient();
                    try {
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(TAG, "failure");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws
                                    IOException {
                                Log.d(TAG, response.body().string());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
//            MultipartBody.Part music = mView.getMusicUri();
//            if (music != null) {
//
//                Observable<PostMusicResponse> observable = UserApi.getInstance().postMusic(Utils
//                        .getUsername(), mView.getMusic(), music, Utils.getHeader());
//                observable.observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(new Observer<PostMusicResponse>() {
//                            @Override
//                            public void onCompleted() {
//                                if (sOnAlarmAdded != null) {
//                                    sOnAlarmAdded.onAlarmAdded(alarmTime);
//                                }
//                                if (mOnDataChanged != null) {
//                                    if (mAlarmTime != null) {
//                                        mAlarmTime.delete();
//                                        mOnDataChanged.OnDataModified();
//                                        mAlarmTime = null;
//                                    } else
//                                        mOnDataChanged.OnDataAdded(alarmTime);
//                                }
//                                mView.showToast("添加闹钟成功");
//                                mView.setAlarm(alarmTime);
//                                mView.finishActivity();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                mView.showToast("添加闹钟失败");
//                            }
//
//                            @Override
//                            public void onNext(PostMusicResponse postMusicResponse) {
//                                alarmTime.setMusic_id(postMusicResponse.get_id());
//                            }
//                        });
//
//            }

//

        }

    }

    public static void setOnDataChanged(OnDataChanged onDataChanged) {
        mOnDataChanged = onDataChanged;
    }

    public static void setOnAlarmAdded(OnAlarmAdded onAlarmAdded) {
        sOnAlarmAdded = onAlarmAdded;
    }

    public interface OnDataChanged {
        void OnDataAdded(AlarmTime alarmTime);

        void OnDataModified();
    }

    public interface OnAlarmAdded {
        void onAlarmAdded(AlarmTime alarmTime);
    }
}
