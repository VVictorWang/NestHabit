package com.victor.nesthabit.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.victor.nesthabit.db.StringListConverter;

import java.util.List;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

@Entity(tableName = "users")
@TypeConverters(StringListConverter.class)
public class UserInfo {

    /**
     * alarm_clocks : []
     * avatar :
     * createdAt : 2017-11-18 15:50:42
     * joined_nests : []
     * objectId : e6549a8d01
     * sessionToken : 7494dee940dc02a280e3d8569d9633b7
     * updatedAt : 2017-11-18 15:50:42
     * uploaded_musics : []
     * username : wang
     */

    private String avatar;
    private String createdAt;
    @PrimaryKey
    @NonNull
    private String objectId;
    private String sessionToken;
    private String updatedAt;

    @PrimaryKey
    @NonNull
    private String username;
    private List<String> alarm_clocks;
    private List<String> joined_nests;
    private List<String> uploaded_musics;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getAlarm_clocks() {
        return alarm_clocks;
    }

    public void setAlarm_clocks(List<String> alarm_clocks) {
        this.alarm_clocks = alarm_clocks;
    }

    public List<String> getJoined_nests() {
        return joined_nests;
    }

    public void setJoined_nests(List<String> joined_nests) {
        this.joined_nests = joined_nests;
    }

    public List<String> getUploaded_musics() {
        return uploaded_musics;
    }

    public void setUploaded_musics(List<String> uploaded_musics) {
        this.uploaded_musics = uploaded_musics;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"avatar\" : " + avatar + ",\n" + "\"username\" : " + username + ",\n" +
                "\"alarm_clocks\" : [");
        int i = 0;
        if (alarm_clocks != null) {
            for (String alarmid : alarm_clocks) {
                i++;
                builder.append(alarmid);
                if (i != alarm_clocks.size() - 1) {
                    builder.append(",");
                }
            }
        }

        builder.append("],\n" + "\"joined_nests\" : [");
        if (joined_nests != null) {
            i = 0;
            for (String nestId : joined_nests) {
                i++;
                builder.append(nestId);
                if (i != alarm_clocks.size() - 1) {
                    builder.append(",");
                }
            }
        }
        builder.append("],\n" + "\"uploaded_musics\" : [");
        if (uploaded_musics != null) {
            i = 0;
            for (String musicId : uploaded_musics) {
                i++;
                builder.append(musicId);
                if (i != alarm_clocks.size() - 1) {
                    builder.append(",");
                }
            }
        }

        builder.append("]\n}");
        return builder.toString();
    }
}
