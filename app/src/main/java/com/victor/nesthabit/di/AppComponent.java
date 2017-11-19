package com.victor.nesthabit.di;

import com.victor.nesthabit.MyApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author victor
 * @date 11/18/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MyApplication myApplication);
}
