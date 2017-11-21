package com.victor.nesthabit.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.bean.ChatInfo;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.PunchInfo;
import com.victor.nesthabit.bean.UserInfo;

/**
 * @author victor
 * @date 11/18/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

@Database(entities = {UserInfo.class, NestInfo.class, AlarmInfo.class, PunchInfo.class, ChatInfo
        .class}, version = 1)
public abstract class NestHabitDataBase extends RoomDatabase {
    abstract public UserDao userDao();

    abstract public NestDao nestDao();

    abstract public AlarmDao alarmDao();

    private static NestHabitDataBase instance;

    public static NestHabitDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NestHabitDataBase
                    .class, "nestHabit3.db").addMigrations(new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                }
            }).build();
        }
        return instance;
    }


}
