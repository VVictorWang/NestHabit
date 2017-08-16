package com.victor.nesthabit.api;

import com.victor.nesthabit.data.AddNestResponse;
import com.victor.nesthabit.data.AlarmResponse;
import com.victor.nesthabit.data.DakaResponse;
import com.victor.nesthabit.data.DateOfNest;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.JoinedNests;
import com.victor.nesthabit.data.LoginResponse;
import com.victor.nesthabit.data.MsgResponse;
import com.victor.nesthabit.data.MusicInfo;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.data.RegisterResponse;
import com.victor.nesthabit.data.UserInfo;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface UserApiService {
    @POST("user/{username}/session")
    Observable<LoginResponse> login(@Path("username") String username, @Body
            RequestBody loginReq);

    @DELETE("user/{username}/session")
    Observable<MsgResponse> logout(@Path("username") String username, @Header
            (GlobalData.HEADER_AU) String header);


    @GET("user/{username}/info")
    Observable<UserInfo> getUserInfor(@Path("username") String username, @Header
            (GlobalData.HEADER_AU) String authorization);


    @POST("user")
    Observable<UserInfo> changeNickname(@Body RequestBody body, @Header(GlobalData
            .HEADER_AU) String header);

    @POST("user")
    Observable<RegisterResponse> register(@Body RequestBody registerBody);

    @POST("nest")
    Observable<AddNestResponse> addNest(@Body RequestBody body, @Header(GlobalData
            .HEADER_AU) String header);

    @DELETE("nest/{id}")
    Observable<MsgResponse> deleteNest(@Path("id") String id, @Header(GlobalData
            .HEADER_AU) String header);

    @POST("nest/{id}")
    Observable<NestInfo> changeNest(@Path("id") String id, @Body RequestBody body,
                                              @Header(GlobalData.HEADER_AU) String header);

    @DELETE("nest/{id}/members/{member_username}")
    Observable<NestInfo> deleteMember(@Path("id") String id, @Path("member_username")
            String membername, @Header(GlobalData.HEADER_AU) String header);

    @POST("user/{username}/joined_nests")
    Observable<JoinedNests> enterNest(@Path("username") String username, @Body
            RequestBody body, @Header(GlobalData.HEADER_AU) String header);

    @DELETE("user/{username}/joined_nests")
    Observable<JoinedNests> quitNest(@Path("username") String username, @Body
            RequestBody body, @Header(GlobalData.HEADER_AU) String header);

    @GET("user/{username}/joined_nests")
    Observable<JoinedNests> getNestList(@Path("username") String username, @Header
            (GlobalData.HEADER_AU) String header);


    @POST("nest/{id}/members/{member_username}")
    Observable<AlarmResponse> addAlarm(@Path("id") String id, @Path("member_username")
            String username, @Body RequestBody body, @Header(GlobalData.HEADER_AU) String header);

    @GET("alarm_clock/{id}")
    Observable<AlarmResponse> getAlarm(@Path("id") String id, @Header(GlobalData
            .HEADER_AU) String header);

    @DELETE("alarm_clock/{id}")
    Observable<MsgResponse> deleteAlarm(@Path("id") String id, @Header(GlobalData
            .HEADER_AU) String header);

    @POST("alarm_clock/{id}")
    Observable<NestInfo> changeAlarm(@Path("id") String id, @Body RequestBody body,
                                               @Header(GlobalData.HEADER_AU) String header);

    @GET("nest/{id}?list_members=1")
    Observable<NestInfo> getNestInfo(@Path("id") String id, @Header(GlobalData
            .HEADER_AU) String header);

    @GET("user/{username}/nest/{nest_id}/punches")
    Observable<DateOfNest> getDateOfNest(@Path("username") String username, @Path("nest_id")
            String nestid,@Header(GlobalData.HEADER_AU) String header);

    @POST("punch")
    Observable<DakaResponse> daka(@Body RequestBody body, @Header(GlobalData.HEADER_AU) String
            header);

    @GET("alarm_clock/{id}")
    Observable<MusicInfo> getMusicName(@Path("id") String id, @Header(GlobalData.HEADER_AU)
            String header);
}
