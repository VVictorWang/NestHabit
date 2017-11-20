package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.bean.AlarmTime;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.ClockListContract;
import com.victor.nesthabit.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class ClockListPresenter extends RxPresenter implements ClockListContract.Presenter {
    public static final String TAG = "@victor ClockListPrese";
    private ClockListContract.View mView;

    public ClockListPresenter(ClockListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void start() {
    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

}
