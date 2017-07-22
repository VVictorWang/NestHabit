package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 7/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface AddRemindContract {
    interface View extends Baseview<Presenter> {
        void startService();

        void stopService();

        void startChrometor();

        void stopChrometor();

        void setPauseImage();

        void setPlayImage();

        void updateTime(Runnable runnable);

        void removeCallbacks(Runnable runnable);

        void setRecordText(String text);

        void hidePlayButtonshowRecord();

        void hideRecordButtonshowPlay();

        void hideChormetorShowText();

        void hideTextShowChrometor();

        void clearWindowFlags();

        void addWindowFlags();

        String getRemindText();

        void finishActivity();
    }

    interface Presenter extends BasePresenter {
        void onRecord(boolean start);


        void finish();
        void Play();

        void recordAgain();
    }
}
