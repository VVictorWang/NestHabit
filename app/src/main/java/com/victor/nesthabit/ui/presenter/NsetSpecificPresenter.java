package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.ui.contract.NestSpecificContract;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NsetSpecificPresenter implements NestSpecificContract.Presenter{
    private NestSpecificContract.View mView;

    public NsetSpecificPresenter(NestSpecificContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void checkin() {
        mView.setTotalday(mView.getTotalday() + 1);
        mView.setConstantDay(mView.getConstantDay() + 1);
    }

}
