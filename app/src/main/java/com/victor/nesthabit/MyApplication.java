package com.victor.nesthabit;

import android.app.Application;
import android.util.Log;

import com.victor.nesthabit.utils.AppUtils;
import com.victor.nesthabit.utils.LogUtils;
import com.victor.nesthabit.utils.NetworkUtils;

import org.litepal.LitePal;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        AppUtils.init(this);
        LogUtils.init(this);
    }
}
