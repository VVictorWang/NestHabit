package com.victor.nesthabit.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.RegisterResponse;
import com.victor.nesthabit.bean.UpdateInfo;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.db.NestHabitDataBase;
import com.victor.nesthabit.db.UserDao;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import java.io.IOException;

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


    private UserRepository(Context context) {
        mUserDao = NestHabitDataBase.getInstance(context).userDao();
        mNestHabitApi = NestHabitApi.getInstance();
    }

    private static UserRepository instance;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository(AppUtils.getAppContext());
        }
        return instance;
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

    public void changeUserInfo(UserInfo userInfo, ReposityCallback<UpdateInfo> callback) {
        mNestHabitApi.changeUserInfo(userInfo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateInfoResponse -> {
                    if (updateInfoResponse.isSuccessful()) {
                        callback.callSuccess(updateInfoResponse.body());
                    } else {
                        try {
                            callback.callFailure(updateInfoResponse.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    public void deleteUserInDb(String name) {
        Observable.just(1).subscribeOn(Schedulers.io())
                .subscribe(integer -> mUserDao.deleteUser(mUserDao.loadUser(name)));
    }

    /**
     * Login observable.
     *
     * @param name     the name
     * @param password the password
     * @param callBack the call back
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
                return true;
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

    public void getUserInfo(String objectId, NetWorkBoundUtils.CallBack<UserInfo> callBack) {
        new NetWorkBoundUtils<UserInfo, UserInfo>(callBack) {
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
            protected Observable<UserInfo> loadFromDb() {
                return Observable.just(mUserDao.loadUserById(objectId));
            }

            @NonNull
            @Override
            protected Observable<Response<UserInfo>> createCall() {
                return mNestHabitApi.getUserInfo(objectId);
            }
        };
    }

}
