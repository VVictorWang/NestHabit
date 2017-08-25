package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 8/24/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface ChooseContract {

    interface View extends Baseview<Presenter> {

        String getChooseDate();

        String getNestId();

        void addItem(UserInfo userInfo);

        void finishActivity();


    }

    interface Presenter extends BasePresenter {
        void next();
    }

}
