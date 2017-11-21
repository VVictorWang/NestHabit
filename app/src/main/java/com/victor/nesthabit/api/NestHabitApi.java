package com.victor.nesthabit.api;

import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.bean.AlarmInfo;
import com.victor.nesthabit.bean.Constants;
import com.victor.nesthabit.bean.DakaResponse;
import com.victor.nesthabit.bean.DateOfNest;
import com.victor.nesthabit.bean.JoinedNests;
import com.victor.nesthabit.bean.MessageList;
import com.victor.nesthabit.bean.MsgResponse;
import com.victor.nesthabit.bean.MusicInfo;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.PostFileResponse;
import com.victor.nesthabit.bean.RegisterResponse;
import com.victor.nesthabit.bean.SendMessageResponse;
import com.victor.nesthabit.bean.UpdateInfo;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.util.safe.Base64Cipher;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NestHabitApi {
    public static NestHabitApi instance;
    private NestHabitApiService mApiService;

    public NestHabitApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        mApiService = retrofit.create(NestHabitApiService.class);
    }

    public static NestHabitApi getInstance() {
        if (instance == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(12, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);
            builder.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("X-Bmob-Application-Id", Constants.BMOB_APPLICATION_ID)
                        .addHeader("X-Bmob-REST-API-Key", Constants.BMOB_REST_API_KEY);
                return chain.proceed(requestBuilder.build());
            });
            instance = new NestHabitApi(builder.build());
        }
        return instance;
    }

    //user api
    public Observable<Response<RegisterResponse>> register(String username, String password) {
        return mApiService.register(JsonRequestBody.getJsonRegister(username, password));
    }

    public Observable<Response<UserInfo>> login(String username, String paswword) {
        return mApiService.login(username, Base64Cipher.encrypt(paswword));
    }


    public Observable<Response<UpdateInfo>> changeUserInfo(UserInfo userInfo) {
        return mApiService.editUserInfo(userInfo.getObjectId(), userInfo.getSessionToken(),
                JsonRequestBody.getUserInfo(userInfo));
    }

    public Observable<MsgResponse> logout(String username, String authorization) {
        return mApiService.logout(username, authorization);
    }


    //nest api
    public Observable<Response<AddResponse>> addNest(NestInfo nestInfo) {
        return mApiService.addNest(JsonRequestBody.getAddNest(nestInfo));
    }

    public Observable<Response<NestInfo>> getNestInfo(String id) {
        return mApiService.getNestInfo(id);
    }

    public Observable<MsgResponse> deleteNest(String id) {
        return mApiService.deleteNest(id);
    }

    public Observable<JoinedNests> enterNest(String username, String[] nests, String
            header) {
        return mApiService.enterNest(username, JsonRequestBody.getNest(nests), header);
    }


    public Observable<NestInfo> deleteMember(String nestId, String membername, String
            header) {
        return mApiService.deleteMember(nestId, membername, header);
    }


    public Observable<JoinedNests> quitNset(String username, String[] nests, String
            header) {
        return mApiService.quitNest(username, JsonRequestBody.getNest(nests), header);
    }


    //alarm api
    public Observable<AlarmInfo> addAlarm(AlarmInfo alarmInfo) {
        return mApiService.addAlarm(JsonRequestBody.getAlarm(alarmInfo));
    }

    public Observable<AlarmInfo> changeAlarm(AlarmInfo alarmInfo) {
        return mApiService.changeAlarm(alarmInfo.getObjectId(), JsonRequestBody.getAlarm
                (alarmInfo));
    }

    public Observable<Response<AlarmInfo>> getAlarmInfo(String alarmId) {
        return mApiService.getAlarmInfo(alarmId);
    }

    public Observable<MsgResponse> deleteAlarm(String id, String header) {
        return mApiService.deleteAlarm(id, header);
    }


    public Observable<DateOfNest> getDateOfNest(String username, String nestid, String header) {
        return mApiService.getDateOfNest(username, nestid, header);
    }

    public Observable<DakaResponse> daka(String nestid, String comment, long time, String header) {
        return mApiService.daka(JsonRequestBody.getTargetNest(nestid, comment, time), header);
    }

    public Observable<MusicInfo> getMusicName(String musicid, String header) {
        return mApiService.getMusicName(musicid, header);
    }

    public Observable<PostFileResponse> postMusic(String fileName, File music) {
        return mApiService.postMusic(fileName, JsonRequestBody.getFile(music));
    }

    public Observable<SendMessageResponse> sendMessage(String message, long time, String nestid,
                                                       String
                                                               header) {
        return mApiService.sendMessage(JsonRequestBody.getCommunicationItem(message, time, nestid),
                header);
    }

    public Observable<MessageList> getMessageList(String nestid, String header) {
        return mApiService.getMessageList(nestid, header);
    }
}
