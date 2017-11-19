package com.victor.nesthabit.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.ApiResponse;
import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.RegisterResponse;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.db.UserDao;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The type User repository.
 *
 * @author victor
 * @date 11 /18/17
 * @email chengyiwang @hustunique.com
 * @blog www.victorwan.cn #
 */
@Singleton
public class UserRepository {

    private final UserDao mUserDao;
    private final NestHabitApi mNestHabitApi;

    /**
     * Instantiates a new User repository.
     *
     * @param userDao      the user dao
     * @param nestHabitApi the nest habit api
     */
    @Inject
    public UserRepository(UserDao userDao, NestHabitApi nestHabitApi) {
        mUserDao = userDao;
        mNestHabitApi = nestHabitApi;
    }

    /**
     * Register.
     *
     * @param name     the name
     * @param password the password
     * @param callback the callback
     */
    public void register(String name, String password, ReposityCallback<RegisterResponse>
            callback) {
        mNestHabitApi.register(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        callback.callSuccess(response.body);
                    } else {
                        callback.callFailure(response.errorMessage);
                    }
                });
    }

    /**
     * Login observable.
     *
     * @param name     the name
     * @param password the password
     * @return the observable
     */
    public Observable<UserInfo> login(String name, String password) {
        return new NetWorkBoundUtils<UserInfo, UserInfo>() {
            @Override
            protected void saveCallResult(@NonNull UserInfo item) {
                mUserDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable UserInfo data) {
                return data == null && password != null;
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
