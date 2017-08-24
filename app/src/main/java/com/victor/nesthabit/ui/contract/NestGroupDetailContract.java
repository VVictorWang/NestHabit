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

        void setDes(String des);

        String getNestid();

        void setTitle(String titleString);

        int getChalengeDay();

        void setChalengeDay(int days);

        String getStartTime();

        void setStartTime(String date);

        void setAmount(int amount);

        void setAmountEnabled(boolean enabled);

        boolean isLimited();

        void setLimited(boolean limited);

        boolean isOpen();

        void setOpen(boolean open);

        void setMaxAmount(int amounts);
    }

    interface Presenter extends BasePresenter {

    }
}
