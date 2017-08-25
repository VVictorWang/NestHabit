package com.victor.nesthabit.api;

import com.victor.nesthabit.util.safe.Base64Cipher;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class JsonRequestBody {

    private static final MediaType sMediaType = MediaType.parse("application/json");

    public static RequestBody getJsonRegister(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            password = Base64Cipher.encrypt(password);
            jsonObject.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(sMediaType, jsonObject.toString());
    }

    public static RequestBody getJsonLogin(String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            password = Base64Cipher.encrypt(password);
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
            jsonObject.put("start_time", (int) start_time);
            jsonObject.put("challenge_days", days);
            jsonObject.put("open", isOpen);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(sMediaType, jsonObject.toString());
    }

    public static RequestBody getNickname(String nickname) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickname", nickname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(sMediaType, jsonObject.toString());
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

    public static RequestBody getAlarm(String title, int[] time, int[] repeate, String music_id,
                                       boolean nap, boolean shock, String bind_to_nest, boolean
                                               willing_music, boolean willing_text) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("time", time);
            jsonObject.put("repeat", repeate);
            jsonObject.put("music_id", music_id);
            jsonObject.put("nap", nap);
            jsonObject.put("shock", shock);
            jsonObject.put("bind_to_nest", bind_to_nest);
            jsonObject.put("willing_music", willing_music);
            jsonObject.put("willing_text", willing_text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(sMediaType, jsonObject.toString());
    }

    public static RequestBody getTargetNest(String nestid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("target_nest", nestid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(sMediaType, jsonObject.toString());
    }

    public static RequestBody getCommunicationItem(String value, String targetnest) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("value", value);
            jsonObject.put("target_nest", targetnest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestBody.create(sMediaType, jsonObject.toString());
    }

}
