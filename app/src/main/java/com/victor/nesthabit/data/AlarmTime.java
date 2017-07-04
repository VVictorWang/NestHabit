package com.victor.nesthabit.data;

import org.litepal.crud.DataSupport;

/**
 * Created by victor on 7/4/17.
 */

public class AlarmTime extends DataSupport{
    private String time;
    private int frequency;
    private long id;
    private String alert_music;
    private long timeInmillis;

    public long getTimeInmillis() {
        return timeInmillis;
    }

    public void setTimeInmillis(long timeInmillis) {
        this.timeInmillis = timeInmillis;
    }

    public String getAlert_music() {
        return alert_music;
    }

    public void setAlert_music(String alert_music) {
        this.alert_music = alert_music;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
