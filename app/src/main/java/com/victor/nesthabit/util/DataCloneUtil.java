package com.victor.nesthabit.util;

import com.victor.nesthabit.api.UserApi;
import com.victor.nesthabit.bean.AlarmResponse;
import com.victor.nesthabit.bean.AlarmTime;
import com.victor.nesthabit.bean.MyNestInfo;
import com.victor.nesthabit.bean.NestInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

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



}
