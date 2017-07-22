package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.data.BirdCageInfo;
import com.victor.nesthabit.ui.contract.AddNestContract;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AddNestPresenter implements AddNestContract.Presenter{
    private AddNestContract.View mView;
    public static OnCageDataChanged sOnCageDataChanged;

    public AddNestPresenter(AddNestContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {
        BirdCageInfo info = new BirdCageInfo();
        info.setInfo(mView.getName());
        info.setIntroduction(mView.getIntroduction());
        info.setDay_total(mView.getDay());
        info.setPeople(mView.getAmount());
        info.setStart_time(mView.getStartTime());
        info.setLimitNumber(mView.IsAmountLimited());
        info.save();
        sOnCageDataChanged.OnDataAdded(info);
        mView.finishActivity();
    }

    public static void setOnCageDataChanged(OnCageDataChanged onCageDataChanged) {
        sOnCageDataChanged = onCageDataChanged;
    }

    public interface OnCageDataChanged {
        void OnDataAdded(BirdCageInfo cageInfo);
    }
}
