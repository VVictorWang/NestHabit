package com.victor.nesthabit.api;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by victor on 7/25/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class JsonRequestBodyAlarm {
    private static final MediaType sMediaType = MediaType.parse("application/json");
    private JSONObject mJSONObject;

    public JsonRequestBodyAlarm() {
        mJSONObject = new JSONObject();
    }

    public JsonRequestBodyAlarm setTitle(String title) {
        try {
            mJSONObject.put("title", title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyAlarm setDuration_level(int duration_level) {
        try {
            mJSONObject.put("duration_level", duration_level);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyAlarm setNap_level(int nap_level) {
        try {
            mJSONObject.put("nap_level", nap_level);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyAlarm setVolume_level(int volume_level) {
        try {
            mJSONObject.put("volume_level", volume_level);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyAlarm setBind_to_nest(String bind_to_nest) {
        try {
            mJSONObject.put("bind_to_nest", bind_to_nest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyAlarm setWilling_music(boolean willing_music) {
        try {
            mJSONObject.put("willing_music", willing_music);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyAlarm setWilling_text(boolean willing_text) {
        try {
            mJSONObject.put("willing_text", willing_text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyAlarm setTime(List<Integer> time) {
        try {
            mJSONObject.put("time", time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyAlarm setRepeat(List<Integer> repeat) {
        try {
            mJSONObject.put("repeat", repeat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    public RequestBody create() {
        return RequestBody.create(sMediaType, mJSONObject.toString());
    }
}
