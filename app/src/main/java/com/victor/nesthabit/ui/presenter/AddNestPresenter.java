package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.ReposityCallback;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.AddNestContract;
import com.victor.nesthabit.util.CheckUtils;
import com.victor.nesthabit.util.DateUtils;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class AddNestPresenter extends RxPresenter implements AddNestContract.Presenter {
    public static final String TAG = "@victor AddNestPresen";
    private AddNestContract.View mView;

    private NestRepository mNestRepository;

    private static NotifyNestAdded sNotifyNestAdded;

    public AddNestPresenter(AddNestContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mNestRepository = NestRepository.getInstance();

    }


    @Override
    public void start() {

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    @Override
    public void finish(ReposityCallback<AddResponse> result) {
        if (CheckUtils.isEmpty(mView.getName())) {
            mView.showNameError();
        } else if (CheckUtils.isEmpty(mView.getDay())) {
            mView.showDayError();
        } else if (mView.IsAmountLimited() && CheckUtils.isEmpty(mView.getAmount())) {
            mView.showAmountError();
        } else {
            NestInfo nestInfo = new NestInfo();
            nestInfo.setName(mView.getName());
            nestInfo.setDesc(mView.getIntroduction());
            nestInfo.setChallenge_days(Integer.valueOf(mView.getDay()));
            nestInfo.setStart_time(DateUtils.stringToLong(mView.getStartTime()));
            nestInfo.setMembers_limit(mView.IsAmountLimited() ? Integer.valueOf(mView.getAmount()
            ) : 0);
            nestInfo.setOpen(mView.isOPen());
            mNestRepository.addNest(nestInfo, result);
        }
    }

    @Override
    public void notifyNestAdded(String nestId) {
        sNotifyNestAdded.notifyNestAdded(nestId);
    }

    public static void setNotifyNestAdded(NotifyNestAdded notifyNestAdded) {
        sNotifyNestAdded = notifyNestAdded;
    }

    public interface NotifyNestAdded{
        void notifyNestAdded(String nestId);
    }

}
