package com.victor.nesthabit.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.victor.nesthabit.bean.AlarmInfo;

/**
 * @author victor
 * @date 11/19/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

@Dao
public interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AlarmInfo alarmInfo);

    @Query("select * from alarms where objectId = :objectId")
    AlarmInfo loadAlarm(String objectId);
}
