package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.RankContract;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 8/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RankPresnter extends RxPresenter implements RankContract.Presenter {

    public static final String TAG = "@victor RankPresnter";
    private RankContract.View mView;

    private UserRepository mUserRepository;
    private NestRepository mNestRepository;

    public RankPresnter(RankContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mNestRepository = NestRepository.getInstance();
        mUserRepository = UserRepository.getInstance();
    }

    @Override
    public void start() {
        String nestId = mView.getNestId();
        if (nestId != null) {
            mNestRepository.loadNestInfo(nestId, new NetWorkBoundUtils.CallBack<NestInfo>() {
                @Override
                public void callSuccess(Observable<NestInfo> result) {
                    result.subscribeOn(Schedulers.io())
                            .map(nestInfo -> nestInfo.getMembers())
                            .subscribe(membersBeans ->{
                                for (NestInfo.MembersBean bean : membersBeans) {
                                    mUserRepository.getUserInfo(bean.getUserId(), new NetWorkBoundUtils.CallBack<UserInfo>() {

                                        @Override
                                        public void callSuccess(Observable<UserInfo> result) {

                                        }

                                        @Override
                                        public void callFailure(String errorMessage) {

                                        }
                                    });
                                }
                            });
                }

                @Override
                public void callFailure(String errorMessage) {

                }
            });
        }
//        if (nestId != null) {
//            Log.d(TAG, nestId);
//            String key = Utils.createAcacheKey("get_nestinfo", nestId);
//            Observable<NestInfo> observable = NestHabitApi.getInstance().getNestInfo(nestId, Utils
//                    .getHeader())
//                    .compose(RxUtil.<NestInfo>rxCacheListHelper(key));
//            Subscription subscription = Observable.concat(RxUtil.rxCreateDiskObservable(key,
//                    NestInfo.class), observable)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .map(new Func1<NestInfo, List<UserInfo>>() {
//                        @Override
//                        public List<UserInfo> call(NestInfo nestInfo) {
//                            Log.d(TAG, "mapping");
//                            return nestInfo.members;
//                        }
//                    })
//                    .map(new Func1<List<UserInfo>, Void>() {
//                        @Override
//                        public Void call(List<UserInfo> userInfos) {
//                            Observable.from(userInfos).subscribeOn(Schedulers.io())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .map(new Func1<UserInfo, String>() {
//                                        @Override
//                                        public String call(UserInfo userInfo) {
//
//
//                                            Log.d(TAG, "members: " + userInfo.getUsername());
//                                            return userInfo.getUsername();
//                                        }
//                                    })
//                                    .doOnNext(new Action1<String>() {
//                                        @Override
//                                        public void call(String s) {
//                                            String datekey = Utils.createAcacheKey
//                                                    ("get_nest_days", nestId);
//                                            Observable<DateOfNest> ofNestObservable = NestHabitApi
//                                                    .getInstance().getDateOfNest(s, nestId, Utils
//                                                            .getHeader()).compose(RxUtil
//                                                            .<DateOfNest>rxCacheListHelper
//                                                                    (datekey));
//
//                                            RankItem rankItem = new RankItem();
//                                            rankItem.setName(s);
//                                            Subscription subscription1 = Observable.concat(RxUtil
//                                                    .rxCreateDiskObservable(datekey,
//                                                            DateOfNest.class), ofNestObservable)
//                                                    .observeOn(AndroidSchedulers.mainThread())
//                                                    .subscribe(new Observer<DateOfNest>() {
//                                                        @Override
//                                                        public void onCompleted() {
//                                                            mView.addItem(rankItem);
//                                                        }
//
//                                                        @Override
//                                                        public void onError(Throwable e) {
//
//                                                        }
//
//                                                        @Override
//                                                        public void onNext(DateOfNest
// dateOfNest) {
//                                                            int type = mView.getType();
//                                                            if (type == RankTotalFragment
//                                                                    .TOTAL_TYPE) {
//                                                                rankItem.setDays(dateOfNest.days
//                                                                        .size());
//                                                            } else
//                                                                rankItem.setDays(DateUtils
//                                                                        .getConstantDays
//                                                                                (DateUtils
//
// .formatStrings(Utils.getDays(dateOfNest))));
//                                                        }
//                                                    });
//                                            addSubscribe(subscription1);
//                                        }
//                                    })
//                                    .subscribe();
//                            return null;
//                        }
//                    })
//                    .subscribe();
//            addSubscribe(subscription);
//        }

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }
}
