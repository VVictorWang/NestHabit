package com.victor.nesthabit.data;

import java.util.List;

/**
 * Created by victor on 7/24/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class AlarmReq {
    /**
     * title : waaaaaaa
     * repeat : [0,1,3,5,6]
     * music_id :
     * duration_level : 1
     * volume_level : 1
     * nap_level : 1
     * bind_to_nest :
     * willing_music : true
     * willing_text : true
     * time : [7,0]
     */

    private String title;
    private String music_id;
    private int duration_level;
    private int volume_level;
    private int nap_level;
    private String bind_to_nest;
    private boolean willing_music;
    private boolean willing_text;
    private List<Integer> repeat;
    private List<Integer> time;

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

    public int getDuration_level() {
        return duration_level;
    }

    public void setDuration_level(int duration_level) {
        this.duration_level = duration_level;
    }

    public int getVolume_level() {
        return volume_level;
    }

    public void setVolume_level(int volume_level) {
        this.volume_level = volume_level;
    }

    public int getNap_level() {
        return nap_level;
    }

    public void setNap_level(int nap_level) {
        this.nap_level = nap_level;
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
