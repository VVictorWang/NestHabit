package com.victor.nesthabit.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.ApiResponse;
import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.db.NestDao;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author victor
 * @date 11/18/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class NestRepository {

    private final NestHabitApi mNestHabitApi;
    private final NestDao mNestDao;

    @Inject
    public NestRepository(NestHabitApi nestHabitApi, NestDao nestDao) {
        mNestHabitApi = nestHabitApi;
        mNestDao = nestDao;
    }

    public Observable<NestInfo> loadNestInfo(String objectId) {
        return new NetWorkBoundUtils<NestInfo, NestInfo>() {
            @Override
            protected void saveCallResult(@NonNull NestInfo item) {
                mNestDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable NestInfo data) {
                return data == null;
            }

            @NonNull
            @Override
            protected NestInfo loadFromDb() {
                return mNestDao.loadNestInfo(objectId);
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<NestInfo>> createCall() {
                return mNestHabitApi.getNestInfo(objectId);
            }
        }.getResult();
    }
}
