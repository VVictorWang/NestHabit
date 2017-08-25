package com.victor.nesthabit.ui.contract;

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


    }
    interface Presenter extends BasePresenter {
    }

}
