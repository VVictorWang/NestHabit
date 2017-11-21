package com.victor.nesthabit.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.victor.nesthabit.db.MembersBeanConverter;
import com.victor.nesthabit.db.StringListConverter;

import java.util.List;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

@Entity(tableName = "nests")
@TypeConverters({StringListConverter.class, MembersBeanConverter.class})
public class NestInfo {


    /**
     * challenge_days : 1000
     * chatlogs : []
     * cover_image :
     * createdAt : 2017-11-21 22:14:29
     * desc : aaaa
     * members : [{"constant_days":2,"kept_days":34,"userId":"32423e"}]
     * members_amount : 1
     * members_limit : 0
     * name : bbbb
     * objectId : 8503821e85
     * open : true
     * owner : wang
     * punchlogs : []
     * start_time : 1331856000
     * updatedAt : 2017-11-21 22:14:29
     */

    private int challenge_days;
    private String cover_image;
    private String createdAt;
    private String desc;
    private int members_amount;
    private int members_limit;
    private String name;
    @PrimaryKey
    @NonNull
    private String objectId;
    private boolean open;
    private String owner;
    private long start_time;
    private String updatedAt;
    private List<String> chatlogs;
    private List<MembersBean> members;
    private List<String> punchlogs;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getMembers_amount() {
        return members_amount;
    }

    public void setMembers_amount(int members_amount) {
        this.members_amount = members_amount;
    }

    public int getMembers_limit() {
        return members_limit;
    }

    public void setMembers_limit(int members_limit) {
        this.members_limit = members_limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public List<MembersBean> getMembers() {
        return members;
    }

    public void setMembers(List<MembersBean> members) {
        this.members = members;
    }

    public List<String> getChatlogs() {
        return chatlogs;
    }

    public void setChatlogs(List<String> chatlogs) {
        this.chatlogs = chatlogs;
    }

    public List<String> getPunchlogs() {
        return punchlogs;
    }

    public void setPunchlogs(List<String> punchlogs) {
        this.punchlogs = punchlogs;
    }

    public static class MembersBean {
        /**
         * constant_days : 2
         * kept_days : 34
         * userId : 32423e
         */

        private int constant_days;
        private int kept_days;
        private String userId;

        public int getConstant_days() {
            return constant_days;
        }

        public void setConstant_days(int constant_days) {
            this.constant_days = constant_days;
        }

        public int getKept_days() {
            return kept_days;
        }

        public void setKept_days(int kept_days) {
            this.kept_days = kept_days;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
