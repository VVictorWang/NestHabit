package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.bean.DateOfNest;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.RankItem;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.RankContract;
import com.victor.nesthabit.ui.fragment.RankTotalFragment;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static rx.Observable.concat;

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
            Subscription subscription = concat(RxUtil.rxCreateDiskObservable(key,
                    NestInfo.class), observable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<NestInfo, List<UserInfo>>() {
                        @Override
                        public List<UserInfo> call(NestInfo nestInfo) {
                            Log.d(TAG, "mapping");
                            return nestInfo.members;
                        }
                    })
                    .map(new Func1<List<UserInfo>, Void>() {
                        @Override
                        public Void call(List<UserInfo> userInfos) {
                            Observable.from(userInfos).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map(new Func1<UserInfo, String>() {
                                        @Override
                                        public String call(UserInfo userInfo) {


                                            Log.d(TAG, "members: " + userInfo.username);
                                            return userInfo.username;
                                        }
                                    })
                                    .doOnNext(new Action1<String>() {
                                        @Override
                                        public void call(String s) {
                                            String datekey = Utils.createAcacheKey
                                                    ("get_nest_days", nestId);
                                            Observable<DateOfNest> ofNestObservable = UserApi
                                                    .getInstance().getDateOfNest(s, nestId, Utils
                                                            .getHeader()).compose(RxUtil
                                                            .<DateOfNest>rxCacheListHelper
                                                                    (datekey));
                                            ;
                                            RankItem rankItem = new RankItem();
                                            rankItem.setName(s);
                                            Subscription subscription1 = Observable.concat(RxUtil
                                                    .rxCreateDiskObservable(datekey,
                                                            DateOfNest.class), ofNestObservable)
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Observer<DateOfNest>() {
                                                        @Override
                                                        public void onCompleted() {
                                                            mView.addItem(rankItem);
                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {

                                                        }

                                                        @Override
                                                        public void onNext(DateOfNest dateOfNest) {
                                                            int type = mView.getType();
                                                            if (type == RankTotalFragment
                                                                    .TOTAL_TYPE) {
                                                                rankItem.setDays(dateOfNest.days
                                                                        .size());
                                                            } else
                                                                rankItem.setDays(DateUtils
                                                                        .getConstantDays
                                                                                (DateUtils
                                                                                        .formatStrings(dateOfNest.days)));
                                                        }
                                                    });
                                            addSubscribe(subscription1);
                                        }
                                    })
                                    .subscribe();
                            return null;
                        }
                    })
                    .subscribe();
            addSubscribe(subscription);
        }

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }
}
