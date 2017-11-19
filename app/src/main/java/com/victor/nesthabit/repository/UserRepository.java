package com.victor.nesthabit.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.ApiResponse;
import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.db.UserDao;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author victor
 * @date 11/18/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

@Singleton
public class UserRepository {

    private final UserDao mUserDao;
    private final NestHabitApi mNestHabitApi;

    @Inject
    public UserRepository(UserDao userDao, NestHabitApi nestHabitApi) {
        mUserDao = userDao;
        mNestHabitApi = nestHabitApi;
    }

    public void register(String name, String password, ReposityCallback callback) {
        mNestHabitApi.register(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        callback.call(response.body);
                    } else {
                        callback.call(response.errorMessage);
                    }
                });
    }

    public Observable<UserInfo> login(String name, String password) {
        return new NetWorkBoundUtils<UserInfo, UserInfo>() {
            @Override
            protected void saveCallResult(@NonNull UserInfo item) {
                mUserDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable UserInfo data) {
                return data == null;
            }

            @NonNull
            @Override
            protected UserInfo loadFromDb() {
                return mUserDao.loadUser(name);
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<UserInfo>> createCall() {
                return mNestHabitApi.login(name, password);
            }
        }.getResult();
    }


}
