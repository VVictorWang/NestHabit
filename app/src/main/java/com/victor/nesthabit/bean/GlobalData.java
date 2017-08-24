package com.victor.nesthabit.bean;

import com.victor.nesthabit.util.AppUtils;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public interface GlobalData {
    public static final String APP_ID_WECHAT = "wx69ea2f76fd79e402";
    public static final String BASE_URL = "http://nesthabit.far-away.cn/api/v1/";
    public static final String HEADER_AU = "Authorization";

    public static final String UPLOAD_MUSIC = "uploaded_musics";

    public static final String UPLOAD_AVATAR = "avatar";

    public static final String AUTHORIZATION = "Login_Authorization";

    public static final String USERNAME = "username";

    public static final String USER_ID = "User_id";
    public static final String PATH_DATA = AppUtils.getAppContext().getExternalCacheDir().getPath();
}
