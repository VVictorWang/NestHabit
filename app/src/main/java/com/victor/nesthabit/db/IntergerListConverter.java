package com.victor.nesthabit.db;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author victor
 * @date 11/19/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class IntergerListConverter {

    @TypeConverter
    public static List<Integer> integerStringToList(String integers) {
        String[] resultString = integers.split(" ");
        List<Integer> result = new ArrayList<>();
        for (String item : resultString) {
            result.add(Integer.valueOf(item));
        }
        return result;
    }

    @TypeConverter
    public static String integerToString(List<Integer> integers) {
        StringBuilder result = new StringBuilder();
        for (Integer item : integers) {
            result.append(item);
            result.append(" ");
        }
        return result.toString();
    }
}
