package com.victor.nesthabit.ui.model;

import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

import java.util.List;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface AddAlarmModel {
    interface View extends Baseview<Presenter> {
        String getSeletedHour();

        String getSeletedMinute();

        List<Integer> getSeletedWeek();

        String getEditTitle();

        String getMusic();


        boolean getSnap();

        boolean getVoice();

        boolean getRemindText();

        void finishActivity();

        void setAlarm(AlarmTime alarm);

        void setMusic(String name);
    }

    interface Presenter extends BasePresenter {
        void finish();
    }
}

