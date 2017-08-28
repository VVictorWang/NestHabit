package com.victor.nesthabit.util;

import com.victor.nesthabit.bean.AlarmResponse;
import com.victor.nesthabit.bean.AlarmTime;
import com.victor.nesthabit.bean.MyNestInfo;
import com.victor.nesthabit.bean.NestInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by victor on 7/26/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class DataCloneUtil {
    public static NestInfo cloneMynestToNest(MyNestInfo info) {
        NestInfo nestInfo = new NestInfo();
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

    public static AlarmTime cloneAlarmRestoTime(AlarmResponse response) {
        AlarmTime alarmTime = new AlarmTime();
        alarmTime.setSnap(response.isNap());
        alarmTime.setTitle(response.getTitle());
        alarmTime.setReceive_Voice(response.isWilling_music());
        alarmTime.setReceive_text(response.isWilling_text());
        String time = response.getTime();
        String[] str = time.split(",");
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str[0]);
        alarmTime.setHour(Integer.valueOf(m.replaceAll("").trim()));
        m = p.matcher(str[1]);
        alarmTime.setMinute(Integer.valueOf(m.replaceAll("").trim()));
        String  repeatString = response.getRepeat();
        str = repeatString.split(",");
        List<Integer> repeat = new ArrayList<>();
        for (String s : str) {
            m = p.matcher(s);
            repeat.add(Integer.valueOf(m.replaceAll("").trim()));
        }
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

}
