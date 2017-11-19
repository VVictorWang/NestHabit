package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.NestHabitApi;
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
    public static final String TAG = "@victor DetailPresenter";
    private NestGroupDetailContract.View mView;

    public NestGroupDetailPresenter(NestGroupDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        String nestId = mView.getNestid();
        Log.d(TAG, "nestId" + nestId);
        if (nestId != null) {
            String key = Utils.createAcacheKey("get_nestinfo", nestId);
            Observable<NestInfo> observable = NestHabitApi.getInstance().getNestInfo(nestId, Utils
                    .getHeader())
                    .compose(RxUtil.<NestInfo>rxCacheBeanHelper(key));
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
                            mView.setStartTime(DateUtils.format(nestInfo.start_time, "yyyy-MM-dd"));
                            mView.setChalengeDay(nestInfo.challenge_days);
                            mView.setAmount(nestInfo.members_amount);
                            if (mView.isOwner()) {
                                if (nestInfo.members_limit == 0) {
                                    mView.setLimited(false);
                                    mView.setAmountEnabled(false);
                                    mView.setMaxAmount(0);
                                } else {
                                    mView.setLimited(true);
                                    mView.setAmountEnabled(true);
                                    mView.setMaxAmount(nestInfo.members_limit);
                                }
                                mView.setOpen(nestInfo.open);
                            } else
                                mView.setMaxAmount(nestInfo.members_limit);
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
