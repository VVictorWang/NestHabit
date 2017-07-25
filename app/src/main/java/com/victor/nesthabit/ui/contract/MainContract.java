package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface MainContract {
    interface View extends Baseview<Presenter> {
        void setUpViewPager();

        void setTab();

        void showProgress();

        void hideProgress();

        void saveUserId(long userid);

    }

    interface Presenter extends BasePresenter {

    }
}
