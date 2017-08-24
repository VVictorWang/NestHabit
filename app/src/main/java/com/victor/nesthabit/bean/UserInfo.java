package com.victor.nesthabit.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class UserInfo extends DataSupport{

    /**
     * username : swwwe
     * joined_nests : []
     * uploaded_musics : []
     * alarm_clocks : []
     * avatar :
     * nickname :
     */

    public long id;
    public String username;
    public String avatar;
    public String nickname;
    public List<Nests> joined_nests;
    public List<String> uploaded_musics;
    public List<String> alarm_clocks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Nests> getJoined_nests() {
        return joined_nests;
    }

    public void setJoined_nests(List<Nests> joined_nests) {
        this.joined_nests = joined_nests;
    }

    public List<String> getUploaded_musics() {
        return uploaded_musics;
    }

    public void setUploaded_musics(List<String> uploaded_musics) {
        this.uploaded_musics = uploaded_musics;
    }

    public List<String> getAlarm_clocks() {
        return alarm_clocks;
    }

    public void setAlarm_clocks(List<String> alarm_clocks) {
        this.alarm_clocks = alarm_clocks;
    }
}
