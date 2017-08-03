package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.JoinedNests;
import com.victor.nesthabit.data.MyNestInfo;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.data.Nests;
import com.victor.nesthabit.data.UserInfo;
import com.victor.nesthabit.ui.contract.NestListContract;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.DataCloneUtil;
import com.victor.nesthabit.util.PrefsUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NestListPresenter implements NestListContract.Presenter, MainPresenter.NestDateBegin {
    private final NestListContract.View mView;
    private List<NestInfo> nestInfos = new ArrayList<>();
    public static final String TAG = "@victor NestListPresen";
    private static onNestInfoAdded sOnNestInfoAdded;


    public NestListPresenter(NestListContract.View view) {
        mView = view;
        mView.setPresenter(this);
        MainPresenter.setNestDateBegin(this);
    }

    @Override
    public void start() {
        List<MyNestInfo> mnestInfos = DataSupport.findAll(MyNestInfo.class);
        if (sOnNestInfoAdded != null && !mnestInfos.isEmpty()) {
            sOnNestInfoAdded.addNestInfos(DataCloneUtil.cloneMyNestToNestList(mnestInfos));
        }

    }

    @Override
    public void begin(long id) {
        UserInfo info = DataSupport.find(UserInfo.class, id);
        Log.d(TAG, "begin");
        List<Nests> nestses = info.getJoined_nests();
        UserApi api = UserApi.getInstance();
        Observable<JoinedNests> responseObservable = api.getNestList(PrefsUtils
                .getValue(AppUtils.getAppContext(), GlobalData.USERNAME, "null"), PrefsUtils
                .getValue(AppUtils.getAppContext(), GlobalData.AUTHORIZATION, "null"));
        responseObservable.subscribeOn(Schedulers.newThread()).subscribe(joinedNestsResponse -> {
            if ( nestses != null) {
                nestInfos = joinedNestsResponse.getJoined_nests();
                for (NestInfo nestInfo : nestInfos) {
                    for (Nests nests : nestses) {
                        if (nestInfo.get_id().equals(nests.get_id())) {
                            nestInfo.setDay_insist(nests.getKept_days());
                        }
                    }
                    MyNestInfo myNestInfo = DataCloneUtil.cloneNestToMyNest(nestInfo);
                    myNestInfo.save();
                }
            }
        });
        if (sOnNestInfoAdded != null && !nestInfos.isEmpty()) {
            sOnNestInfoAdded.addNestInfos(nestInfos);
        }
//        mView.showRecyclerview(nestInfos);
    }

    public static void setOnNestInfoAdded(onNestInfoAdded onnestInfoAdded) {
        sOnNestInfoAdded = onnestInfoAdded;
    }

    public interface onNestInfoAdded {
        void addNestInfos(List<NestInfo> nestInfos);
    }
}
