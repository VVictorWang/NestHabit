package com.victor.nesthabit.bean;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RegisterResponse {


    /**
     * createdAt : 2017-11-18 14:53:12
     * objectId : ba5e7ac1b9
     * sessionToken : 595db7464016c2988000dabadc139065
     */

    private String createdAt;
    private String objectId;
    private String sessionToken;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
