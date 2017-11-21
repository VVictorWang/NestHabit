package com.victor.nesthabit.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.victor.nesthabit.bean.PunchInfo;

/**
 * @author victor
 * @date 11/22/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

@Dao
public interface PunchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PunchInfo punchInfo);

    @Query("select * from punches where objectId =:objectId")
    PunchInfo loadPunch(String objectId);

}
