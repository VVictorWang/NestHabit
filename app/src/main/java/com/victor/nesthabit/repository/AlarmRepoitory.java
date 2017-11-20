package com.victor.nesthabit.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.db.AlarmDao;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author victor
 * @date 11/19/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class AlarmRepoitory {

    private final NestHabitApi mNestHabitApi;
    private final AlarmDao mAlarmDao;

    @Inject
    public AlarmRepoitory(NestHabitApi nestHabitApi, AlarmDao alarmDao) {
        mNestHabitApi = nestHabitApi;
        mAlarmDao = alarmDao;
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
}
