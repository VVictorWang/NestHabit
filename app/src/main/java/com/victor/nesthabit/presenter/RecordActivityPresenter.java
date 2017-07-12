package com.victor.nesthabit.presenter;

import com.victor.nesthabit.contacts.RecordContact;
import com.victor.nesthabit.data.RecordItem;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by victor on 7/12/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RecordActivityPresenter implements RecordContact.Presenter {
    private RecordContact.View mView;

    public RecordActivityPresenter(RecordContact.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

        List<RecordItem> mRecordItems = DataSupport.findAll(RecordItem.class);
        mView.showRecyclerview(mRecordItems);

    }
}
