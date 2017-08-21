package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.victor.nesthabit.R;
import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.contract.LoginContract;
import com.victor.nesthabit.ui.presenter.LoginPresenter;
import com.victor.nesthabit.util.ActivityManager;

public class LoginActivity extends BaseActivity implements LoginContract.View{

    private android.widget.RelativeLayout loginqq;
    private android.widget.RelativeLayout loginweichat;
    private LoginContract.Presenter mLoginPresenter;

    public static final String TAG = "@victor LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter = new LoginPresenter(this);
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
                mLoginPresenter.login("test", "12345");
            }
        });
        loginweichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApi api = UserApi.getInstance();
//                Observable<RegisterResponse> responseObservable = api
//                        .register("test", "12345");
//                responseObservable.observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<RegisterResponse>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.d(TAG, "error");
//                            }
//
//                            @Override
//                            public void onNext(RegisterResponse registerResponse) {
//                                Log.d(TAG, registerResponse.getUsername());
//                            }
//                        });
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        OkHttpClient client = new OkHttpClient();
//                        Request request = new Request.Builder().url(GlobalData
//                                .BASE_URL +
//                                "user").post(JsonRequestBody.getJsonRegister("test", "12345"))
//                                .build();
//                        try {
//                            Response response = client.newCall(request).execute();
//                            InputStream inputStream = response.body()
//                                    .byteStream();
//                            ByteArrayOutputStream outputStream = new
//                                    ByteArrayOutputStream();
//                            byte[] data = new byte[1028];
//                            int count = -1;
//                            while ((count = inputStream.read(data, 0, 1028)) !=
//                                    -1) {
//                                outputStream.write(data, 0, count);
//                            }
//                            data = null;
//                            Log.d(TAG, "code: " + response.code());
//                            Log.d(TAG, "data: " + new String(outputStream
//                                    .toByteArray()));
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }).start();
            }
        });
    }

    @Override
    protected Activity getActivity() {
        return LoginActivity.this;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mLoginPresenter = presenter;
    }

    @Override
    public void switchToMain() {
        ActivityManager.startActivity(LoginActivity.this, MainActivity
                .class);
        ActivityManager.finishActivity(getActivity());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.unscribe();
    }
}
