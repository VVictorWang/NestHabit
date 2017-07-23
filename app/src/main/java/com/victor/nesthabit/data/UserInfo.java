package com.victor.nesthabit.data;

import java.util.List;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class UserInfo {

    /**
     * username : swwwe
     * joined_nests : []
     * uploaded_musics : []
     * alarm_clocks : []
     * avatar :
     * nickname :
     */

    private String username;
    private String avatar;
    private String nickname;
    private List<Nests> joined_nests;
    private List<String> uploaded_musics;
    private List<String> alarm_clocks;

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

    public List<?> getJoined_nests() {
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
