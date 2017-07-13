package com.victor.nesthabit.ui.contracts;

import com.victor.nesthabit.base.BasePresenter;
import com.victor.nesthabit.base.Baseview;
import com.victor.nesthabit.data.BirdCageInfo;

import java.util.List;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface BirdCageContract {
    interface View extends Baseview<Presenter> {
        void showRecyclerview(List<BirdCageInfo> mBirdCageInfos);
    }

     interface Presenter extends BasePresenter {
    }

}
