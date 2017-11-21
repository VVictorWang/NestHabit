package com.victor.nesthabit.db;

import android.arch.persistence.room.TypeConverter;

import com.victor.nesthabit.bean.PostFileResponse;

/**
 * @author victor
 * @date 11/21/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class PostFileConverter {

    @TypeConverter
    public static String postResponseToString(PostFileResponse response) {
        StringBuilder builder = new StringBuilder();
        builder.append(response.getCdn() + " ");
        builder.append(response.getFilename() + " ");
        builder.append(response.getUrl());
        return builder.toString();
    }

    @TypeConverter
    public static PostFileResponse stringToPostResponse(String postString) {
        PostFileResponse postFileResponse = new PostFileResponse();
        String[] resultStrings = postString.split(" ");
        postFileResponse.setCdn(resultStrings[0]);
        postFileResponse.setFilename(resultStrings[1]);
        postFileResponse.setUrl(resultStrings[2]);
        return postFileResponse;
    }
}
