package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.ui.contract.ClockContract;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class ClockPresenter implements ClockContract.Presenter, MainPresenter.ClockDataBegin {
    private ClockContract.View mView;

    public ClockPresenter(ClockContract.View view) {
        mView = view;
        mView.setPresenter(this);
        MainPresenter.setClockDataBegin(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void begin() {

    }
}
