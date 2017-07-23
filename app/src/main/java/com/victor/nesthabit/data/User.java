package com.victor.nesthabit.data;

import java.util.List;

/**
 * Created by victor on 7/14/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class User {
    /**
     * username : far
     * password : xxxx
     * joined_nests : [{"_id":"59737af8f6ded61e7af0e6b9","kept_days":0},
     * {"_id":"59737af9f6ded61e7af0e6bb","kept_days":0}]
     * uploaded_musics : ["597385a6f6ded65832a3c175","597385a6f6deda5f2a3gc165"]
     * alarm_clocks : ["597385a6f6ded65832a3c175","597385aaf6ded65832a3c176"]
     * avatar : http://nesthabit.pek3a.qingstor.com/image/759535687779715.png
     */

    private String username;
    private String password;
    private String avatar;
    private List<JoinedNestsBean> joined_nests;
    private List<String> uploaded_musics;
    private List<String> alarm_clocks;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<JoinedNestsBean> getJoined_nests() {
        return joined_nests;
    }

    public void setJoined_nests(List<JoinedNestsBean> joined_nests) {
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

    public static class JoinedNestsBean {
        /**
         * _id : 59737af8f6ded61e7af0e6b9
         * kept_days : 0
         */

        private String _id;
        private int kept_days;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getKept_days() {
            return kept_days;
        }

        public void setKept_days(int kept_days) {
            this.kept_days = kept_days;
        }
    }
    /**
     * username : Faraway
     * password : 531a9a424c8b00bda936c84dc34f7e7fc4adfa44
     * nickname : hzy
     * avatar :
     */

}
