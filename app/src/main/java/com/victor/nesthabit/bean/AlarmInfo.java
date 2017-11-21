package com.victor.nesthabit.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.victor.nesthabit.db.IntergerListConverter;
import com.victor.nesthabit.db.PostFileConverter;

import java.util.List;

/**
 * Created by victor on 7/24/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

@Entity(tableName = "alarms")
@TypeConverters({IntergerListConverter.class, PostFileConverter.class})
public class AlarmInfo {


    /**
     * bind_to_nest :
     * createdAt : 2017-11-20 22:18:45
     * hour : 7
     * minute : 12
     * music_id :
     * nap : true
     * objectId : e64ada2045
     * owner : far
     * repeat : [0,1,3,5,6]
     * title : waaaaaaa
     * updatedAt : 2017-11-20 22:18:45
     * vibrate : true
     * volume : 40
     * willing_music : true
     * willing_text : true
     */

    private String bind_to_nest;
    private String createdAt;
    private int hour;
    private int minute;
    private PostFileResponse music;
    private boolean nap;

    @PrimaryKey
    @NonNull
    private String objectId;
    private String owner;
    private String title;
    private String updatedAt;
    private boolean vibrate;
    private int volume;
    private boolean willing_music;
    private boolean willing_text;
    private List<Integer> repeat;

    public String getBind_to_nest() {
        return bind_to_nest;
    }

    public void setBind_to_nest(String bind_to_nest) {
        this.bind_to_nest = bind_to_nest;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public PostFileResponse getMusic() {
        return music;
    }


    public boolean isNap() {
        return nap;
    }

    public void setNap(boolean nap) {
        this.nap = nap;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setMusic(PostFileResponse music) {
        this.music = music;
    }

    public boolean isWilling_music() {
        return willing_music;
    }

    public void setWilling_music(boolean willing_music) {
        this.willing_music = willing_music;
    }

    public boolean isWilling_text() {
        return willing_text;
    }

    public void setWilling_text(boolean willing_text) {
        this.willing_text = willing_text;
    }

    public List<Integer> getRepeat() {
        return repeat;
    }

    public void setRepeat(List<Integer> repeat) {
        this.repeat = repeat;
    }
}
