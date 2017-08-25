package com.victor.nesthabit.bean;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AddNestResponse {

    /**
     * name : aaaaa
     * desc : bbbbb
     * members_limit : 0
     * start_time : 1456789000
     * challenge_days : 10000
     * cover_image :
     * open : true
     * created_time : 1500757500
     * creator : far
     * owner : far
     * members_amount : 1
     * _id : 59742e7cf6ded67422b9ffa4
     */

    private String name;
    private String desc;
    private int members_limit;
    private long start_time;
    private int challenge_days;
    private String cover_image;
    private boolean open;
    private long created_time;
    private String creator;
    private String owner;
    private int members_amount;
    private String _id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getMembers_limit() {
        return members_limit;
    }

    public void setMembers_limit(int members_limit) {
        this.members_limit = members_limit;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getCreated_time() {
        return created_time;
    }

    public void setCreated_time(long created_time) {
        this.created_time = created_time;
    }

    public int getChallenge_days() {
        return challenge_days;
    }

    public void setChallenge_days(int challenge_days) {
        this.challenge_days = challenge_days;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getMembers_amount() {
        return members_amount;
    }

    public void setMembers_amount(int members_amount) {
        this.members_amount = members_amount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
