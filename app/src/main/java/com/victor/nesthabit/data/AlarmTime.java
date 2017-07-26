package com.victor.nesthabit.data;

import android.content.Intent;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by victor on 7/4/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class AlarmTime extends DataSupport {
    private String title;
    private long id;
    private String alert_music;
    private List<Integer> weeks;
    private String music_id;
    private int hour;
    private int minute;
    private boolean isSnap;
    private boolean receive_Voice;
    private boolean receive_text;
    private String bind_to_nest;
    private long creat_time;

    public long getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(long creat_time) {
        this.creat_time = creat_time;
    }

    public String getBind_to_nest() {
        return bind_to_nest;
    }

    public void setBind_to_nest(String bind_to_nest) {
        this.bind_to_nest = bind_to_nest;
    }

    public boolean isReceive_Voice() {
        return receive_Voice;
    }

    public void setReceive_Voice(boolean receive_Voice) {
        this.receive_Voice = receive_Voice;
    }

    public String getMusic_id() {
        return music_id;
    }

    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }

    public boolean isReceive_text() {
        return receive_text;
    }

    public void setReceive_text(boolean receive_text) {
        this.receive_text = receive_text;
    }

    public boolean isSnap() {

        return isSnap;
    }

    public void setSnap(boolean snap) {
        isSnap = snap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlert_music() {
        return alert_music;
    }

    public void setAlert_music(String alert_music) {
        this.alert_music = alert_music;
    }

    public List<Integer> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Integer> weeks) {
        this.weeks = weeks;
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
}
