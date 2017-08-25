package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.bean.JoinedNests;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.NestListContract;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NestListPresenter extends RxPresenter implements NestListContract.Presenter,
        MainPresenter.NestDateBegin {
    public static final String TAG = "@victor NestListPresen";
    private static onNestInfoAdded sOnNestInfoAdded;
    private final NestListContract.View mView;


    public NestListPresenter(NestListContract.View view) {
        mView = view;
        mView.setPresenter(this);
        MainPresenter.setNestDateBegin(this);
    }

    public static void setOnNestInfoAdded(onNestInfoAdded onnestInfoAdded) {
        sOnNestInfoAdded = onnestInfoAdded;
    }

    @Override
    public void start() {

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    @Override
    public void begin(List<String> ids) {
        UserApi api = UserApi.getInstance();
        String key = Utils.createAcacheKey("get_nest_list", "nestid");
        Observable<JoinedNests> responseObservable = api.getNestList(Utils.getUsername(), Utils
                .getHeader()).compose(RxUtil.<JoinedNests>rxCacheListHelper(key));
        Observable observable = Observable.concat(RxUtil.rxCreateDiskObservable(key,
                JoinedNests.class), responseObservable)
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscription = observable
                .subscribe(new Observer<JoinedNests>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JoinedNests joinedNests) {
                        List<NestInfo> nestInfos = joinedNests.getJoined_nests();
                        if (nestInfos != null && !nestInfos.isEmpty() && sOnNestInfoAdded != null) {
                            sOnNestInfoAdded.addNestInfos(nestInfos);
                        }
                    }
                });
        addSubscribe(subscription);

    }

    public interface onNestInfoAdded {
        void addNestInfos(List<NestInfo> nestInfos);
    }
}
