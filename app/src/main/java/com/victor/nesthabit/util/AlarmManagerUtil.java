package com.victor.nesthabit.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.victor.nesthabit.ui.activity.AlarmActivity;
import com.victor.nesthabit.broadcast.AlarmReceiver;
import com.victor.nesthabit.data.AlarmTime;

import java.util.Calendar;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by victor on 7/2/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science
 */

public class AlarmManagerUtil {
    private static int[] WEEKS = new int[]{Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY,
            Calendar
                    .WEDNESDAY, Calendar.THURSDAY, Calendar.FEBRUARY, Calendar.SATURDAY};
    public static final int intervalMillis = 24 * 3600 * 1000 * 7;

    public static void cancelAlarm(Context context, AlarmTime alarmTime) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("id", alarmTime.getId());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        Intent intent2 = new Intent(context, AlarmActivity.class);
        intent2.putExtra("alert", alarmTime.getAlert_music());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarm.cancel(pendingIntent);
    }

    /**
     * 设置闹钟
     */
    public static void setAlarm(Context context, AlarmTime alarmTime) {

        AlarmManager alarm = null;
        Intent intent2 = new Intent(context, AlarmActivity.class);
        intent2.putExtra("alert", alarmTime.getAlert_music());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2,
                PendingIntent.FLAG_UPDATE_CURRENT);
        List<Integer> weeks = alarmTime.getWeeks();
        for (int i = 0; i < weeks.size(); i++) {
            if (weeks.get(i) == 1) {
                Calendar calendar = Calendar.getInstance();
                calendar.setFirstDayOfWeek(Calendar.SUNDAY);
                calendar.set(Calendar.DAY_OF_WEEK, WEEKS[i]);
                calendar.set(Calendar.HOUR, alarmTime.getHour());
                calendar.set(Calendar.MINUTE, alarmTime.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        intervalMillis, pendingIntent);
            }
        }

//        Intent intent = new Intent(context, AlarmReceiver.class);
//        intent.putExtra("id", alarmTime.getId());
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


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
