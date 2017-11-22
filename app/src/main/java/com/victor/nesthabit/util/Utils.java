package com.victor.nesthabit.util;

import android.net.Uri;
import android.os.Environment;

import com.victor.nesthabit.bean.Constants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by victor on 7/26/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class Utils {
    public static String getHeader() {
        return PrefsUtils.getValue(AppUtils.getAppContext(), Constants.AUTHORIZATION, null);
    }

    public static String getUsername() {
        return PrefsUtils.getValue(AppUtils.getAppContext(), Constants.USERNAME, null);
    }

    public static String getPassword() {
        return PrefsUtils.getValue(AppUtils.getAppContext(), Constants.PASSWORD, null);
    }

    public static String getUserId() {
        return PrefsUtils.getValue(AppUtils.getAppContext(), Constants.USER_OBJEDCTID, null);
    }

    public static File Bytes2File(String fileName, byte[] bytes) {
        File file = new File(Environment.getDownloadCacheDirectory(), fileName);
        if (file.exists()) {
            return file;
        }
        FileOutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {

            file.createNewFile();
            outputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return file;

    }

    public static String createAcacheKey(Object... param) {
        String key = "";
        for (Object o : param) {
            key += "-" + o;
        }
        return key.replaceFirst("-", "");
    }

//    public static String getFileMimeType(File file) {
//        String fileName = file.getName();
//
//    }

    public static Uri getImageStreamFromExternal(String imageName) {
        File externalPubPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        );

        File picPath = new File(externalPubPath, imageName);
        Uri uri = null;
        if (picPath.exists()) {
            uri = Uri.fromFile(picPath);
        }

        return uri;
    }

    public static String getAlarmHour(String time) {
        String[] str = time.split(",");
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str[0]);
        return String.format("%02d", Integer.valueOf(m.replaceAll("").trim()));
    }

    public static String getAlarmMinute(String time) {
        String[] str = time.split(",");
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str[1]);
        return String.format("%02d", Integer.valueOf(m.replaceAll("").trim()));
    }

    public static List<Integer> getSelectedWeeks(String repeatString) {
        String[] str = repeatString.split(",");
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        List<Integer> repeat = new ArrayList<>();
        for (String s : str) {
            Matcher m = p.matcher(s);
            repeat.add(Integer.valueOf(m.replaceAll("").trim()));
        }
        List<Integer> weeks = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weeks.add(0);
        }
        for (int i : repeat) {
            weeks.set(i, 1);
        }
        return weeks;
    }


}


