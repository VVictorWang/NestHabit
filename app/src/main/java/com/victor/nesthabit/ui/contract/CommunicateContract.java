package com.victor.nesthabit.ui.contract;

import com.victor.nesthabit.bean.ChatInfo;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.base.Baseview;

import java.util.List;

/**
 * Created by victor on 8/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface CommunicateContract {
    interface View extends Baseview<Presenter> {
        String getMessage();

        String getNestId();

        void addItem(ChatInfo item);

        void addAll(List<ChatInfo> items);

        void setEditText(String text);

        void showToast(String des);


    }

    interface Presenter extends BasePresenter {
        void sendMessage();
    }

}
