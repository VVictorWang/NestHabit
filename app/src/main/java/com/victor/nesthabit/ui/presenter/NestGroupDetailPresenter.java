package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.NestGroupDetailContract;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static rx.Observable.concat;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NestGroupDetailPresenter extends RxPresenter implements NestGroupDetailContract
        .Presenter {
    private NestGroupDetailContract.View mView;
    public static final String TAG = "@victor DetailPresenter";

    public NestGroupDetailPresenter(NestGroupDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        String nestId = mView.getNestid();
        if (nestId != null) {
            Log.d(TAG, nestId);
            String key = Utils.createAcacheKey("get_nestinfo", nestId);
            Observable<NestInfo> observable = UserApi.getInstance().getNestInfo(nestId, Utils
                    .getHeader())
                    .compose(RxUtil.<NestInfo>rxCacheListHelper(key));
            Subscription subscription = concat(RxUtil.rxCreateDiskObservable(key,
                    NestInfo.class), observable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<NestInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(NestInfo nestInfo) {
                            mView.setTitle(nestInfo.name);
                            mView.setDes(nestInfo.desc);
                            if (nestInfo.members_limit == 0) {
                                mView.setLimited(false);
                                mView.setAmountEnabled(false);
                                mView.setMaxAmount(0);
                            } else {
                                mView.setLimited(true);
                                mView.setAmountEnabled(true);
                                mView.setMaxAmount(nestInfo.members_limit);
                            }
                            mView.setStartTime(DateUtils.format(nestInfo.start_time, "yyyy-MM-dd"));
                            mView.setChalengeDay(nestInfo.challenge_days);
                            mView.setOpen(nestInfo.open);
                            mView.setAmount(nestInfo.members_amount);
                        }
                    });
            addSubscribe(subscription);
        }
    }

    @Override
    public void unscribe() {

    }
}
