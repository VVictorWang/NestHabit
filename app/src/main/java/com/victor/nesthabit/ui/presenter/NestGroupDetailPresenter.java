package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.ui.contract.NestGroupDetailContract;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NestGroupDetailPresenter implements NestGroupDetailContract.Presenter {
    private NestGroupDetailContract.View mView;

    public NestGroupDetailPresenter(NestGroupDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void unscribe() {

    }
}
