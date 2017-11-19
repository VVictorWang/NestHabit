package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 8/24/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface MemberListContract {

    interface View extends Baseview<Presenter> {
        String getNestId();


        void addItem(UserInfo userInfo);
    }

    interface Presenter extends BasePresenter {
    }

}
