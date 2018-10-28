package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.bean.ChatInfo;
import com.victor.nesthabit.bean.PunchInfo;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface NestSpecificContract {
    interface View extends Baseview<Presenter> {
        int getTotalday();

        void setTotalday(int totalday);

        void addPunchIno(PunchInfo punchInfo);

        void clearPunchInfo();

        void addChatInfo(ChatInfo chatInfo);

        void clearChatInfo();

        int getConstantDay();

        void setConstantDay(int constantDay);

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
