package com.victor.nesthabit.contacts;

import com.victor.nesthabit.base.BasePresenter;
import com.victor.nesthabit.base.Baseview;
import com.victor.nesthabit.data.BirdCageInfo;

import java.util.List;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class BirdCageContact {
    public interface View extends Baseview<Presenter> {
        void showRecyclerview(List<BirdCageInfo> mBirdCageInfos);
    }

    public interface Presenter extends BasePresenter {
    }

}
