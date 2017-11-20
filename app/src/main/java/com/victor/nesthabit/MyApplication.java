package com.victor.nesthabit;

import android.app.Activity;
import android.app.Application;

import com.victor.nesthabit.di.AppInject;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.LogUtils;
import com.victor.nesthabit.util.NetworkUtils;

import org.litepal.LitePal;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class MyApplication extends Application implements HasActivityInjector{
    private static MyApplication sInstance;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppInject.init(this);
        LitePal.initialize(this);
        AppUtils.init(this);
        LogUtils.init(this);
        NetworkUtils.startNetService(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
