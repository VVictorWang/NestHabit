package com.victor.nesthabit;

import android.app.Application;

import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.LogUtils;
import com.victor.nesthabit.util.NetworkUtils;

import org.litepal.LitePal;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        LitePal.initialize(this);
        AppUtils.init(this);
        LogUtils.init(this);
        NetworkUtils.startNetService(this);
    }

    public static MyApplication getInstance() {
        return sInstance;
    }
}
