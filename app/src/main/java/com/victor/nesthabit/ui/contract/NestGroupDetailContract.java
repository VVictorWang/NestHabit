package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface NestGroupDetailContract {
    interface View extends Baseview<Presenter> {
        String getIntroduction();

        int getChalengeDay();

        String getStartTime();

        void setStartTime(String date);

        void setAmount(int amount);

        boolean isLimited();

        boolean isOpen();

        void setMaxAmount(int amount);
    }

    interface Presenter extends BasePresenter {

    }
}
