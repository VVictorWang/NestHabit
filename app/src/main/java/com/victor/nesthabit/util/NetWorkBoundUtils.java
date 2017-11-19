package com.victor.nesthabit.util;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.victor.nesthabit.api.ApiResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author victor
 * @date 11/18/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public abstract class NetWorkBoundUtils<ResultType, RequestType> {

    private Observable<ResultType> result = null;

    @MainThread
    public NetWorkBoundUtils() {
        ResultType dbSource = loadFromDb();
        if (shouldFetch(dbSource)) {
            fetchFromNetwork();
        } else {
            result = Observable.just(dbSource);
        }
    }


    private void fetchFromNetwork() {
        Observable<ApiResponse<RequestType>> apiResponse = createCall();
        apiResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(requestTypeApiResponse -> {
                    if (requestTypeApiResponse.isSuccessful()) {
                        saveCallResult(processResponse(requestTypeApiResponse));
                    }
                })
                .subscribe(requestTypeApiResponse -> {
                    if (requestTypeApiResponse.isSuccessful()) {
                        result = Observable.just(loadFromDb());
                    } else {
                        onFetchFailed();
                        result = null;
                    }
                });
    }


    protected void onFetchFailed() {
    }

    public Observable<ResultType> getResult() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract ResultType loadFromDb();

    @NonNull
    @MainThread
    protected abstract Observable<ApiResponse<RequestType>> createCall();
}

