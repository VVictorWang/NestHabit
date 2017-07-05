package com.victor.nesthabit.listenners;

import com.victor.nesthabit.data.RecordItem;

/**
 * Created by victor on 7/5/17.
 */

public interface OnNewRecordListenner {
    void onNewRecordAdded(long id);

    void onNewRecordAddedtoDataBase(RecordItem item);

    void onRecordDeleted(RecordItem item);
}
