package com.victor.nesthabit.ui.presenter;

import android.util.Log;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.UserInfo;
import com.victor.nesthabit.ui.contract.MainContract;
import com.victor.nesthabit.utils.AppUtils;
import com.victor.nesthabit.utils.PrefsUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.victor.nesthabit.view.PickerView.TAG;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    public static final String TAG = "@victor MainPresenter";
    private static NestDateBegin sNestDateBegin;
    private static ClockDataBegin sClockDataBegin;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showProgress();
        UserApi api = UserApi.getInstance();
        Observable<Response<UserInfo>> responseObservable = api.getUserInfo(PrefsUtils.getValue
                        (AppUtils.getAppContext(), GlobalData.USERNAME, "null"),
                PrefsUtils.getValue(AppUtils.getAppContext(), GlobalData.AUTHORIZATION, "null"));
        Log.d(TAG, PrefsUtils.getValue
                (AppUtils.getAppContext(), GlobalData.USERNAME, "null"));
        responseObservable.subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Response<UserInfo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Response<UserInfo> userInfoResponse) {
                        Log.d(TAG, "code: " + userInfoResponse.code());
                        if (userInfoResponse.code() == 200) {
                            userInfoResponse.body().save();
                            mView.saveUserId(userInfoResponse.body().getId());
                            if (sNestDateBegin != null) {
                                sNestDateBegin.begin();
                            }
                            if (sClockDataBegin != null) {
                                sClockDataBegin.begin();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public static void setNestDateBegin(NestDateBegin nestDateBegin) {
        sNestDateBegin = nestDateBegin;
    }

    public static void setClockDataBegin(ClockDataBegin clockDataBegin) {
        sClockDataBegin = clockDataBegin;
    }

    interface ClockDataBegin {
        void begin();
    }


    interface NestDateBegin {
        void begin();
    }

}
