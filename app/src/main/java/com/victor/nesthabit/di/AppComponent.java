package com.victor.nesthabit.di;

import android.app.Application;

import com.victor.nesthabit.MyApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * @author victor
 * @date 11/18/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AppModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MyApplication myApplication);
}
