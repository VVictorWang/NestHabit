package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.data.BirdCageInfo;
import com.victor.nesthabit.ui.contract.AddNestContract;
import com.victor.nesthabit.utils.CheckUtils;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AddNestPresenter implements AddNestContract.Presenter {
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
        if (CheckUtils.isEmpty(mView.getName())) {
            mView.showNameError();
        } else if (CheckUtils.isEmpty(mView.getDay())) {
            mView.showDayError();
        } else if (mView.IsAmountLimited() && CheckUtils.isEmpty(mView.getAmount())) {
            mView.showAmountError();
        } else {
            BirdCageInfo info = new BirdCageInfo();
            info.setInfo(mView.getName());
            info.setIntroduction(mView.getIntroduction());
            info.setDay_total(Integer.valueOf(mView.getDay()));
            info.setStart_time(mView.getStartTime());
            info.setLimitNumber(mView.IsAmountLimited());
            if (mView.IsAmountLimited()) {
                info.setPeople(Integer.valueOf(mView.getAmount()));
            } else
                info.setPeople(1000);
            info.save();
            if (sOnCageDataChanged != null) {
                sOnCageDataChanged.OnDataAdded(info);
            }
            mView.finishActivity();
        }

    }

    public static void setOnCageDataChanged(OnCageDataChanged onCageDataChanged) {
        sOnCageDataChanged = onCageDataChanged;
    }

    public interface OnCageDataChanged {
        void OnDataAdded(BirdCageInfo cageInfo);
    }
}
