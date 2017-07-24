package com.victor.nesthabit.api;

import com.victor.nesthabit.data.AddNestResponse;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.JoinedNests;
import com.victor.nesthabit.data.LoginResponse;
import com.victor.nesthabit.data.MsgResponse;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.data.RegisterResponse;
import com.victor.nesthabit.data.UserInfo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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

    public static UserApi getInstance() {
        if (instance == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(12, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);
            instance = new UserApi(builder.build());
        }
        return instance;
    }

    public Observable<Response<RegisterResponse>> register(String username, String password) {
        return mApiService.register(JsonRequestBody.getJsonRegister(username, password));
    }

    public Observable<Response<LoginResponse>> login(String username, String paswword) {
        return mApiService.login(username, JsonRequestBody.getJsonLogin(paswword));
    }

    public Observable<Response<MsgResponse>> logout(String username, String authorization) {
        return mApiService.logout(username, authorization);
    }

    public Observable<Response<UserInfo>> getUserInfo(String username, String authorization) {
        return mApiService.getUserInfor(username, authorization);
    }

    public Observable<Response<UserInfo>> changeNickname(String nickname, String header) {
        return mApiService.changeNickname(JsonRequestBody.getNickname(nickname), header);
    }

    public Observable<Response<AddNestResponse>> addNest(String name, String desc, int
            member_limit, long start_time, int days, boolean isOpen, String header) {
        return mApiService.addNest(JsonRequestBody.getAddNest(name, desc, member_limit,
                start_time, days, isOpen), header);
    }

    public Observable<Response<MsgResponse>> deleteNest(String id, String header) {
        return mApiService.deleteNest(id, header);
    }

    public Observable<Response<NestInfo>> changeNest(String id, RequestBody body, String header) {
        return mApiService.changeNest(id, body, header);
    }

    public Observable<Response<NestInfo>> deleteMember(String nestId, String membername, String
            header) {
        return mApiService.deleteMember(nestId, membername, header);
    }

    public Observable<Response<JoinedNests>> enterNest(String username, String[] nests, String
            header) {
        return mApiService.enterNest(username, JsonRequestBody.getNest(nests), header);
    }

    public Observable<Response<JoinedNests>> quitNset(String username, String[] nests, String
            header) {
        return mApiService.quitNest(username, JsonRequestBody.getNest(nests), header);
    }

    public Observable<Response<JoinedNests>> getNestList(String username, String header) {
        return mApiService.getNestList(username, header);
    }


}
