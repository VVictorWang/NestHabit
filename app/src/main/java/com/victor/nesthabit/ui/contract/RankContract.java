package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.bean.RankItem;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

/**
 * Created by victor on 8/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface RankContract {

    interface View extends Baseview<Presenter> {
        String getNestId();

        int getType();

        void addItem(RankItem rankItem);

    }

    interface Presenter extends BasePresenter {
    }

}
