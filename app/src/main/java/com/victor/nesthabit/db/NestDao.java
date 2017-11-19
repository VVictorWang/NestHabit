package com.victor.nesthabit.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.victor.nesthabit.bean.NestInfo;

/**
 * @author victor
 * @date 11/19/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

@Dao
public interface NestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NestInfo nestInfo);

    @Query("select * from nests where objectId = :objectId")
    NestInfo loadNestInfo(String objectId);


}
