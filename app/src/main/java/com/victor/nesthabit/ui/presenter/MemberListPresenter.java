package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.MemberListContract;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 8/24/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MemberListPresenter extends RxPresenter implements MemberListContract.Presenter {

    public static final String TAG = "@victor MemberPresenter";
    private MemberListContract.View mView;

    public MemberListPresenter(MemberListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        String nestId = mView.getNestId();
        if (nestId != null) {
            Log.d(TAG, nestId);
            NestRepository.getInstance().loadNestInfo(nestId, new NetWorkBoundUtils.CallBack<NestInfo>() {
                @Override
                public void callSuccess(Observable<NestInfo> result) {
                    result.subscribeOn(Schedulers.io())
                            .map(nestInfo -> {
                                for (NestInfo.MembersBean bean : nestInfo.getMembers()) {
                                    UserRepository.getInstance().getUserInfo(bean.getUserId(), new NetWorkBoundUtils.CallBack<UserInfo>() {
                                        @Override
                                        public void callSuccess(Observable<UserInfo> result1) {
                                            result1.observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(userInfo -> {
                                                        mView.addItem(userInfo);
                                                    }, throwable -> {
                                                    });
                                        }

                                        @Override
                                        public void callFailure(String errorMessage) {

                                        }
                                    });
                                }
                                return true;
                            }).subscribe();
                }

                @Override
                public void callFailure(String errorMessage) {

                }
            });

        }
    }

    @Override
    public void unscribe() {
        unSubscribe();
    }
}
