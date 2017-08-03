package com.victor.nesthabit.ui.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.MyNestInfo;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.ui.contract.NestSpecificContract;
import com.victor.nesthabit.util.Utils;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NsetSpecificPresenter implements NestSpecificContract.Presenter{
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
            Observable<NestInfo> observable = UserApi.getInstance().getNestInfo(id,
                    Utils.getHeader());
//            Log.d(TAG, "code nest:" + nestInfoResponse.code());
//            if (nestInfoResponse.code() == 200) {
//                List<MyNestInfo> nestInfos = DataSupport.findAll(MyNestInfo.class);
//                for (MyNestInfo nestInfo : nestInfos) {
//                    Log.d(TAG, "info id: " + nestInfo.getMyid());
//                    Log.d(TAG, "responce id :" + nestInfoResponse.body().get_id());
//                    if (nestInfo.getMyid().equals(nestInfoResponse.body().get_id())) {
//                        mMyNestInfo = nestInfo;
//                    }
//                }
//                if (mMyNestInfo != null) {
//                    mView.setId(mMyNestInfo.getId());
//                    mView.setToolbar(mMyNestInfo.getName());
//                    mView.setTotalday(mMyNestInfo.getDay_insist());
//                    mView.setMaxProgress(mMyNestInfo.getChallenge_days());
//                    mView.setTotalProgress((mMyNestInfo.getDay_insist() + 20));
//                    mView.setConstantProgresss(mMyNestInfo.getDay_insist() + 10);
//
//                }
//            }
        }
    }

    @Override
    public void checkin() {
        mView.setTotalday(mView.getTotalday() + 1);
        mView.setConstantDay(mView.getConstantDay() + 1);
        mView.setTotalProgress(mView.getTotalday());
        mView.setConstantProgresss(mView.getConstantDay());
    }

}
