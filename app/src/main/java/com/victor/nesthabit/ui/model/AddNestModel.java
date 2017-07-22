package com.victor.nesthabit.ui.model;

import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface AddNestModel {
    interface View extends Baseview<Presenter> {
        String getName();

        String getIntroduction();

        int getDay();

        String getStartTime();

        boolean IsAmountLimited();

        int getAmount();

        void finishActivity();

    }

    interface Presenter extends BasePresenter {
        void finish();
    }
}
