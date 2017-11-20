package com.victor.nesthabit.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

/**
 * @author victor
 * @date 11/18/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class StringListConverter {

    @TypeConverter
    public static List<String> stringToList(String needs) {
        String[] result = needs.split(" ");
        return Arrays.asList(result);
    }

    @TypeConverter
    public static String listToString(List<String> needs) {
        if (needs == null || needs.isEmpty()) {
            return " ";
        }
        StringBuilder result = new StringBuilder();
        for (String item : needs) {
            result.append(item);
            result.append(" ");
        }
        return result.toString();
    }
}
