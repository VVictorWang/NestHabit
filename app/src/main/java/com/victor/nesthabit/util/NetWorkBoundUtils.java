package com.victor.nesthabit.util;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

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

public abstract class NetWorkBoundUtils<ResultType, RequestType> {

//    private Observable<ResultType> result = null;

    public interface CallBack<ResultType> {
        void callSuccess(Observable<ResultType> result);

        void callFailure(String errorMessage);
    }

    public NetWorkBoundUtils(CallBack callBack) {

        Observable.just(1).subscribeOn(Schedulers.io())
                .subscribe(integer -> {
                    loadFromDb().subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe(resultType -> {
                                if (shouldFetch(resultType)) {
                                    fetchFromNetwork(callBack);
                                } else
                                    callBack.callSuccess(loadFromDb());
                            });
                });
    }


    private void fetchFromNetwork(CallBack callBack) {
        Observable<Response<RequestType>> apiResponse = createCall();
        apiResponse.subscribeOn(Schedulers.io())
                .doOnNext(requestTypeApiResponse -> {
                    if (requestTypeApiResponse.isSuccessful()) {
                        saveCallResult(processResponse(requestTypeApiResponse));
                        callBack.callSuccess(loadFromDb());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(requestTypeApiResponse -> {
                    if (!requestTypeApiResponse.isSuccessful()) {
                        onFetchFailed();
                        try {
                            callBack.callFailure(requestTypeApiResponse.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    protected void onFetchFailed() {
    }

//    public Observable<ResultType> getResult() {
//        return result;
//    }

    protected RequestType processResponse(Response<RequestType> response) {
        return response.body();
    }

    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    protected abstract Observable<ResultType> loadFromDb();

    @NonNull
    protected abstract Observable<Response<RequestType>> createCall();
}

