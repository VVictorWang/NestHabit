package com.victor.nesthabit.api;

import com.victor.nesthabit.data.AddNestResponse;
import com.victor.nesthabit.data.GlobalData;
import com.victor.nesthabit.data.JoinedNests;
import com.victor.nesthabit.data.LoginResponse;
import com.victor.nesthabit.data.MsgResponse;
import com.victor.nesthabit.data.NestInfo;
import com.victor.nesthabit.data.RegisterResponse;
import com.victor.nesthabit.data.UserInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface UserApiService {
    @POST("user/{username}/session")
    Observable<Response<LoginResponse>> login(@Path("username") String username, @Body
            RequestBody
            loginReq);

    @DELETE("user/{username}/session")
    Observable<Response<MsgResponse>> logout(@Path("username") String username, @Header
            (GlobalData.HEADER_AU) String header);


    @GET("user/{username}/info")
    Observable<Response<UserInfo>> getUserInfor(@Path("username") String username, @Header
            (GlobalData.HEADER_AU) String authorization);


    @POST("user")
    Observable<Response<RegisterResponse>> register(@Body RequestBody registerBody);

    @POST("nest")
    Observable<Response<AddNestResponse>> addNest(@Body RequestBody body, @Header(GlobalData
            .HEADER_AU) String header);

    @DELETE("nest/{id}")
    Observable<Response<MsgResponse>> deleteNest(@Path("id") String id, @Header(GlobalData
            .HEADER_AU) String header);

    @DELETE("nest/{id}/members/{member_username}")
    Observable<Response<NestInfo>> deleteMember(@Path("id") String id, @Path("member_username")
            String membername, @Header(GlobalData.HEADER_AU) String header);

    @POST("user/{username}/joined_nests")
    Observable<Response<JoinedNests>> enterNest(@Path("username") String username, @Body
            RequestBody body, @Header(GlobalData.HEADER_AU) String header);

    @DELETE("user/{username}/joined_nests")
    Observable<Response<JoinedNests>> quitNest(@Path("username") String username, @Body
            RequestBody body, @Header(GlobalData.HEADER_AU) String header);

    @GET("user/{username}/joined_nests")
    Observable<Response<JoinedNests>> getNestList(@Path("username") String username, @Header
            (GlobalData.HEADER_AU) String header);
}
