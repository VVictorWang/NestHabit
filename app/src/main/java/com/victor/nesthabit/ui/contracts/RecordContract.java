package com.victor.nesthabit.ui.contracts;

import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;
import com.victor.nesthabit.data.RecordItem;

import java.util.List;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface RecordContract {
    public interface View extends Baseview<Presenter> {
        void showRecyclerview(List<RecordItem> mRecordItems);

    }

    public interface Presenter extends BasePresenter {

    }
}
