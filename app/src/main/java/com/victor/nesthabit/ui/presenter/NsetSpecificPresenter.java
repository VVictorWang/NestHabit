package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.DakaResponse;
import com.victor.nesthabit.data.DateOfNest;
import com.victor.nesthabit.data.MyNestInfo;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.NestSpecificContract;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NsetSpecificPresenter extends RxPresenter implements NestSpecificContract.Presenter {
    private NestSpecificContract.View mView;
    public static final String TAG = "@victor NsetSpecific";
    private MyNestInfo mMyNestInfo;

    public NsetSpecificPresenter(NestSpecificContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        String id = mView.getNestId();
        if (id != null) {
            String key = Utils.createAcacheKey("get_nestinfo", id);
            Observable<NestInfo> observable = UserApi.getInstance().getNestInfo(id,
                    Utils.getHeader()).compose(RxUtil.<NestInfo>rxCacheBeanHelper(key));
            Subscription subscription = Observable.concat(RxUtil.rxCreateDiskObservable(key,
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
                            mView.setToolbar(nestInfo.getName());
                            mView.setMaxProgress(nestInfo.getChallenge_days());
                        }
                    });
            addSubscribe(subscription);
            String datekey = Utils.createAcacheKey("get_nest_days", id);
            Observable<DateOfNest> nestObservable = UserApi.getInstance().getDateOfNest(Utils
                    .getUsername(), id, Utils.getHeader()).compose(RxUtil
                    .<DateOfNest>rxCacheListHelper(datekey));
            Subscription datesub = Observable.concat(RxUtil.rxCreateDiskObservable(datekey,
                    DateOfNest.class), nestObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DateOfNest>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(DateOfNest dateOfNest) {
                            List<String> days = dateOfNest.getDays();
                            if (days.isEmpty()) {
                                mView.setTotalday(0);
                                mView.setTotalProgress(0);
                                mView.setConstantProgresss(0);
                                mView.setConstantDay(0);
                            }
                            List<Date> daysof = DateUtils.sortDateDesc(DateUtils.formatStrings
                                    (days));
                            if (daysof != null) {
                                mView.setTotalday(daysof.size());
                                mView.setTotalProgress((float) daysof.size());
                                mView.setConstantDay(DateUtils.getConstantDays(daysof));
                                mView.setConstantProgresss(mView.getConstantDay());
                            }

                        }
                    });
            addSubscribe(datesub);
        }
    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    @Override
    public void checkin() {
        Observable<DakaResponse> dakaResponseObservable = UserApi.getInstance().daka(mView
                .getNestId(), Utils.getHeader());

        mView.setTotalday(mView.getTotalday() + 1);
        mView.setConstantDay(mView.getConstantDay() + 1);
        mView.setTotalProgress(mView.getTotalday());
        mView.setConstantProgresss(mView.getConstantDay());
    }

}
