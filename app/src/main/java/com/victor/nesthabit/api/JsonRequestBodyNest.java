package com.victor.nesthabit.api;

import com.victor.nesthabit.data.UserInfo;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.PUT;

/**
 * Created by victor on 7/24/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class JsonRequestBodyNest {
    private static final MediaType sMediaType = MediaType.parse("application/json");
    private JSONObject mJSONObject;

    public JsonRequestBodyNest() {
        mJSONObject = new JSONObject();
    }

    public JsonRequestBodyNest setName(String name) {
        try {
            mJSONObject.put("name", name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyNest setDesc(String desc) {
        try {
            mJSONObject.put("desc", desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyNest setMemberlimit(int members_limit) {
        try {
            mJSONObject.put("members_limit", members_limit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyNest setStarttime(long starttime) {
        try {
            mJSONObject.put("start_time", starttime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyNest setChalengeday(int challenge_days) {
        try {
            mJSONObject.put("challenge_days", challenge_days);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyNest setOpen(boolean open) {
        try {
            mJSONObject.put("open", open);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBodyNest setMemberAmount(int memberAmount) {
        try {
            mJSONObject.put("members_amount", memberAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public RequestBody create() {
        return RequestBody.create(sMediaType, mJSONObject.toString());
    }
}
