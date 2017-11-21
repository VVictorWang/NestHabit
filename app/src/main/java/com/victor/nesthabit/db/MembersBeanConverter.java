package com.victor.nesthabit.db;

import android.arch.persistence.room.TypeConverter;

import com.victor.nesthabit.bean.NestInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author victor
 * @date 11/21/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class MembersBeanConverter {

    @TypeConverter
    public static String MembersBeanToString(List<NestInfo.MembersBean> membersBean) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        if (membersBean == null) {
            return " ";
        }
        for (NestInfo.MembersBean bean : membersBean) {
            builder.append(bean.getConstant_days() + " ");
            builder.append(bean.getKept_days() + " ");
            builder.append(bean.getUserId());
            if (i < membersBean.size() - 1) {
                builder.append(",");
            }
            i++;
        }
        return builder.toString();
    }

    @TypeConverter
    public static List<NestInfo.MembersBean> stringToMembersBean(String membersBeanString) {
        List<NestInfo.MembersBean> membersBeans = new ArrayList<>();

        if (" ".equals(membersBeanString)) {
            return membersBeans;
        }
        String[] results = membersBeanString.split(",");
        for (String item : results) {
            String[] result = item.split(" ");
            NestInfo.MembersBean bean = new NestInfo.MembersBean();
            bean.setConstant_days(Integer.valueOf(result[0]));
            bean.setKept_days(Integer.valueOf(result[1]));
            bean.setUserId(result[2]);
            membersBeans.add(bean);
        }
        return membersBeans;
    }
}
