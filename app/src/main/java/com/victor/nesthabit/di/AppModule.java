package com.victor.nesthabit.di;

import com.victor.nesthabit.MyApplication;
import com.victor.nesthabit.api.NestHabitApi;
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
    NestHabitDataBase provideNestHabitDataBase(MyApplication myApplication) {
        return NestHabitDataBase.getInstance(myApplication);
    }

    @Provides
    @Singleton
    MyApplication provideMyApplication() {
        return MyApplication.getInstance();
    }

    @Provides
    @Singleton
    NestHabitApi provideNestApi() {
        return NestHabitApi.getInstance();
    }
}
