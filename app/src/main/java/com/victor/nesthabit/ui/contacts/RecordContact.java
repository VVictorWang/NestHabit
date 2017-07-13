package com.victor.nesthabit.ui.contacts;

import com.victor.nesthabit.base.BasePresenter;
import com.victor.nesthabit.base.Baseview;
import com.victor.nesthabit.data.RecordItem;

import java.util.List;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RecordContact {
    public interface View extends Baseview<Presenter> {
        void showRecyclerview(List<RecordItem> mRecordItems);

    }

    public interface Presenter extends BasePresenter {

    }
}
