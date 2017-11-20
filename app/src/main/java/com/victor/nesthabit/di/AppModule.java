package com.victor.nesthabit.di;

import android.app.Application;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.db.AlarmDao;
import com.victor.nesthabit.db.NestDao;
import com.victor.nesthabit.db.NestHabitDataBase;
import com.victor.nesthabit.db.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author victor
 * @date 11/18/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    UserDao provideUserDao(NestHabitDataBase nestHabitDataBase) {
        return nestHabitDataBase.userDao();
    }

    @Provides
    @Singleton
    AlarmDao provideAlarmDao(NestHabitDataBase nestHabitDataBase) {
        return nestHabitDataBase.alarmDao();
    }


    @Provides
    @Singleton
    NestDao provideNeedDao(NestHabitDataBase nestHabitDataBase) {
        return nestHabitDataBase.nestDao();
    }

    @Provides
    @Singleton
    NestHabitDataBase provideNestHabitDataBase(Application myApplication) {
        return NestHabitDataBase.getInstance(myApplication);
    }


    @Provides
    @Singleton
    NestHabitApi provideNestApi() {
        return NestHabitApi.getInstance();
    }
}
