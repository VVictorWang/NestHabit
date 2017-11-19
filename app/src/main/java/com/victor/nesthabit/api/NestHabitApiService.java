package com.victor.nesthabit.api;

import com.victor.nesthabit.bean.AddNestResponse;
import com.victor.nesthabit.bean.AlarmResponse;
import com.victor.nesthabit.bean.Constants;
import com.victor.nesthabit.bean.DakaResponse;
import com.victor.nesthabit.bean.DateOfNest;
import com.victor.nesthabit.bean.JoinedNests;
import com.victor.nesthabit.bean.MessageList;
import com.victor.nesthabit.bean.MsgResponse;
import com.victor.nesthabit.bean.MusicInfo;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.NestList;
import com.victor.nesthabit.bean.PostMusicResponse;
import com.victor.nesthabit.bean.RegisterResponse;
import com.victor.nesthabit.bean.SendMessageResponse;
import com.victor.nesthabit.bean.UserInfo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface NestHabitApiService {

    //user api
    @POST("users")
    Observable<ApiResponse<RegisterResponse>> register(@Body RequestBody registerBody);

    @GET("login")
    Observable<ApiResponse<UserInfo>> login(@Query("username") String username, @Query("password") String
            password);

    @PUT("users/{objectId}")
    Observable<UserInfo> editUserInfo(@Path("objectId") String objectId, @Header
            ("X-Bmob-Session-Token") String header, @Body RequestBody body);


    @DELETE("user/{username}/session")
    Observable<MsgResponse> logout(@Path("username") String username, @Header
            (Constants.HEADER_AU) String header);




    //nest api
    @POST("classes/nest")
    Observable<AddNestResponse> addNest(@Body RequestBody body);

    @DELETE("classes/nest/{objectId}")
    Observable<MsgResponse> deleteNest(@Path("objectId") String objectId);

    @POST("classes/nest/{objectId}")
    Observable<NestInfo> changeNest(@Path("objectId") String objectId, @Body RequestBody body);

    @DELETE("nest/{id}/members/{member_username}")
    Observable<NestInfo> deleteMember(@Path("id") String id, @Path("member_username")
            String membername, @Header(Constants.HEADER_AU) String header);

    @POST("user/{username}/joined_nests")
    Observable<JoinedNests> enterNest(@Path("username") String username, @Body
            RequestBody body, @Header(Constants.HEADER_AU) String header);

    @DELETE("user/{username}/joined_nests")
    Observable<JoinedNests> quitNest(@Path("username") String username, @Body
            RequestBody body, @Header(Constants.HEADER_AU) String header);




    @POST("alarm_clock")
    Observable<AlarmResponse> addAlarm(@Body RequestBody body, @Header(Constants.HEADER_AU)
            String header);

    @GET("alarm_clock/{id}")
    Observable<AlarmResponse> getAlarm(@Path("id") String id, @Header(Constants
            .HEADER_AU) String header);

    @DELETE("alarm_clock/{id}")
    Observable<MsgResponse> deleteAlarm(@Path("id") String id, @Header(Constants
            .HEADER_AU) String header);

    @PUT("alarm_clock/{id}")
    Observable<AlarmResponse> changeAlarm(@Path("id") String id, @Body RequestBody body,
                                          @Header(Constants.HEADER_AU) String header);

    @GET("classes/nest")
    Observable<ApiResponse<NestList>> getNestList();

    @GET("classes/nest/{objectId}")
    Observable<ApiResponse<NestInfo>> getNestInfo(@Path("objectId") String objectId);

    @GET("user/{username}/nest/{nest_id}/punches")
    Observable<DateOfNest> getDateOfNest(@Path("username") String username, @Path("nest_id")
            String nestid, @Header(Constants.HEADER_AU) String header);

    @POST("punch")
    Observable<DakaResponse> daka(@Body RequestBody body, @Header(Constants.HEADER_AU) String
            header);

    @GET("music/{id}")
    Observable<MusicInfo> getMusicName(@Path("id") String id, @Header(Constants.HEADER_AU)
            String header);

    @POST("user/{username}/avatar/{name}")
    Observable<UserInfo> postImage(@Path("username") String username, @Path("name")
            String name, @Part MultipartBody.Part file, @Header(Constants.HEADER_AU) String
                                           header);

    @POST("user/{username}/uploaded_musics/{name}")
    Observable<PostMusicResponse> postMusic(@Path("username") String username, @Path("name")
            String name, @Body RequestBody file, @Header(Constants.HEADER_AU) String
                                                    header, @Header("X-Mime-Type") String type);

    @POST("chat_log")
    Observable<SendMessageResponse> sendMessage(@Body RequestBody body, @Header(Constants
            .HEADER_AU) String
            header);

    @GET("nest/{id}/chat_log")
    Observable<MessageList> getMessageList(@Path("id") String id, @Header(Constants.HEADER_AU)
            String header);
}
