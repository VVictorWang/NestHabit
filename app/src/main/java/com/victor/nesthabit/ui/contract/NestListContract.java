package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

import java.util.List;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface NestListContract {
    interface View extends Baseview<Presenter> {
        void showRecyclerview(List<NestInfo> mBirdCageInfos);

        long getUserId();
    }

    interface Presenter extends BasePresenter {
    }

}
