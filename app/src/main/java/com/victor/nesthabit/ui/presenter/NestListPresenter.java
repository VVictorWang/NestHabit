package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.JoinedNests;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.data.Nests;
import com.victor.nesthabit.data.UserInfo;
import com.victor.nesthabit.ui.contract.NestListContract;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NestListPresenter implements NestListContract.Presenter, MainPresenter.NestDateBegin {
    private final NestListContract.View mView;
    private List<NestInfo> nestInfos = new ArrayList<>();

    public NestListPresenter(NestListContract.View view) {
        mView = view;
        mView.setPresenter(this);
        MainPresenter.setNestDateBegin(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void begin(long id) {
        UserInfo info = DataSupport.find(UserInfo.class, id);
        List<Nests> nestses = info.getJoined_nests();
        UserApi api = UserApi.getInstance();
        Observable<Response<JoinedNests>> responseObservable = api.getNestList(GlobalData
                .USERNAME, GlobalData.AUTHORIZATION);
        responseObservable.subscribeOn(Schedulers.newThread()).subscribe(joinedNestsResponse -> {
            if (joinedNestsResponse.code() == 200 && nestses != null) {
                nestInfos = joinedNestsResponse.body().getJoined_nests();
                for (NestInfo nestInfo : nestInfos) {
                    for (Nests nests : nestses) {
                        if (nestInfo.get_id().equals(nests.get_id())) {
                            nestInfo.setDay_insist(nests.getKept_days());
                        }
                    }
                    nestInfo.save();
                }
            }
        });
        mView.showRecyclerview(nestInfos);
    }
}
