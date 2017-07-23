package com.victor.nesthabit.api;

import com.victor.nesthabit.data.AddNestResponse;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.LoginResponse;
import com.victor.nesthabit.data.MsgResponse;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.data.RegisterResponse;
import com.victor.nesthabit.data.UserInfo;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class UserApi {
    public static UserApi instance;
    private UserApiService mApiService;

    public UserApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalData.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        mApiService = retrofit.create(UserApiService.class);
    }

    public Observable<Response<RegisterResponse>> getRegister(String username, String password) {
        return mApiService.getRegister(RequestBodyGenerate.getJsonRegister(username, password));
    }

    public Observable<Response<LoginResponse>> getLogin(String username, String paswword) {
        return mApiService.getLogin(username, RequestBodyGenerate.getJsonLogin(paswword));
    }

    public Observable<Response<MsgResponse>> getLogout(String username, String authorization) {
        return mApiService.getLogout(username, authorization);
    }

    public Observable<Response<UserInfo>> getUserInfo(String username, String authorization) {
        return mApiService.getUserInfor(username, authorization);
    }

    public Observable<Response<AddNestResponse>> getAddNset(String name, String desc, int
            member_limit, long
            start_time, int days, boolean isOpen, String header) {
        return mApiService.getAddNset(RequestBodyGenerate.getAddNest(name, desc, member_limit,
                start_time, days, isOpen), header);
    }

    public Observable<Response<MsgResponse>> getDeleteNest(String id, String header) {
        return mApiService.getDeleteNest(id, header);
    }

    public Observable<Response<NestInfo>> deleteMember(String nestId, String membername, String
            header) {
        return mApiService.deleteMember(nestId, membername, header);
    }



}
