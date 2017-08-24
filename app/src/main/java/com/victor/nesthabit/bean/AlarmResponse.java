package com.victor.nesthabit.bean;

import java.util.List;

/**
 * Created by victor on 7/24/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AlarmResponse {


    /**
     * title : AAA
     * time : [7,0]
     * repeat : [1,2,3]
     * music_id : 5989ebfd9ccbae31ae89dcd7
     * nap : true
     * shock : true
     * bind_to_nest :
     * willing_music : true
     * willing_text : true
     * created_time : 1502182372
     * owner : far
     * _id : 5989ec64f6ded620ef411fef
     */

    public String title;
    public String music_id;
    public boolean nap;
    public boolean shock;
    public String bind_to_nest;
    public boolean willing_music;
    public boolean willing_text;
    public int created_time;
    public String owner;
    public String _id;
    public List<Integer> time;
    public List<Integer> repeat;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMusic_id() {
        return music_id;
    }

    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }

    public boolean isNap() {
        return nap;
    }

    public void setNap(boolean nap) {
        this.nap = nap;
    }

    public boolean isShock() {
        return shock;
    }

    public void setShock(boolean shock) {
        this.shock = shock;
    }

    public String getBind_to_nest() {
        return bind_to_nest;
    }

    public void setBind_to_nest(String bind_to_nest) {
        this.bind_to_nest = bind_to_nest;
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

    public int getCreated_time() {
        return created_time;
    }

    public void setCreated_time(int created_time) {
        this.created_time = created_time;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Integer> getTime() {
        return time;
    }

    public void setTime(List<Integer> time) {
        this.time = time;
    }

    public List<Integer> getRepeat() {
        return repeat;
    }

    public void setRepeat(List<Integer> repeat) {
        this.repeat = repeat;
    }
}
