package com.victor.nesthabit.api;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class JsonRequestBody {

    private static MediaType sMediaType = MediaType.parse("application/json");

    public static RequestBody getJsonRegister(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(sMediaType, jsonObject.toString());
    }

    public static RequestBody getJsonLogin(String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            jsonObject.put("client_id", "android_client_87542701");
            jsonObject.put("client_secret", "385trd4m");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(sMediaType, jsonObject.toString());
    }

    public static RequestBody getAddNest(String name, String desc, int member_limit, long
            start_time, int days, boolean isOpen) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("desc", desc);
            jsonObject.put("members_limit", member_limit);
            jsonObject.put("start_time", start_time);
            jsonObject.put("challenge_days", days);
            jsonObject.put("open", isOpen);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(sMediaType, jsonObject.toString());
    }

    public static RequestBody getNickname(String nickname, String header) {
        return RequestBody.create(sMediaType, header);
    }

    public static RequestBody getNest(String[] id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nests", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(sMediaType, jsonObject.toString());
    }


}
