package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

import java.util.List;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface ClockListContract {
    interface View extends Baseview<Presenter> {
        void showRecyclerView(List<AlarmTime> time);
    }

    interface Presenter extends BasePresenter {

    }
}
