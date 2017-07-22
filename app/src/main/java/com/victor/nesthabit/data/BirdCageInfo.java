package com.victor.nesthabit.data;

import org.litepal.crud.DataSupport;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class BirdCageInfo extends DataSupport{
    private int id;
    private String info;
    private int day_total;
    private int day_insist;
    private int people;
    private String start_time;
    private String introduction;
    private boolean limitNumber;

    public boolean isLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(boolean limitNumber) {
        this.limitNumber = limitNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getDay_total() {
        return day_total;
    }

    public void setDay_total(int day_total) {
        this.day_total = day_total;
    }

    public int getDay_insist() {
        return day_insist;
    }

    public void setDay_insist(int day_insist) {
        this.day_insist = day_insist;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }
}
