package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.JoinedNests;
import com.victor.nesthabit.data.MyNestInfo;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.data.Nests;
import com.victor.nesthabit.data.UserInfo;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.NestListContract;
import com.victor.nesthabit.util.DataCloneUtil;
import com.victor.nesthabit.util.RxUtil;
import com.victor.nesthabit.util.Utils;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static android.R.attr.id;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NestListPresenter extends RxPresenter implements NestListContract.Presenter,
        MainPresenter.NestDateBegin {
    private final NestListContract.View mView;
    public static final String TAG = "@victor NestListPresen";
    private static onNestInfoAdded sOnNestInfoAdded;


    public NestListPresenter(NestListContract.View view) {
        mView = view;
        mView.setPresenter(this);
        MainPresenter.setNestDateBegin(this);
    }

    @Override
    public void start() {
//        List<MyNestInfo> mnestInfos = DataSupport.findAll(MyNestInfo.class);
//        if (sOnNestInfoAdded != null && !mnestInfos.isEmpty()) {
//            sOnNestInfoAdded.addNestInfos(DataCloneUtil.cloneMyNestToNestList(mnestInfos));
//        }

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


    public static void setOnNestInfoAdded(onNestInfoAdded onnestInfoAdded) {
        sOnNestInfoAdded = onnestInfoAdded;
    }

    public interface onNestInfoAdded {
        void addNestInfos(List<NestInfo> nestInfos);
    }
}
