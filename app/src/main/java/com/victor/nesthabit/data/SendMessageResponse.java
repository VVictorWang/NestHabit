package com.victor.nesthabit.data;

/**
 * Created by victor on 8/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class SendMessageResponse {
    /**
     * value : 撤回
     * target_nest : 599a76a79ccbae33e5715cbb
     * time : 1503369750
     * username : test
     * _id : 599c0a969ccbae33e3715cbd
     */

    private String value;
    private String target_nest;
    private long time;
    private String username;
    private String _id;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTarget_nest() {
        return target_nest;
    }

    public void setTarget_nest(String target_nest) {
        this.target_nest = target_nest;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
