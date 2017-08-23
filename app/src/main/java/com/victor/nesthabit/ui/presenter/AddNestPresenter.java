package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.AddNestResponse;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.JoinedNests;
import com.victor.nesthabit.data.MyNestInfo;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.AddNestContract;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.CheckUtils;
import com.victor.nesthabit.util.DataCloneUtil;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.PrefsUtils;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class AddNestPresenter extends RxPresenter implements AddNestContract.Presenter {
    private AddNestContract.View mView;
    public static OnCageDataChanged sOnCageDataChanged;
    public static final String TAG = "@victor AddNestPresen";

    public AddNestPresenter(AddNestContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    @Override
    public void finish() {
        if (CheckUtils.isEmpty(mView.getName())) {
            mView.showNameError();
        } else if (CheckUtils.isEmpty(mView.getDay())) {
            mView.showDayError();
        } else if (mView.IsAmountLimited() && CheckUtils.isEmpty(mView.getAmount())) {
            mView.showAmountError();
        } else {
            MyNestInfo nestInfo = new MyNestInfo();
            nestInfo.setName(mView.getName());
            nestInfo.setDesc(mView.getIntroduction());
            nestInfo.setChallenge_days(Integer.valueOf(mView.getDay()));
            nestInfo.setStart_time(DateUtils.stringToLong(mView.getStartTime()));
            if (mView.IsAmountLimited()) {
                nestInfo.setMembers_limit(Integer.valueOf(mView.getAmount()));
            } else
                nestInfo.setMembers_limit(0);

            Observable<AddNestResponse> responseObservable = UserApi.getInstance()
                    .addNest(nestInfo.getName(), nestInfo.getDesc(), nestInfo.getMembers_limit(),
                            nestInfo.getStart_time(), nestInfo
                                    .getChallenge_days(),
                            false, PrefsUtils.getValue(AppUtils.getAppContext(), GlobalData
                                    .AUTHORIZATION, "null"));
            Subscription subscription = responseObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<AddNestResponse>() {
                        @Override
                        public void onCompleted() {
                            mView.showMyToast("添加成功");
                            if (sOnCageDataChanged != null) {
                                sOnCageDataChanged.OnDataAdded(DataCloneUtil.cloneMynestToNest
                                        (nestInfo));
                                String key = Utils.createAcacheKey("get_nest_list", "nestid");
                                Observable<JoinedNests> responseObservable = UserApi.getInstance
                                        ().getNestList(Utils.getUsername(), Utils
                                        .getHeader()).compose(RxUtil
                                        .<JoinedNests>rxCacheListHelper(key));
                                responseObservable.observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe();
                            }
                            mView.finishActivity();
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.showMyToast("添加失败");
                            mView.finishActivity();
                        }

                        @Override
                        public void onNext(AddNestResponse addNestResponse) {
                            nestInfo.setCreated_time(addNestResponse.getCreated_time());
                            nestInfo.setOwner(addNestResponse.getOwner());
                            nestInfo.setMembers_amount(addNestResponse.getMembers_amount());
                        }
                    });
            addSubscribe(subscription);
//            Log.d(TAG, addNestResponseResponse.code() + " code");
//            if (addNestResponseResponse.code() == 200) {
//                nestInfo.setMyid(addNestResponseResponse.body().get_id());
//                Log.d(TAG, addNestResponseResponse.body().get_id());
//                nestInfo.setCreated_time(addNestResponseResponse.body()
//                        .getCreated_time());
//                nestInfo.setCreator(addNestResponseResponse.body().getCreator());
//                nestInfo.setOwner(addNestResponseResponse.body().getOwner());
//                nestInfo.setMembers_amount(addNestResponseResponse.body()
//                        .getMembers_amount());
//                nestInfo.save();
//            }

//            Log.d(TAG, nestInfo.getMyid());
//            if (sOnCageDataChanged != null) {
//                sOnCageDataChanged.OnDataAdded(DataCloneUtil.cloneMynestToNest
//                        (nestInfo));
//            }
//            mView.finishActivity();
        }
    }

    public static void setOnCageDataChanged(OnCageDataChanged onCageDataChanged) {
        sOnCageDataChanged = onCageDataChanged;
    }

    public interface OnCageDataChanged {
        void OnDataAdded(NestInfo cageInfo);
    }
}
