package com.victor.nesthabit;

import android.app.Application;

import com.victor.nesthabit.utils.AppUtils;

import org.litepal.LitePal;

/**
 * Created by victor on 7/2/17.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        AppUtils.init(this );
    }
}
