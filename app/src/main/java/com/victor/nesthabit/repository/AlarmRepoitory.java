package com.victor.nesthabit.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.bean.MsgResponse;
import com.victor.nesthabit.db.AlarmDao;
import com.victor.nesthabit.db.NestHabitDataBase;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.Utils;

import java.io.IOException;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author victor
 * @date 11/19/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class AlarmRepoitory {

    private final NestHabitApi mNestHabitApi;
    private final AlarmDao mAlarmDao;

    private AlarmRepoitory(Context context) {
        mNestHabitApi = NestHabitApi.getInstance();
        mAlarmDao = NestHabitDataBase.getInstance(context).alarmDao();
    }

    private static AlarmRepoitory instance;

    public static AlarmRepoitory getInstance() {
        if (instance == null) {
            instance = new AlarmRepoitory(AppUtils.getAppContext());
        }
        return instance;
    }

    public void loadAlarmInfo(String alarmId, NetWorkBoundUtils.CallBack<AlarmInfo> callBack) {
        new NetWorkBoundUtils<AlarmInfo, AlarmInfo>(callBack) {
            @Override
            protected void saveCallResult(@NonNull AlarmInfo item) {
                mAlarmDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable AlarmInfo data) {
                return data == null;
            }

            @NonNull
            @Override
            protected Observable<AlarmInfo> loadFromDb() {
                return Observable.just(mAlarmDao.loadAlarm(alarmId));
            }

            @NonNull
            @Override
            protected Observable<Response<AlarmInfo>> createCall() {
                return mNestHabitApi.getAlarmInfo(alarmId);
            }
        };
    }

    public void addAlarm(AlarmInfo alarmInfo, ReposityCallback<AddResponse> callback) {
        mNestHabitApi.addAlarm(alarmInfo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(alarmInfo1 -> {
                    if (alarmInfo1.isSuccessful()) {
                        callback.callSuccess(alarmInfo1.body());
                    } else {
                        try {
                            callback.callFailure(alarmInfo1.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public Observable<MsgResponse> deleteAlarm(String alarmId) {
        return mNestHabitApi.deleteAlarm(alarmId, Utils.getHeader())
                .subscribeOn(Schedulers.io())
                .map(new Func1<MsgResponse, MsgResponse>() {
                    @Override
                    public MsgResponse call(MsgResponse msgResponse) {
                        mAlarmDao.deleteAlarm(alarmId);
                        return msgResponse;
                    }
                });
    }
}
