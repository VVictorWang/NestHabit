package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.data.BirdCageInfo;
import com.victor.nesthabit.ui.model.BirdCageModel;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class BirdCagePresenter implements BirdCageModel.Presenter {
    private final BirdCageModel.View mView;
    public BirdCagePresenter(BirdCageModel.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        List<BirdCageInfo> mBirdCageInfos = DataSupport.findAll(BirdCageInfo.class);
        mView.showRecyclerview(mBirdCageInfos);
    }
}
