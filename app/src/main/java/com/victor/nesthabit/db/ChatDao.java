package com.victor.nesthabit.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.victor.nesthabit.bean.ChatInfo;
import com.victor.nesthabit.bean.PunchInfo;

/**
 * @author victor
 * @date 11/22/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

@Dao
public interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChatInfo chatInfo);

    @Query("select * from chats where objectId =:objectId")
    PunchInfo loadPunch(String objectId);
}
