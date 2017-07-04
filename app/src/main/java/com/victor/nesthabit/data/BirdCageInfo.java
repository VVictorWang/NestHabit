package com.victor.nesthabit.data;

/**
 * Created by victor on 7/2/17.
 */

public class BirdCageInfo {
    private String info;
    private int day_total;
    private int day_insist;
    private int people;

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
