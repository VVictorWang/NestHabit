package com.victor.nesthabit.data;

/**
 * Created by victor on 7/14/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class User {
    /**
     * username : Faraway
     * password : 531a9a424c8b00bda936c84dc34f7e7fc4adfa44
     * nickname : hzy
     * avatar :
     */

    private String username;
    private String password;
    private String nickname;
    private String avatar;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
