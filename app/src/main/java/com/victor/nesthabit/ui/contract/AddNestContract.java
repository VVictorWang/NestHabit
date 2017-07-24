package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

import java.util.Date;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface AddNestContract {
    interface View extends Baseview<Presenter> {
        String getName();

        String getIntroduction();

        int getDay();

        String getStartTime();

        void setStartTime(String date);

        boolean IsAmountLimited();

        int getAmount();

        void finishActivity();

    }

    interface Presenter extends BasePresenter {
        void finish();
    }
}
