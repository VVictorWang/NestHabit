package com.victor.nesthabit.util;

import com.victor.nesthabit.data.GlobalData;

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
}
