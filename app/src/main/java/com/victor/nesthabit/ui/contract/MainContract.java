package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.bean.AlarmTime;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

import java.util.List;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface MainContract {
    interface View extends Baseview<Presenter> {


        void showProgress();

        void hideProgress();

        void saveUserId(long userid);

        void addAlarmTime(AlarmInfo alarmInfo);

        void addNestInfo(NestInfo nestInfo);


    }

    interface Presenter extends BasePresenter {

    }
}
