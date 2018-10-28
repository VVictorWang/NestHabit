package com.victor.nesthabit.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.bean.PunchInfo;
import com.victor.nesthabit.db.NestHabitDataBase;
import com.victor.nesthabit.db.PunchDao;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import java.io.IOException;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author victor
 * @date 11/22/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class PunchRepository {

    private final NestHabitApi mNestHabitApi;
    private final PunchDao mPunchDao;

    private PunchRepository(NestHabitApi nestHabitApi, PunchDao punchDao) {
        mNestHabitApi = nestHabitApi;
        mPunchDao = punchDao;
    }

    private static PunchRepository instance;

    public static PunchRepository getInstance() {
        if (instance == null) {
            instance = new PunchRepository(NestHabitApi.getInstance(), NestHabitDataBase
                    .getInstance(AppUtils.getAppContext()).punchDao());
        }
        return instance;
    }

    public void punch(PunchInfo punchInfo, ReposityCallback<AddResponse> callback) {
        mNestHabitApi.punch(punchInfo).subscribeOn(Schedulers.io())
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

    public void getPunchInfo(String punchId, NetWorkBoundUtils.CallBack<PunchInfo> callBack) {
        new NetWorkBoundUtils<PunchInfo, PunchInfo>(callBack) {
            @Override
            protected void saveCallResult(@NonNull PunchInfo item) {
                mPunchDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable PunchInfo data) {
                return true;
            }

            @NonNull
            @Override
            protected Observable<PunchInfo> loadFromDb() {
                return Observable.just(mPunchDao.loadPunch(punchId));
            }

            @NonNull
            @Override
            protected Observable<Response<PunchInfo>> createCall() {
                return mNestHabitApi.getPunchInfo(punchId);
            }
        };
    }
}
