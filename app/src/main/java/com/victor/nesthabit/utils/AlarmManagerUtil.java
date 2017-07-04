package com.victor.nesthabit.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.victor.nesthabit.activity.AlarmActivity;
import com.victor.nesthabit.broadcast.AlarmReceiver;
import com.victor.nesthabit.data.AlarmTime;

import static android.R.attr.id;

/**
 * Created by victor on 7/2/17.
 */

public class AlarmManagerUtil {
    public static void cancelAlarm(Context context,  AlarmTime alarmTime) {
//        Intent cancel = new Intent(context, AlarmService.class);
//        cancel.putExtra("id", id);
//        cancel.setAction(AlarmService.CANCEL);
//        context.startService(cancel);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("id", alarmTime.getId());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        Intent intent2 = new Intent(context, AlarmActivity.class);
        intent2.putExtra("alert", alarmTime.getAlert_music());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        alarm.cancel(pendingIntent);
    }

    /**
     * 设置闹钟
     */
    public static void setAlarm(Context context, AlarmTime alarmTime) {

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("id", alarmTime.getId());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        Intent intent2 = new Intent(context, AlarmActivity.class);
        intent2.putExtra("alert", alarmTime.getAlert_music());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        long timeInMilliseconds = alarmTime.getTimeInmillis();
        alarm.set(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pendingIntent);
//        Intent alarm = new Intent(context, AlarmService.class);
//        alarm.setAction(AlarmService.CREATE);
//        alarm.putExtra("id", 8);
//        alarm.putExtra("time", 12344);
//        context.startService(alarm);
    }

    public static void updateAlarm(Context context, AlarmTime previous) {
        cancelAlarm(context, previous);
        previous.setId(id);
        previous.save();
        setAlarm(context, previous);
    }

}
