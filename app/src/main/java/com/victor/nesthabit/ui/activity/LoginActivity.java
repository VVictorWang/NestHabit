package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.victor.nesthabit.R;
import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.LoginResponse;
import com.victor.nesthabit.data.RegisterResponse;
import com.victor.nesthabit.ui.base.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    private android.widget.RelativeLayout loginqq;
    private android.widget.RelativeLayout loginweichat;

    public static final String TAG = "@victor LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        this.loginweichat = (RelativeLayout) findViewById(R.id.login_weichat);
        this.loginqq = (RelativeLayout) findViewById(R.id.login_qq);
    }

    @Override
    protected void initEvent() {
        loginqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserApi userApi = new UserApi(new OkHttpClient());
                Observable<retrofit2.Response<LoginResponse>> responseObservable = userApi
                        .getLogin("swwwe", "1234");

                responseObservable.subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .doOnNext(new Consumer<retrofit2.Response<LoginResponse>>() {
                            @Override
                            public void accept(@NonNull retrofit2.Response<LoginResponse>
                                                       loginResponseResponse) throws Exception {
                                Log.d(TAG, "code: " + loginResponseResponse.code());
                                Log.d(TAG, "data: " + loginResponseResponse.body().getMsg());
                            }
                        })
                        .subscribe(new Observer<retrofit2.Response<LoginResponse>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull retrofit2.Response<LoginResponse>
                                                       loginResponseResponse) {
                                Log.d(TAG, loginResponseResponse.code() + "");
                                Log.d(TAG, loginResponseResponse.body().getMsg());
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        OkHttpClient client = new OkHttpClient();
                                        Request request = new Request.Builder().url(GlobalData.BASE_URL +
                                                "user/swwwe/info").header("Authorization",loginResponseResponse.body().getAuthorization()).get().build();
                                        try {

                                            Response response = client.newCall(request).execute();
                                            InputStream inputStream = response.body().byteStream();
                                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                            byte[] data = new byte[1028];
                                            int count = -1;
                                            while ((count = inputStream.read(data, 0, 1028)) != -1) {
                                                outputStream.write(data, 0, count);
                                            }
                                            data = null;
                                            Log.d(TAG, "code: " + response.code());
                                            Log.d(TAG, "data: " + new String(outputStream.toByteArray()));

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });


//
            }
        });
    }

    @Override
    protected Activity getActivityToPush() {
        return LoginActivity.this;
    }
}
