package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface NestSpecificContract {
    interface View extends Baseview<Presenter> {
        void setTotalday(int totalday);

        int getTotalday();

        void setConstantDay(int constantDay);

        int getConstantDay();

        String getNestId();

        void setId(long id);

        void setToolbar(String title);

        void setMaxProgress(float progress);

        void setTotalProgress(float progress);

        void setConstantProgresss(float progresss);
    }

    interface Presenter extends BasePresenter {
        void checkin();
    }
}
