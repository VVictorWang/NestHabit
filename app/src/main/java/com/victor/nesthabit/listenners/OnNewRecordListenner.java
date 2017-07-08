package com.victor.nesthabit.listenners;

import com.victor.nesthabit.data.RecordItem;

/**
 * 监听录音文件的保存、删除
 * Created by victor on 7/5/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public interface OnNewRecordListenner {
    void onNewRecordAdded(RecordItem item); //录音文件已缓存
    void onNewRecordAddedtoDataBase(RecordItem item); //录音文件实际保存
    void onRecordDeleted(RecordItem item); //录音文件删除
}
