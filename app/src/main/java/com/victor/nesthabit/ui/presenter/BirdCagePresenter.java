package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.data.BirdCageInfo;
import com.victor.nesthabit.ui.contract.BirdCageContract;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class BirdCagePresenter implements BirdCageContract.Presenter {
    private final BirdCageContract.View mView;
    public BirdCagePresenter(BirdCageContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        List<BirdCageInfo> mBirdCageInfos = DataSupport.findAll(BirdCageInfo.class);
        mView.showRecyclerview(mBirdCageInfos);
    }
}
