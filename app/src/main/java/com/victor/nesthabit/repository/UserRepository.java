package com.victor.nesthabit.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.RegisterResponse;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.db.UserDao;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;
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
                        callback.callSuccess(response.body());
                    } else {
                        try {
                            callback.callFailure(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
    public void login(String name, String password, NetWorkBoundUtils.CallBack<UserInfo> callBack) {
        new NetWorkBoundUtils<UserInfo, UserInfo>(callBack) {
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
            protected Observable<UserInfo> loadFromDb() {
                return Observable.just(mUserDao.loadUser(name));
            }

            @NonNull
            @Override
            protected Observable<Response<UserInfo>> createCall() {
                return mNestHabitApi.login(name, password);
            }
        };
    }

}
