package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.bean.AlarmTime;
import com.victor.nesthabit.bean.PostFileResponse;
import com.victor.nesthabit.bean.UpdateInfo;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.AlarmRepoitory;
import com.victor.nesthabit.repository.FileRepositoty;
import com.victor.nesthabit.repository.ReposityCallback;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.service.PostMusicService;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.AddAlarmContract;
import com.victor.nesthabit.util.CheckUtils;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
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

    private AddAlarmContract.View mView;

    private AlarmRepoitory mAlarmRepoitory;
    private FileRepositoty mFileRepositoty;


    public AddAlarmPresenter(AddAlarmContract.View view) {
        mView = view;
        mView.setPresenter(this);
        PostMusicService.setOnAlarmAdded(this);
        mAlarmRepoitory = AlarmRepoitory.getInstance();
        mFileRepositoty = FileRepositoty.getInstance();
    }


    @Override
    public void start() {
        String id = mView.getIntentId();
        if (id == null) {
            mView.clearText();
            mView.setSeletedHour(DateUtils.getCurrentHour());
            mView.setSeletedMinute(DateUtils.getCurrentMinute());
        } else {
            mView.setEditToobar();
            mAlarmRepoitory.loadAlarmInfo(id, new NetWorkBoundUtils.CallBack<AlarmInfo>() {
                @Override
                public void callSuccess(Observable<AlarmInfo> result) {
                    result.observeOn(AndroidSchedulers.mainThread())
                            .subscribe(alarmInfo -> {
                                mView.setEditTitle(alarmInfo.getTitle());
                                mView.setNestname(alarmInfo.getBind_to_nest());
                                mView.setRemindText(alarmInfo.isWilling_text());
                                mView.setSelectedWeek(alarmInfo.getRepeat());
                                mView.setSeletedHour(String.valueOf(alarmInfo.getHour()));
                                mView.setSeletedMinute(String.valueOf(alarmInfo.getMinute()));
                                mView.setSnap(alarmInfo.isNap());
                                mView.setVoice(alarmInfo.isWilling_music());
                                mFileRepositoty.getMusic(alarmInfo.getMusic(), new
                                        ReposityCallback<File>() {

                                            @Override
                                            public void callSuccess(File data) {
                                                mView.setMusicFile(data);
                                                mView.setMusic(alarmInfo.getMusic().getFilename());
                                            }

                                            @Override
                                            public void callFailure(String errorMessage) {

                                            }
                                        });
                            });
                }

                @Override
                public void callFailure(String errorMessage) {

                }
            });
        }
    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    @Override
    public void finish() {
        if (CheckUtils.isEmpty(mView.getEditTitle())) {
            mView.setEditTitleError();
        } else if (CheckUtils.isEmpty(mView.getNestName())) {
            mView.setNestError();
        } else if (CheckUtils.isEmpty(mView.getMusic())) {
            mView.setMusicError();
        } else {
            File file = new File(mView.getMusicUri());
            mFileRepositoty.postMusic(file.getName(), file, new
                    ReposityCallback<PostFileResponse>() {
                        @Override
                        public void callSuccess(PostFileResponse data) {
                            AlarmInfo alarmInfo = new AlarmInfo();
                            alarmInfo.setHour(Integer.valueOf(mView.getSeletedHour()));
                            alarmInfo.setMinute(Integer.valueOf(mView.getSeletedMinute()));
                            alarmInfo.setRepeat(mView.getSeletedWeek());
                            alarmInfo.setTitle(mView.getEditTitle());
                            alarmInfo.setWilling_text(mView.getRemindText());
                            alarmInfo.setWilling_music(mView.getVoice());
                            alarmInfo.setNap(mView.getSnap());
                            alarmInfo.setBind_to_nest(mView.getNestName());
                            alarmInfo.setVolume(mView.getVolume());
                            alarmInfo.setVibrate(mView.isVibrate());
                            alarmInfo.setMusic(data);
                            mAlarmRepoitory.addAlarm(alarmInfo, new ReposityCallback<AddResponse>
                                    () {
                                @Override
                                public void callSuccess(AddResponse data) {
                                    UserRepository userRepository = UserRepository.getInstance();
                                    userRepository.login(Utils.getUsername(), Utils.getPassword(), new NetWorkBoundUtils.CallBack<UserInfo>() {
                                        @Override
                                        public void callSuccess(Observable<UserInfo> result) {
                                            result.subscribeOn(Schedulers.io())
                                                    .doOnNext(userInfo -> {
                                                        List<String> alarms = userInfo.getAlarm_clocks();
                                                        if (alarms == null) {
                                                            alarms = new ArrayList<>();
                                                        }
                                                        List<String> temp = new ArrayList<>();
                                                        temp.addAll(alarms);
                                                        temp.add(data.getObjectId());
                                                        userInfo.setAlarm_clocks(temp);
                                                    })
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(userInfo -> {
                                                        userRepository.changeUserInfo
                                                                (userInfo, new
                                                                        ReposityCallback<UpdateInfo>() {
                                                                            @Override
                                                                            public void
                                                                            callSuccess
                                                                                    (UpdateInfo data) {
                                                                                mView.showMyToast("添加成功");
                                                                                mView.finishActivity();
                                                                            }

                                                                            @Override
                                                                            public void
                                                                            callFailure
                                                                                    (String errorMessage) {
                                                                                Log.d("@victor", "user change rror " + errorMessage);
                                                                            }
                                                                        });
                                                    },throwable -> {});
                                        }

                                        @Override
                                        public void callFailure(String errorMessage) {

                                        }
                                    });

                                }

                                @Override
                                public void callFailure(String errorMessage) {
                                    Log.d("@victor", "alarm error " + errorMessage);
                                }
                            });
                        }

                        @Override
                        public void callFailure(String errorMessage) {
                            Log.d("@victor", "file error " + errorMessage);
                        }
                    });


        }

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
