package com.victor.nesthabit.ui.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.AddNestResponse;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.MyNestInfo;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.ui.contract.AddNestContract;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.CheckUtils;
import com.victor.nesthabit.util.DataCloneUtil;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.util.PrefsUtils;

import java.util.function.Consumer;

import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class AddNestPresenter implements AddNestContract.Presenter {
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
