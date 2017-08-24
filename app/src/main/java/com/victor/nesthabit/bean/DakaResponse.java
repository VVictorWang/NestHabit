package com.victor.nesthabit.bean;

/**
 * Created by victor on 8/7/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class DakaResponse {
    /**
     * target_nest : id
     * operate_time : 201708091234
     * username :
     * day : %Y%m%d
     */

    private String target_nest;
    private long operate_time;
    private String username;
    private String day;

    public String getTarget_nest() {
        return target_nest;
    }

    public void setTarget_nest(String target_nest) {
        this.target_nest = target_nest;
    }

    public long getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(long operate_time) {
        this.operate_time = operate_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
