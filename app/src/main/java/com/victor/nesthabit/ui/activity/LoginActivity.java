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
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.PrefsUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

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
                UserApi userApi = UserApi.getInstance();
                Observable<retrofit2.Response<LoginResponse>> response = userApi.login("test",
                        "12345");
                response.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<Response<LoginResponse>>() {
                            @Override
                            public void accept(@NonNull Response<LoginResponse>
                                                       loginResponseResponse) throws Exception {
                                Log.d(TAG, "code" + loginResponseResponse.code());
                                if (loginResponseResponse.code() == 200) {
                                    PrefsUtils.putValue(LoginActivity.this, GlobalData
                                            .AUTHORIZATION, loginResponseResponse.body()
                                            .getAuthorization());
                                    PrefsUtils.putValue(LoginActivity.this, GlobalData.USERNAME,
                                            "test");
                                }
                            }
                        })
                        .subscribe(new Observer<retrofit2.Response<LoginResponse>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull retrofit2.Response<LoginResponse>
                                                       loginResponseResponse) {
                                if (loginResponseResponse.code() == 200) {
                                    ActivityManager.startActivity(LoginActivity.this, MainActivity
                                            .class);
                                }

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        OkHttpClient client = new OkHttpClient();
//                                        Request request = new Request.Builder().url(GlobalData
// .BASE_URL +
//                                                "user/swwwe/info").header("Authorization",
// loginResponseResponse.body().getAuthorization()).get().build();
//                                        try {
//
//                                            Response response = client.newCall(request).execute();
//                                            InputStream inputStream = response.body()
// .byteStream();
//                                            ByteArrayOutputStream outputStream = new
// ByteArrayOutputStream();
//                                            byte[] data = new byte[1028];
//                                            int count = -1;
//                                            while ((count = inputStream.read(data, 0, 1028)) !=
// -1) {
//                                                outputStream.write(data, 0, count);
//                                            }
//                                            data = null;
//                                            Log.d(TAG, "code: " + response.code());
//                                            Log.d(TAG, "data: " + new String(outputStream
// .toByteArray()));
//
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                }).start();
//
//                            }


//
            }
        });
        loginweichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApi api = UserApi.getInstance();
                Observable<retrofit2.Response<RegisterResponse>> responseObservable = api
                        .register("test", "12345");
                responseObservable.subscribeOn(Schedulers.newThread())
                        .subscribe(new Observer<retrofit2.Response<RegisterResponse>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull retrofit2.Response<RegisterResponse>
                                                       registerResponseResponse) {
                                Log.d(TAG, registerResponseResponse.code() + "");


                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

    @Override
    protected Activity getActivity() {
        return LoginActivity.this;
    }
}
