package com.victor.nesthabit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.victor.nesthabit.data.AlarmTime;
import com.victor.nesthabit.utils.AlarmManagerUtil;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    private final  int SECONDLY = 1,MINUTELY = 2,HOURLY  = 3, DAILY = 4;

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", 0);
        AlarmTime alarmTime = DataSupport.find(AlarmTime.class, (long) id);
//        Dbhelper database = Dbhelper.getreminderDatabase(context);
//        Cursor cursor = database.getItem(id);
//        cursor.moveToFirst();
        int frequency = alarmTime.getFrequency();
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(alarmTime.getTimeInmillis());
        //根据重复次数设置闹钟提醒
        if (frequency > 0) {
            if (frequency == SECONDLY) {
                time.add(Calendar.SECOND, 1);
            }
            if (frequency == MINUTELY) {
                time.add(Calendar.MINUTE, 1);
            }
            if (frequency == HOURLY) {
                time.add(Calendar.HOUR, 1);
            } else if (frequency == DAILY) {
                time.add(Calendar.DATE, 1);
            }
            alarmTime.setTimeInmillis(time.getTimeInMillis());
            alarmTime.update(id);
//            database.updateTime(id, time.getTimeInMillis());
            AlarmManagerUtil.setAlarm(context, alarmTime);
//            Intent setAlarm = new Intent(context, AlarmService.class);
//            setAlarm.putExtra("id", id);
//            setAlarm.setAction(AlarmService.CREATE);
//            context.startService(setAlarm);
    }


}

}
