package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.MemberListContract;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static rx.Observable.concat;

/**
 * Created by victor on 8/24/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MemberListPresenter extends RxPresenter implements MemberListContract.Presenter {

    private MemberListContract.View mView;
    public static final String TAG = "@victor MemberPresenter";

    public MemberListPresenter(MemberListContract.View view) {
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
                    .subscribe(new Observer<NestInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(NestInfo nestInfo) {
                            for (UserInfo userInfo : nestInfo.members) {
                                mView.addItem(userInfo);
                            }
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
