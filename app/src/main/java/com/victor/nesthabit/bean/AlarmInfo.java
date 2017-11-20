package com.victor.nesthabit.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.victor.nesthabit.db.IntergerListConverter;

import java.util.List;

/**
 * Created by victor on 7/24/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

@Entity(tableName = "alarms")
@TypeConverters(IntergerListConverter.class)
public class AlarmInfo {


    /**
     * bind_to_nest :
     * createdAt : 2017-11-19 23:03:15
     * duration_level : 1
     * music_id :
     * nap_level : 1
     * objectId : f7004392c8
     * owner : far
     * repeat : [0,1,3,5,6]
     * time : [7,0]
     * title : waaaaaaa
     * updatedAt : 2017-11-19 23:03:15
     * volume_level : 1
     * willing_music : true
     * willing_text : true
     */

    private String bind_to_nest;
    private String createdAt;
    private int duration_level;
    private String music_id;
    private int nap_level;

    @PrimaryKey
    @NonNull
    private String objectId;
    private String owner;
    private String title;
    private String updatedAt;
    private int volume_level;
    private boolean willing_music;
    private boolean willing_text;
    private List<Integer> repeat;
    private List<Integer> time;

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

    public int getDuration_level() {
        return duration_level;
    }

    public void setDuration_level(int duration_level) {
        this.duration_level = duration_level;
    }

    public String getMusic_id() {
        return music_id;
    }

    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }

    public int getNap_level() {
        return nap_level;
    }

    public void setNap_level(int nap_level) {
        this.nap_level = nap_level;
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

    public int getVolume_level() {
        return volume_level;
    }

    public void setVolume_level(int volume_level) {
        this.volume_level = volume_level;
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

    public List<Integer> getTime() {
        return time;
    }

    public void setTime(List<Integer> time) {
        this.time = time;
    }
}
