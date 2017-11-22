package com.victor.nesthabit.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.UpdateInfo;
import com.victor.nesthabit.db.NestDao;
import com.victor.nesthabit.db.NestHabitDataBase;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import java.io.IOException;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author victor
 * @date 11/18/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class NestRepository {

    private final NestHabitApi mNestHabitApi;
    private final NestDao mNestDao;


    private NestRepository(Context context) {
        mNestHabitApi = NestHabitApi.getInstance();
        mNestDao = NestHabitDataBase.getInstance(context).nestDao();
    }

    private static NestRepository instance;

    public static NestRepository getInstance() {
        if (instance == null) {
            instance = new NestRepository(AppUtils.getAppContext());
        }
        return instance;
    }

    public void addNest(NestInfo nestInfo, ReposityCallback<AddResponse> callback) {
        mNestHabitApi.addNest(nestInfo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addResponseResponse -> {
                    if (addResponseResponse.isSuccessful()) {
                        callback.callSuccess(addResponseResponse.body());
                    } else {
                        try {
                            callback.callFailure(addResponseResponse.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void deleteNest(String nestId) {
        Observable.just(1).subscribeOn(Schedulers.io())
                .subscribe(integer -> mNestDao.deleteNest(mNestDao.loadNestInfo(nestId)));
    }

    public void loadNestInfo(String objectId, NetWorkBoundUtils.CallBack<NestInfo> callBack) {
        new NetWorkBoundUtils<NestInfo, NestInfo>(callBack) {
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
            protected Observable<NestInfo> loadFromDb() {
                return Observable.just(mNestDao.loadNestInfo(objectId));
            }

            @NonNull
            @Override
            protected Observable<Response<NestInfo>> createCall() {
                return mNestHabitApi.getNestInfo(objectId);
            }
        };
    }

    public void changeNestInfo(NestInfo nestInfo, ReposityCallback<UpdateInfo> callback) {
        mNestHabitApi.changeNestInfo(nestInfo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateInfoResponse -> {
                    if (updateInfoResponse.isSuccessful()) {
                        callback.callSuccess(updateInfoResponse.body());
                        deleteNest(nestInfo.getObjectId());
                    } else {
                        try {
                            callback.callFailure(updateInfoResponse.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
