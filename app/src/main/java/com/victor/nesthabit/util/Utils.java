package com.victor.nesthabit.util;

import android.net.Uri;
import android.os.Environment;

import com.victor.nesthabit.bean.GlobalData;

import java.io.File;

/**
 * Created by victor on 7/26/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class Utils {
    public static String getHeader() {
        return PrefsUtils.getValue(AppUtils.getAppContext(), GlobalData.AUTHORIZATION, "null");
    }

    public static String getUsername() {
        return PrefsUtils.getValue(AppUtils.getAppContext(), GlobalData.USERNAME, "null");
    }

    public static String createAcacheKey(Object... param) {
        String key = "";
        for (Object o : param) {
            key += "-" + o;
        }
        return key.replaceFirst("-", "");
    }

    public static Uri getImageStreamFromExternal(String imageName) {
        File externalPubPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        );

        File picPath = new File(externalPubPath, imageName);
        Uri uri = null;
        if (picPath.exists()) {
            uri = Uri.fromFile(picPath);
        }

        return uri;
    }

}


