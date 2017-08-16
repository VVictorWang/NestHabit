package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface LoginContract {
    interface View extends Baseview<Presenter> {
        void switchToMain();
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);
    }
}
