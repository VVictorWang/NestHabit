package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.repository.ReposityCallback;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface AddNestContract {
    interface View extends Baseview<Presenter> {
        String getName();

        void showNameError();

        String getIntroduction();

        String getDay();

        void showDayError();

        String getStartTime();

        void setStartTime(String date);

        boolean IsAmountLimited();

        boolean isOPen();

        String getAmount();

        void showAmountError();

        void finishActivity();

    }

    interface Presenter extends BasePresenter {
        void finish(ReposityCallback<AddResponse> result);

        void notifyNestAdded(String nestId);
    }
}
