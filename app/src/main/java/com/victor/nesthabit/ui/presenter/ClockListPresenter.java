package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.ui.contract.ClockListContract;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class ClockListPresenter implements ClockListContract.Presenter, MainPresenter.ClockDataBegin {
    private ClockListContract.View mView;

    public ClockListPresenter(ClockListContract.View view) {
        mView = view;
        mView.setPresenter(this);
        MainPresenter.setClockDataBegin(this);
    }

    @Override
    public void start() {

    }

    //当用户信息获取完全才开始获取鸟窝列表
    @Override
    public void begin(long id) {

    }
}
