package com.victor.nesthabit.api;

import com.victor.nesthabit.bean.AddNestResponse;
import com.victor.nesthabit.bean.AlarmResponse;
import com.victor.nesthabit.bean.DakaResponse;
import com.victor.nesthabit.bean.DateOfNest;
import com.victor.nesthabit.bean.GlobalData;
import com.victor.nesthabit.bean.JoinedNests;
import com.victor.nesthabit.bean.LoginResponse;
import com.victor.nesthabit.bean.MessageList;
import com.victor.nesthabit.bean.MsgResponse;
import com.victor.nesthabit.bean.MusicInfo;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.PostMusicResponse;
import com.victor.nesthabit.bean.RegisterResponse;
import com.victor.nesthabit.bean.SendMessageResponse;
import com.victor.nesthabit.bean.UserInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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

    public Observable<RegisterResponse> register(String username, String password) {
        return mApiService.register(JsonRequestBody.getJsonRegister(username, password));
    }

    public Observable<LoginResponse> login(String username, String paswword) {
        return mApiService.login(username, JsonRequestBody.getJsonLogin(paswword));
    }

    public Observable<MsgResponse> logout(String username, String authorization) {
        return mApiService.logout(username, authorization);
    }

    public Observable<UserInfo> getUserInfo(String username, String authorization) {
        return mApiService.getUserInfor(username, authorization);
    }

    public Observable<UserInfo> changeNickname(String nickname, String header) {
        return mApiService.changeNickname(JsonRequestBody.getNickname(nickname), header);
    }

    public Observable<AddNestResponse> addNest(String name, String desc, int
            member_limit, long start_time, int days, boolean isOpen, String header) {
        return mApiService.addNest(JsonRequestBody.getAddNest(name, desc, member_limit,
                start_time, days, isOpen), header);
    }

    public Observable<NestInfo> getNestInfo(String id, String header) {
        return mApiService.getNestInfo(id, header);
    }

    public Observable<MsgResponse> deleteNest(String id, String header) {
        return mApiService.deleteNest(id, header);
    }

    public Observable<NestInfo> changeNest(String id, RequestBody body, String header) {
        return mApiService.changeNest(id, body, header);
    }

    public Observable<NestInfo> deleteMember(String nestId, String membername, String
            header) {
        return mApiService.deleteMember(nestId, membername, header);
    }

    public Observable<JoinedNests> enterNest(String username, String[] nests, String
            header) {
        return mApiService.enterNest(username, JsonRequestBody.getNest(nests), header);
    }

    public Observable<JoinedNests> quitNset(String username, String[] nests, String
            header) {
        return mApiService.quitNest(username, JsonRequestBody.getNest(nests), header);
    }

    public Observable<JoinedNests> getNestList(String username, String header) {
        return mApiService.getNestList(username, header);
    }

    public Observable<AlarmResponse> addAlarm(String title, List<Integer> time, List<Integer> repeate, String
            music_id, boolean nap, boolean shock, String bind_to_nest,
                                              boolean willing_music, boolean
                                                      willing_text, String
                                                      header) {
        return mApiService.addAlarm(JsonRequestBody.getAlarm(title, time, repeate,
                music_id, nap, shock, bind_to_nest, willing_music,
                willing_text), header);
    }

    public Observable<AlarmResponse> getAlarm(String id, String header) {
        return mApiService.getAlarm(id, header);
    }

    public Observable<MsgResponse> deleteAlarm(String id, String header) {
        return mApiService.deleteAlarm(id, header);
    }

    public Observable<NestInfo> changeAlarm(String id, RequestBody body, String header) {
        return mApiService.changeAlarm(id, body, header);
    }

    public Observable<DateOfNest> getDateOfNest(String username, String nestid, String header) {
        return mApiService.getDateOfNest(username, nestid, header);
    }

    public Observable<DakaResponse> daka(String nestid, String header) {
        return mApiService.daka(JsonRequestBody.getTargetNest(nestid), header);
    }

    public Observable<MusicInfo> getMusicName(String musicid, String header) {
        return mApiService.getMusicName(musicid, header);
    }

    public Observable<PostMusicResponse> postMusic(String username, String name, MultipartBody
            .Part filebody, String header,String type) {
        return mApiService.postMusic(username, name, filebody, header,type);
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
