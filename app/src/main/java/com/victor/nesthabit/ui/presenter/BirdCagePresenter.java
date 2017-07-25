package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.JoinedNests;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.data.Nests;
import com.victor.nesthabit.data.UserInfo;
import com.victor.nesthabit.ui.contract.BirdCageContract;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class BirdCagePresenter implements BirdCageContract.Presenter {
    private final BirdCageContract.View mView;
    private List<NestInfo> nestInfos = new ArrayList<>();

    public BirdCagePresenter(BirdCageContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

        UserInfo info = DataSupport.find(UserInfo.class, mView.getUserId());
        List<Nests> nestses = info.getJoined_nests();

        UserApi api = UserApi.getInstance();
        Observable<Response<JoinedNests>> responseObservable = api.getNestList(GlobalData
                .USERNAME, GlobalData.AUTHORIZATION);
        responseObservable.subscribeOn(Schedulers.newThread());


        mView.showRecyclerview(nestInfos);
    }
}
