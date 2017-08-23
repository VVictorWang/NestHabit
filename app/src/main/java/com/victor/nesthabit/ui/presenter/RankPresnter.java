package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.data.UserInfo;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.RankContract;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 8/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RankPresnter extends RxPresenter implements RankContract.Presenter {

    private RankContract.View mView;

    public static final String TAG = "@victor RankPresnter";

    public RankPresnter(RankContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        String nestId = mView.getNestId();
        if (nestId != null) {
            Log.d(TAG, nestId);
            String key = Utils.createAcacheKey("get_nestinfo", nestId);
            Observable<NestInfo> observable = UserApi.getInstance().getNestInfo(nestId, Utils
                    .getHeader())
                    .compose(RxUtil.<NestInfo>rxCacheListHelper(key));
            Subscription subscription = Observable.concat(RxUtil.rxCreateDiskObservable(key,
                    NestInfo.class), observable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<NestInfo, List<UserInfo>>() {
                        @Override
                        public List<UserInfo> call(NestInfo nestInfo) {
                            Log.d(TAG, "mapping");
                            return nestInfo.members;
                        }
                    })
                    .subscribe(new Observer<List<UserInfo>>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "observe completd");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "observe failure");
                        }

                        @Override
                        public void onNext(List<UserInfo> userInfos) {
                            Observable.from(userInfos).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map(new Func1<UserInfo, String>() {
                                        @Override
                                        public String call(UserInfo userInfo) {
                                            return userInfo.username;
                                        }
                                    })
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.d(TAG, "error");
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            Log.d(TAG, s);
                                        }
                                    });
                        }
                    });
            addSubscribe(subscription);
        }

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }
}
