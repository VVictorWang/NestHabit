package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.ChooseContract;

/**
 * Created by victor on 8/24/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class ChoosePresenter extends RxPresenter implements ChooseContract.Presenter {

    private ChooseContract.View mView;

    public ChoosePresenter(ChooseContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }
}
