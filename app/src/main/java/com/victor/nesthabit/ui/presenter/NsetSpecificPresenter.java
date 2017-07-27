package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.MyNestInfo;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.ui.contract.NestSpecificContract;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.PrefsUtils;
import com.victor.nesthabit.util.Utils;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NsetSpecificPresenter implements NestSpecificContract.Presenter{
    private NestSpecificContract.View mView;
    public static final String TAG = "@victor NsetSpecific";

    public NsetSpecificPresenter(NestSpecificContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        String id = mView.getNestId();
        if (id != null) {
            Observable<Response<NestInfo>> observable = UserApi.getInstance().getNestInfo(id,
                    Utils.getHeader());
            observable.subscribeOn(Schedulers.newThread())
                    .subscribe(nestInfoResponse -> {
                        Log.d(TAG, "code nest:" + nestInfoResponse.code());
                        if (nestInfoResponse.code() == 200) {
                            List<MyNestInfo> nestInfos = DataSupport.findAll(MyNestInfo.class);
                            MyNestInfo info = null;
                            for (MyNestInfo nestInfo : nestInfos) {
                                if (nestInfo.getMyid().equals(nestInfoResponse.body().get_id())) {
                                    info = nestInfo;
                                }
                            }
                            mView.setId(info.getId());
                        }
                    });
        }
    }

    @Override
    public void checkin() {
        mView.setTotalday(mView.getTotalday() + 1);
        mView.setConstantDay(mView.getConstantDay() + 1);
    }

}
