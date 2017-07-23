package com.victor.nesthabit.data;

import java.util.List;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RegisterResponse {

    /**
     * username : aaaaaaaa
     * password : 8412
     * created_time : 1500719922
     * joined_nests : []
     * uploaded_musics : []
     * alarm_clocks : []
     * avatar :
     */


    private String username;
    private String password;
    private int created_time;
    private String avatar;
    private List<Nests> joined_nests;
    private List<String> uploaded_musics;
    private List<String> alarm_clocks;
    private String nickname;
    private String _id;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCreated_time() {
        return created_time;
    }

    public void setCreated_time(int created_time) {
        this.created_time = created_time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
