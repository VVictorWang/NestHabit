package com.victor.nesthabit.util;

import com.victor.nesthabit.data.AlarmResponse;
import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.data.MyNestInfo;
import com.victor.nesthabit.data.NestInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 7/26/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class DataCloneUtil {
    public static NestInfo cloneMynestToNest(MyNestInfo info) {
        NestInfo nestInfo = new NestInfo();
        nestInfo.setDay_insist(info.getDay_insist());
        nestInfo.setMembers_limit(info.getMembers_limit());
        nestInfo.setStart_time(info.getStart_time());
        nestInfo.setCreated_time(info.getCreated_time());
        nestInfo.setChallenge_days(info.getChallenge_days());
        nestInfo.setName(info.getName());
        nestInfo.setDesc(info.getDesc());
        nestInfo.set_id(info.getMyid());
        nestInfo.setMembers_amount(info.getMembers_amount());
        nestInfo.setCreator(info.getCreator());
        nestInfo.setOwner(info.getOwner());
        nestInfo.setCover_image(info.getCover_image());
        return nestInfo;
    }

    public static MyNestInfo cloneNestToMyNest(NestInfo info) {
        MyNestInfo nestInfo = new MyNestInfo();
        nestInfo.setDay_insist(info.getDay_insist());
        nestInfo.setMembers_limit(info.getMembers_limit());
        nestInfo.setStart_time(info.getStart_time());
        nestInfo.setCreated_time(info.getCreated_time());
        nestInfo.setChallenge_days(info.getChallenge_days());
        nestInfo.setName(info.getName());
        nestInfo.setDesc(info.getDesc());
        nestInfo.setMyid(info.get_id());
        nestInfo.setMembers_amount(info.getMembers_amount());
        nestInfo.setCreator(info.getCreator());
        nestInfo.setOwner(info.getOwner());
        nestInfo.setCover_image(info.getCover_image());
        return nestInfo;
    }

    public static List<MyNestInfo> cloneNestToMyNestList(List<NestInfo> info) {
        List<MyNestInfo> reslut = new ArrayList<>();
        if (!info.isEmpty()) {
            for (NestInfo nnestInfo : info) {
                MyNestInfo nestInfo = new MyNestInfo();
                nestInfo.setDay_insist(nnestInfo.getDay_insist());
                nestInfo.setMembers_limit(nnestInfo.getMembers_limit());
                nestInfo.setStart_time(nnestInfo.getStart_time());
                nestInfo.setCreated_time(nnestInfo.getCreated_time());
                nestInfo.setMembers(nnestInfo.getMembers());
                nestInfo.setChallenge_days(nnestInfo.getChallenge_days());
                nestInfo.setName(nnestInfo.getName());
                nestInfo.setDesc(nnestInfo.getDesc());
                nestInfo.setMyid(nnestInfo.get_id());
                nestInfo.setMembers_amount(nnestInfo.getMembers_amount());
                nestInfo.setCreator(nnestInfo.getCreator());
                nestInfo.setOwner(nnestInfo.getOwner());
                nestInfo.setCover_image(nnestInfo.getCover_image());

                reslut.add(nestInfo);
            }
        }
        return reslut;
    }

    public static List<NestInfo> cloneMyNestToNestList(List<MyNestInfo> info) {
        List<NestInfo> reslut = new ArrayList<>();
        if (!info.isEmpty()) {
            for (MyNestInfo nnestInfo : info) {
                NestInfo nestInfo = new NestInfo();
                nestInfo.setDay_insist(nnestInfo.getDay_insist());
                nestInfo.setMembers_limit(nnestInfo.getMembers_limit());
                nestInfo.setStart_time(nnestInfo.getStart_time());
                nestInfo.setCreated_time(nnestInfo.getCreated_time());
                nestInfo.setMembers(nnestInfo.getMembers());
                nestInfo.setChallenge_days(nnestInfo.getChallenge_days());
                nestInfo.setName(nnestInfo.getName());
                nestInfo.setDesc(nnestInfo.getDesc());
                nestInfo.setCover_image(nnestInfo.getCover_image());
                nestInfo.set_id(nnestInfo.getMyid());
                nestInfo.setMembers_amount(nnestInfo.getMembers_amount());
                nestInfo.setCreator(nnestInfo.getCreator());
                nestInfo.setOwner(nnestInfo.getOwner());
                reslut.add(nestInfo);
            }
        }
        return reslut;
    }

    public static AlarmTime cloneAlarmRestoTime(AlarmResponse response) {
        AlarmTime alarmTime = new AlarmTime();
        alarmTime.setSnap(response.isNap());
        alarmTime.setTitle(response.getTitle());
        alarmTime.setReceive_Voice(response.isWilling_music());
        alarmTime.setReceive_text(response.isWilling_text());
        alarmTime.setHour(response.getTime().get(0));
        alarmTime.setMinute(response.getTime().get(1));
        List<Integer> repeat = response.getRepeat();
        List<Integer> weeks = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weeks.add(0);
        }
        for (int i : repeat) {
            weeks.set(i, 1);
        }
        alarmTime.setWeeks(weeks);
        alarmTime.setMusic_id(response.getMusic_id());
        alarmTime.setBind_to_nest(response.getBind_to_nest());
        alarmTime.setCreat_time(response.getCreated_time());
        return alarmTime;
    }

    public static AlarmResponse cloneTimeToAlarmRes(AlarmTime alarmtime) {
        AlarmResponse result = new AlarmResponse();
        result.setBind_to_nest(alarmtime.getBind_to_nest());
        result.setTitle(alarmtime.getTitle());

        return result;
    }
}
